package net.eymbra.dimensions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.JigsawJunction;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;
import org.jetbrains.annotations.Nullable;

public class EymbraChunkGenerator extends ChunkGenerator {
	public static void registerEymbraChunkGenerator() {
		Registry.register(Registry.CHUNK_GENERATOR, new Identifier(EymbraPrehistoric.MODID, "chunk_generator"), EymbraChunkGenerator.CODEC);
	}

	public static final Codec<EymbraChunkGenerator> CODEC = RecordCodecBuilder.create(
			(instance) -> instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((surfaceChunkGenerator) -> surfaceChunkGenerator.biomeSource), StructuresConfig.CODEC.fieldOf("structures").forGetter((ChunkGenerator::getStructuresConfig)))
					.apply(instance, instance.stable(EymbraChunkGenerator::new)));

	private static final int LERP_RANGE = 3;
	private static final float[] HEIGHT_LERP = Util.make(new float[(LERP_RANGE * 2 + 1) * (LERP_RANGE * 2 + 1)], (fs) -> {
		for (int i = -LERP_RANGE; i <= LERP_RANGE; ++i) {
			for (int j = -LERP_RANGE; j <= LERP_RANGE; ++j) {
				float f = 1.0F / MathHelper.sqrt(i * i + j * j + 0.2F);
				fs[i + LERP_RANGE + (j + LERP_RANGE) * (LERP_RANGE * 2 + 1)] = f;
			}
		}
	});

	private static final float[] NOISE_WEIGHT_TABLE = Util.make(new float[13824], (array) -> {
		for (int i = 0; i < 24; ++i) {
			for (int j = 0; j < 24; ++j) {
				for (int k = 0; k < 24; ++k) {
					array[i * 24 * 24 + j * 24 + k] = (float) calculateNoiseWeight(j - 12, k - 12, i - 12);
				}
			}
		}
	});

	private final OctavePerlinNoiseSampler field_24776;
	private final StructuresConfig structuresConfig;
	private final int horizontalNoiseResolution;
	private final int verticalNoiseResolution;
	protected final ChunkRandom random;
	private final OctavePerlinNoiseSampler lowerInterpolatedNoise;
	private final OctavePerlinNoiseSampler upperInterpolatedNoise;
	private final OctavePerlinNoiseSampler interpolationNoise;
	private final NoiseSampler surfaceDepthNoise;
	protected final BlockState defaultBlock;
	protected final BlockState defaultFluid;
	private static final BlockState AIR;
	private final int noiseSizeX;
	private final int noiseSizeY;
	private final int noiseSizeZ;
	private final int height;

	public EymbraChunkGenerator(BiomeSource biomeSource, StructuresConfig structuresConfig) {
		super(biomeSource, structuresConfig);

		// We need to get world seed here
		this.random = new ChunkRandom(0);

		this.defaultBlock = Blocks.STONE.getDefaultState();
		this.defaultFluid = Blocks.WATER.getDefaultState();

		this.structuresConfig = structuresConfig;
		this.height = 256;
		this.random.consume(2620);
		this.verticalNoiseResolution = 8;
		this.horizontalNoiseResolution = 4;
		this.noiseSizeX = 16 / this.horizontalNoiseResolution;
		this.noiseSizeY = this.height / this.verticalNoiseResolution;
		this.noiseSizeZ = 16 / this.horizontalNoiseResolution;
		this.field_24776 = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
		this.lowerInterpolatedNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
		this.upperInterpolatedNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
		this.interpolationNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-7, 0));
		this.surfaceDepthNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-3, 0));
	}

	@Override
	protected Codec<? extends ChunkGenerator> getCodec() {
		return EymbraChunkGenerator.CODEC;
	}

	@Override
	public ChunkGenerator withSeed(long seed) {
		return new EymbraChunkGenerator(this.biomeSource.withSeed(seed), this.structuresConfig);
	}

	private static double calculateNoiseWeight(int x, int y, int z) {
		double d = x * x + z * z;
		double e = y + 0.5D;
		double f = e * e;
		double g = Math.pow(2.718281828459045D, -(f / 16.0D + d / 16.0D));
		double h = -e * MathHelper.fastInverseSqrt(f / 2.0D + d / 2.0D) / 2.0D;
		return h * g;
	}

	@Override
	public void buildSurface(ChunkRegion region, Chunk chunk) {
		ChunkPos chunkPos = chunk.getPos();
		int i = chunkPos.x;
		int j = chunkPos.z;
		ChunkRandom chunkRandom = new ChunkRandom();
		chunkRandom.setTerrainSeed(i, j);
		ChunkPos chunkPos2 = chunk.getPos();
		int k = chunkPos2.getStartX();
		int l = chunkPos2.getStartZ();
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for (int m = 0; m < 16; ++m) {
			for (int n = 0; n < 16; ++n) {
				int o = k + m;
				int p = l + n;
				int q = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, m, n) + 1;
				double e = this.surfaceDepthNoise.sample(o * 0.0625D, p * 0.0625D, 0.0625D, m * 0.0625D) * 15.0D;
				region.getBiome(mutable.set(k + m, q, l + n)).buildSurface(chunkRandom, chunk, o, p, q, e, this.defaultBlock, this.defaultFluid, this.getSeaLevel(), region.getSeed());
			}
		}

		this.buildBedrock(chunk, chunkRandom);
	}

	private void buildBedrock(Chunk chunk, Random random) {
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		int xStart = chunk.getPos().getStartX();
		int zStart = chunk.getPos().getStartZ();
		int floorHeight = 2;

		for (BlockPos blockpos : BlockPos.iterate(xStart, 0, zStart, xStart + 15, 0, zStart + 15)) {
			// single layer of solid blocks
			for (int floorY = 0; floorY <= floorHeight + random.nextInt(2); ++floorY) {
				chunk.setBlockState(blockpos$Mutable.set(blockpos.getX(), floorY, blockpos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
			}
		}
	}

	@Override
	public void populateEntities(ChunkRegion region) {
		int i = region.getCenterChunkX();
		int j = region.getCenterChunkZ();
		Biome biome = region.getBiome((new ChunkPos(i, j)).getStartPos());
		ChunkRandom chunkRandom = new ChunkRandom();
		chunkRandom.setPopulationSeed(region.getSeed(), i << 4, j << 4);
		SpawnHelper.populateEntities(region, biome, i, j, chunkRandom);
	}

	@SuppressWarnings({
			"unchecked", "rawtypes"
	})
	@Override
	public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
		ObjectList<StructurePiece> objectList = new ObjectArrayList(10);
		ObjectList<JigsawJunction> objectList2 = new ObjectArrayList(32);
		ChunkPos chunkPos = chunk.getPos();
		int i = chunkPos.x;
		int j = chunkPos.z;
		int k = i << 4;
		int l = j << 4;
		Iterator var11 = StructureFeature.JIGSAW_STRUCTURES.iterator();

		while (var11.hasNext()) {
			StructureFeature<?> structureFeature = (StructureFeature) var11.next();
			accessor.getStructuresWithChildren(ChunkSectionPos.from(chunkPos, 0), structureFeature).forEach((start) -> {
				Iterator var6 = start.getChildren().iterator();

				while (true) {
					while (true) {
						StructurePiece structurePiece;
						do {
							if (!var6.hasNext()) {
								return;
							}

							structurePiece = (StructurePiece) var6.next();
						} while (!structurePiece.intersectsChunk(chunkPos, 12));

						if (structurePiece instanceof PoolStructurePiece) {
							PoolStructurePiece poolStructurePiece = (PoolStructurePiece) structurePiece;
							StructurePool.Projection projection = poolStructurePiece.getPoolElement().getProjection();
							if (projection == StructurePool.Projection.RIGID) {
								objectList.add(poolStructurePiece);
							}

							Iterator var10 = poolStructurePiece.getJunctions().iterator();

							while (var10.hasNext()) {
								JigsawJunction jigsawJunction = (JigsawJunction) var10.next();
								int kx = jigsawJunction.getSourceX();
								int lx = jigsawJunction.getSourceZ();
								if (kx > k - 12 && lx > l - 12 && kx < k + 15 + 12 && lx < l + 15 + 12) {
									objectList2.add(jigsawJunction);
								}
							}
						} else {
							objectList.add(structurePiece);
						}
					}
				}
			});
		}

		double[][][] ds = new double[2][this.noiseSizeZ + 1][this.noiseSizeY + 1];

		for (int m = 0; m < this.noiseSizeZ + 1; ++m) {
			ds[0][m] = new double[this.noiseSizeY + 1];
			this.sampleNoiseColumn(ds[0][m], i * this.noiseSizeX, j * this.noiseSizeZ + m);
			ds[1][m] = new double[this.noiseSizeY + 1];
		}

		ProtoChunk protoChunk = (ProtoChunk) chunk;
		Heightmap heightmap = protoChunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
		Heightmap heightmap2 = protoChunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		ObjectListIterator<StructurePiece> objectListIterator = objectList.iterator();
		ObjectListIterator<JigsawJunction> objectListIterator2 = objectList2.iterator();

		for (int n = 0; n < this.noiseSizeX; ++n) {
			int p;
			for (p = 0; p < this.noiseSizeZ + 1; ++p) {
				this.sampleNoiseColumn(ds[1][p], i * this.noiseSizeX + n + 1, j * this.noiseSizeZ + p);
			}

			for (p = 0; p < this.noiseSizeZ; ++p) {
				ChunkSection chunkSection = protoChunk.getSection(15);
				chunkSection.lock();

				for (int q = this.noiseSizeY - 1; q >= 0; --q) {
					double d = ds[0][p][q];
					double e = ds[0][p + 1][q];
					double f = ds[1][p][q];
					double g = ds[1][p + 1][q];
					double h = ds[0][p][q + 1];
					double r = ds[0][p + 1][q + 1];
					double s = ds[1][p][q + 1];
					double t = ds[1][p + 1][q + 1];

					for (int u = this.verticalNoiseResolution - 1; u >= 0; --u) {
						int v = q * this.verticalNoiseResolution + u;
						int w = v & 15;
						int x = v >> 4;
						if (chunkSection.getYOffset() >> 4 != x) {
							chunkSection.unlock();
							chunkSection = protoChunk.getSection(x);
							chunkSection.lock();
						}

						double y = (double) u / (double) this.verticalNoiseResolution;
						double z = MathHelper.lerp(y, d, h);
						double aa = MathHelper.lerp(y, f, s);
						double ab = MathHelper.lerp(y, e, r);
						double ac = MathHelper.lerp(y, g, t);

						for (int ad = 0; ad < this.horizontalNoiseResolution; ++ad) {
							int ae = k + n * this.horizontalNoiseResolution + ad;
							int af = ae & 15;
							double ag = (double) ad / (double) this.horizontalNoiseResolution;
							double ah = MathHelper.lerp(ag, z, aa);
							double ai = MathHelper.lerp(ag, ab, ac);

							for (int aj = 0; aj < this.horizontalNoiseResolution; ++aj) {
								int ak = l + p * this.horizontalNoiseResolution + aj;
								int al = ak & 15;
								double am = (double) aj / (double) this.horizontalNoiseResolution;
								double an = MathHelper.lerp(am, ah, ai);
								double ao = MathHelper.clamp(an / 200.0D, -1.0D, 1.0D);

								int at;
								int au;
								int ar;
								for (ao = ao / 2.0D - ao * ao * ao / 24.0D; objectListIterator.hasNext(); ao += getNoiseWeight(at, au, ar) * 0.8D) {
									StructurePiece structurePiece = objectListIterator.next();
									BlockBox blockBox = structurePiece.getBoundingBox();
									at = Math.max(0, Math.max(blockBox.minX - ae, ae - blockBox.maxX));
									au = v - (blockBox.minY + (structurePiece instanceof PoolStructurePiece ? ((PoolStructurePiece) structurePiece).getGroundLevelDelta() : 0));
									ar = Math.max(0, Math.max(blockBox.minZ - ak, ak - blockBox.maxZ));
								}

								objectListIterator.back(objectList.size());

								while (objectListIterator2.hasNext()) {
									JigsawJunction jigsawJunction = objectListIterator2.next();
									int as = ae - jigsawJunction.getSourceX();
									at = v - jigsawJunction.getSourceGroundY();
									au = ak - jigsawJunction.getSourceZ();
									ao += getNoiseWeight(as, at, au) * 0.4D;
								}

								objectListIterator2.back(objectList2.size());
								BlockState blockState = this.getBlockState(ao, v);
								if (blockState != AIR) {
									if (blockState.getLuminance() != 0) {
										mutable.set(ae, v, ak);
										protoChunk.addLightSource(mutable);
									}

									chunkSection.setBlockState(af, w, al, blockState, false);
									heightmap.trackUpdate(af, v, al, blockState);
									heightmap2.trackUpdate(af, v, al, blockState);
								}
							}
						}
					}
				}

				chunkSection.unlock();
			}

			double[][] es = ds[0];
			ds[0] = ds[1];
			ds[1] = es;
		}

	}

	private static double getNoiseWeight(int x, int y, int z) {
		int i = x + 12;
		int j = y + 12;
		int k = z + 12;
		if (i >= 0 && i < 24) {
			if (j >= 0 && j < 24) {
				return k >= 0 && k < 24 ? (double) NOISE_WEIGHT_TABLE[k * 24 * 24 + i * 24 + j] : 0.0D;
			} else {
				return 0.0D;
			}
		} else {
			return 0.0D;
		}
	}

	@Override
	public int getHeight(int x, int z, Type heightmapType) {
		return this.height;
	}

	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	@Override
	public BlockView getColumnSample(int x, int z) {
		BlockState[] blockStates = new BlockState[this.noiseSizeY * this.verticalNoiseResolution];
		this.sampleHeightmap(x, z, blockStates, (Predicate) null);
		return new VerticalBlockSample(blockStates);
	}

	private double[] sampleNoiseColumn(int x, int z) {
		double[] ds = new double[this.noiseSizeY + 1];
		this.sampleNoiseColumn(ds, x, z);
		return ds;
	}

	private void sampleNoiseColumn(double[] buffer, int x, int z) {
		double ac;
		double ad;
		double topSlideTarget;
		double topSlideSize;
		float g = 0.0F;
		float h = 0.0F;
		float i = 0.0F;
		int k = this.getSeaLevel();
		float l = this.biomeSource.getBiomeForNoiseGen(x, k, z).getDepth();

		for (int m = -LERP_RANGE; m <= LERP_RANGE; ++m) {
			for (int n = -LERP_RANGE; n <= LERP_RANGE; ++n) {
				Biome biome = this.biomeSource.getBiomeForNoiseGen(x + m, k, z + n);
				float o = biome.getDepth();
				float p = biome.getScale();

				float u = o > l ? 0.5F : 1.0F;
				float v = u * HEIGHT_LERP[m + LERP_RANGE + (n + LERP_RANGE) * (LERP_RANGE * 2 + 1)] / (3.0F);
				g += p * v;
				h += o * v;
				i += v;
			}
		}

		float w = h / i;
		float yy = g / i;
		topSlideTarget = w * 0.5F - 0.125F;
		topSlideSize = yy * 0.9F + 0.1F;
		ac = topSlideTarget * 0.265625D;
		ad = 96.0D / topSlideSize;

		double xScale = 2600D;
		double yScale = 16D;
		double xzStretch = 8D;
		double yStretch = 2D;
		topSlideTarget = -10;
		topSlideSize = 3;
		double topSlideOffset = 0;
		double randomDensity = this.method_28553(x, z);
		double densityFactor = 1.0D;
		double densityOffset = -0.46875D;

		for (int y = 0; y <= this.noiseSizeY; ++y) {
			double as = this.sampleNoise(x, y * 2, z, xScale, yScale, xzStretch, yStretch);
			double at = 1.0D - y * 2.0D / this.noiseSizeY + randomDensity;
			double au = at * densityFactor + densityOffset;
			double av = (au + ac) * ad;
			if (av > 0.0D) {
				as += av * 4.0D;
			} else {
				as += av;
			}

			double ax;
			ax = (this.noiseSizeY - y - topSlideOffset) / topSlideSize;
			as = MathHelper.clampedLerp(topSlideTarget, as, ax);
			buffer[y] = as;
		}

	}

	private double method_28553(int i, int j) {
		double d = this.field_24776.sample(i * 200, 10.0D, j * 200, 1.0D, 0.0D, true);
		double f;
		if (d < 0.0D) {
			f = -d * 0.3D;
		} else {
			f = d;
		}

		double g = f * 24.575625D - 2.0D;
		return g < 0.0D ? g * 0.009486607142857142D : Math.min(g, 1.0D) * 0.006640625D;
	}

	private double sampleNoise(int x, int y, int z, double horizontalScale, double verticalScale, double horizontalStretch, double verticalStretch) {
		double d = 0.0D;
		double e = 0.0D;
		double f = 0.0D;
		double g = 1.0D;

		for (int i = 0; i < 16; ++i) {
			double h = OctavePerlinNoiseSampler.maintainPrecision(x * horizontalScale * g);
			double j = OctavePerlinNoiseSampler.maintainPrecision(y * verticalScale * g);
			double k = OctavePerlinNoiseSampler.maintainPrecision(z * horizontalScale * g);
			double l = verticalScale * g;
			PerlinNoiseSampler perlinNoiseSampler = this.lowerInterpolatedNoise.getOctave(i);
			if (perlinNoiseSampler != null) {
				d += perlinNoiseSampler.sample(h, j, k, l, y * l) / g;
			}

			PerlinNoiseSampler perlinNoiseSampler2 = this.upperInterpolatedNoise.getOctave(i);
			if (perlinNoiseSampler2 != null) {
				e += perlinNoiseSampler2.sample(h, j, k, l, y * l) / g;
			}

			if (i < 8) {
				PerlinNoiseSampler perlinNoiseSampler3 = this.interpolationNoise.getOctave(i);
				if (perlinNoiseSampler3 != null) {
					f += perlinNoiseSampler3.sample(OctavePerlinNoiseSampler.maintainPrecision(x * horizontalStretch * g), OctavePerlinNoiseSampler.maintainPrecision(y * verticalStretch * g),
							OctavePerlinNoiseSampler.maintainPrecision(z * horizontalStretch * g), verticalStretch * g, y * verticalStretch * g) / g;
				}
			}

			g /= 2.0D;
		}

		return MathHelper.clampedLerp(d / 512.0D, e / 512.0D, (f / 10.0D + 1.0D) / 2.0D);
	}

	protected BlockState getBlockState(double density, int y) {
		BlockState blockState3;
		if (density > 0.0D) {
			blockState3 = this.defaultBlock;
		} else if (y < this.getSeaLevel()) {
			blockState3 = this.defaultFluid;
		} else {
			blockState3 = AIR;
		}

		return blockState3;
	}

	private int sampleHeightmap(int x, int z, @Nullable BlockState[] states, @Nullable Predicate<BlockState> predicate) {
		int i = Math.floorDiv(x, this.horizontalNoiseResolution);
		int j = Math.floorDiv(z, this.horizontalNoiseResolution);
		int k = Math.floorMod(x, this.horizontalNoiseResolution);
		int l = Math.floorMod(z, this.horizontalNoiseResolution);
		double d = k / (double) this.horizontalNoiseResolution;
		double e = l / (double) this.horizontalNoiseResolution;
		double[][] ds = new double[][] {
				this.sampleNoiseColumn(i, j), this.sampleNoiseColumn(i, j + 1), this.sampleNoiseColumn(i + 1, j), this.sampleNoiseColumn(i + 1, j + 1)
		};

		for (int m = this.noiseSizeY - 1; m >= 0; --m) {
			double f = ds[0][m];
			double g = ds[1][m];
			double h = ds[2][m];
			double n = ds[3][m];
			double o = ds[0][m + 1];
			double p = ds[1][m + 1];
			double q = ds[2][m + 1];
			double r = ds[3][m + 1];

			for (int s = this.verticalNoiseResolution - 1; s >= 0; --s) {
				double t = s / (double) this.verticalNoiseResolution;
				double u = MathHelper.lerp3(t, d, e, f, o, h, q, g, p, n, r);
				int v = m * this.verticalNoiseResolution + s;
				BlockState blockState = this.getBlockState(u, v);
				if (states != null) {
					states[v] = blockState;
				}

				if (predicate != null && predicate.test(blockState)) {
					return v + 1;
				}
			}
		}

		return 0;
	}

	static {
		AIR = Blocks.AIR.getDefaultState();
	}
}
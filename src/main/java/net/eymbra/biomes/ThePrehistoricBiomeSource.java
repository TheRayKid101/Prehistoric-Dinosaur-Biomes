package net.eymbra.biomes;

import java.util.List;
import java.util.function.LongFunction;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.AddBambooJungleLayer;
import net.minecraft.world.biome.layer.AddClimateLayers;
import net.minecraft.world.biome.layer.AddColdClimatesLayer;
import net.minecraft.world.biome.layer.AddDeepOceanLayer;
import net.minecraft.world.biome.layer.AddEdgeBiomesLayer;
import net.minecraft.world.biome.layer.AddHillsLayer;
import net.minecraft.world.biome.layer.AddIslandLayer;
import net.minecraft.world.biome.layer.AddMushroomIslandLayer;
import net.minecraft.world.biome.layer.AddRiversLayer;
import net.minecraft.world.biome.layer.AddSunflowerPlainsLayer;
import net.minecraft.world.biome.layer.ApplyOceanTemperatureLayer;
import net.minecraft.world.biome.layer.ContinentLayer;
import net.minecraft.world.biome.layer.EaseBiomeEdgeLayer;
import net.minecraft.world.biome.layer.IncreaseEdgeCurvatureLayer;
import net.minecraft.world.biome.layer.NoiseToRiverLayer;
import net.minecraft.world.biome.layer.OceanTemperatureLayer;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.SetBaseBiomesLayer;
import net.minecraft.world.biome.layer.SimpleLandNoiseLayer;
import net.minecraft.world.biome.layer.SmoothLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.CachingLayerContext;
import net.minecraft.world.biome.layer.util.CachingLayerSampler;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

public class ThePrehistoricBiomeSource extends BiomeSource {
	// private static final ThePrehistoricBiomeSource.NoiseParameters
	// DEFAULT_NOISE_PARAMETERS = new ThePrehistoricBiomeSource.NoiseParameters(-7,
	// ImmutableList.of(1.0D, 1.0D));
	public static final Codec<ThePrehistoricBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((thePrehistoricBiomeSource) -> {
			return thePrehistoricBiomeSource.biomeRegistry;
		}), Codec.LONG.fieldOf("seed").stable().forGetter((thePrehistoricBiomeSource) -> {
			return thePrehistoricBiomeSource.seed;
		})).apply(instance, instance.stable(ThePrehistoricBiomeSource::new));
	});
	private final Registry<Biome> biomeRegistry;
	private final long seed;
//	private final ThePrehistoricBiomeSource.NoiseParameters temperatureNoiseParameters = DEFAULT_NOISE_PARAMETERS;
//	private final ThePrehistoricBiomeSource.NoiseParameters humidityNoiseParameters = DEFAULT_NOISE_PARAMETERS;
//	private final ThePrehistoricBiomeSource.NoiseParameters altitudeNoiseParameters = DEFAULT_NOISE_PARAMETERS;
//	private final ThePrehistoricBiomeSource.NoiseParameters weirdnessNoiseParameters = DEFAULT_NOISE_PARAMETERS;
//	private final DoublePerlinNoiseSampler temperatureNoise;
//	private final DoublePerlinNoiseSampler humidityNoise;
//	private final DoublePerlinNoiseSampler altitudeNoise;
//	private final DoublePerlinNoiseSampler weirdnessNoise;
//	private boolean threeDimensionalSampling = true;
//	private List<Pair<Biome.MixedNoisePoint, Supplier<Biome>>> biomePoints;
	private final PrehistoricBiomeLayerSampler biomeSampler;
	public static final List<RegistryKey<Biome>> BIOMES;

	static class NoiseParameters {
		private final int firstOctave;
		private final DoubleList amplitudes;
		public static final Codec<ThePrehistoricBiomeSource.NoiseParameters> CODEC = RecordCodecBuilder.create((instance) -> {
			return instance.group(Codec.INT.fieldOf("firstOctave").forGetter(ThePrehistoricBiomeSource.NoiseParameters::getFirstOctave), Codec.DOUBLE.listOf().fieldOf("amplitudes").forGetter(ThePrehistoricBiomeSource.NoiseParameters::getAmplitudes)).apply(instance, ThePrehistoricBiomeSource.NoiseParameters::new);
		});

		public NoiseParameters(int firstOctave, List<Double> amplitudes) {
			this.firstOctave = firstOctave;
			this.amplitudes = new DoubleArrayList(amplitudes);
		}

		public int getFirstOctave() {
			return this.firstOctave;
		}

		public DoubleList getAmplitudes() {
			return this.amplitudes;
		}
	}

	public ThePrehistoricBiomeSource(Registry<Biome> biomeRegistry, long seed) {
		this(biomeRegistry, seed, ImmutableList.of(Pair.of(new Biome.MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
			return (Biome) biomeRegistry.getOrThrow(EymbraBiomes.RAINFOREST);
		}), Pair.of(new Biome.MixedNoisePoint(0.0F, -0.5F, 0.0F, 0.0F, 0.0F), () -> {
			return (Biome) biomeRegistry.getOrThrow(EymbraBiomes.BOG);
		}), Pair.of(new Biome.MixedNoisePoint(0.4F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
			return (Biome) biomeRegistry.getOrThrow(EymbraBiomes.SNOW_MOUNTAINS);
		})));
	}

	public ThePrehistoricBiomeSource(Registry<Biome> biomeRegistry, long seed, List<Pair<Biome.MixedNoisePoint, Supplier<Biome>>> biomePoints) {
//		super(biomePoints.stream().map(Pair::getSecond));
		super(BIOMES.stream().map((registryKey) -> {
			return () -> {
				return (Biome) biomeRegistry.getOrThrow(registryKey);
			};
		}));
		this.seed = seed;
		this.biomeRegistry = biomeRegistry;
//		this.biomePoints = biomePoints;
//		this.temperatureNoise = DoublePerlinNoiseSampler.method_30846(new ChunkRandom(seed), temperatureNoiseParameters.getFirstOctave(), temperatureNoiseParameters.getAmplitudes());
//		this.humidityNoise = DoublePerlinNoiseSampler.method_30846(new ChunkRandom(seed + 1L), humidityNoiseParameters.getFirstOctave(), humidityNoiseParameters.getAmplitudes());
//		this.altitudeNoise = DoublePerlinNoiseSampler.method_30846(new ChunkRandom(seed + 2L), altitudeNoiseParameters.getFirstOctave(), altitudeNoiseParameters.getAmplitudes());
//		this.weirdnessNoise = DoublePerlinNoiseSampler.method_30846(new ChunkRandom(seed + 3L), weirdnessNoiseParameters.getFirstOctave(), weirdnessNoiseParameters.getAmplitudes());
		this.biomeSampler = BiomeLayers_build(seed, false, 4, 4);
	}

	public static PrehistoricBiomeLayerSampler BiomeLayers_build(long seed, boolean old, int biomeSize, int riverSize) {
		LayerFactory<CachingLayerSampler> layerFactory = build(old, biomeSize, riverSize, (salt) -> {
			return new CachingLayerContext(25, seed, salt);
		});
		return new PrehistoricBiomeLayerSampler(layerFactory);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(boolean old, int biomeSize, int riverSize, LongFunction<C> contextProvider) {
		LayerFactory<T> layerFactory = ContinentLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1L));
		layerFactory = ScaleLayer.FUZZY.create((LayerSampleContext) contextProvider.apply(2000L), layerFactory);
		layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1L), layerFactory);
		layerFactory = ScaleLayer.NORMAL.create((LayerSampleContext) contextProvider.apply(2001L), layerFactory);
		layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(2L), layerFactory);
		layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(50L), layerFactory);
		layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(70L), layerFactory);
		layerFactory = AddIslandLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(2L), layerFactory);
		LayerFactory<T> layerFactory2 = OceanTemperatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(2L));
		layerFactory2 = stack(2001L, ScaleLayer.NORMAL, layerFactory2, 6, contextProvider);
		layerFactory = AddColdClimatesLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(2L), layerFactory);
		layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(3L), layerFactory);
		layerFactory = AddClimateLayers.AddTemperateBiomesLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(2L), layerFactory);
		layerFactory = AddClimateLayers.AddCoolBiomesLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(2L), layerFactory);
		layerFactory = AddClimateLayers.AddSpecialBiomesLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(3L), layerFactory);
		layerFactory = ScaleLayer.NORMAL.create((LayerSampleContext) contextProvider.apply(2002L), layerFactory);
		layerFactory = ScaleLayer.NORMAL.create((LayerSampleContext) contextProvider.apply(2003L), layerFactory);
		layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(4L), layerFactory);
		layerFactory = AddMushroomIslandLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(5L), layerFactory);
		layerFactory = AddDeepOceanLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(4L), layerFactory);
		layerFactory = stack(1000L, ScaleLayer.NORMAL, layerFactory, 0, contextProvider);
		LayerFactory<T> layerFactory3 = stack(1000L, ScaleLayer.NORMAL, layerFactory, 0, contextProvider);
		layerFactory3 = SimpleLandNoiseLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(100L), layerFactory3);
		LayerFactory<T> layerFactory4 = (new SetBaseBiomesLayer(old)).create((LayerSampleContext) contextProvider.apply(200L), layerFactory);
		layerFactory4 = AddBambooJungleLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1001L), layerFactory4);
		layerFactory4 = stack(1000L, ScaleLayer.NORMAL, layerFactory4, 2, contextProvider);
		layerFactory4 = EaseBiomeEdgeLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1000L), layerFactory4);
		LayerFactory<T> layerFactory5 = stack(1000L, ScaleLayer.NORMAL, layerFactory3, 2, contextProvider);
		layerFactory4 = AddHillsLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1000L), layerFactory4, layerFactory5);
		layerFactory3 = stack(1000L, ScaleLayer.NORMAL, layerFactory3, 2, contextProvider);
		layerFactory3 = stack(1000L, ScaleLayer.NORMAL, layerFactory3, riverSize, contextProvider);
		layerFactory3 = NoiseToRiverLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1L), layerFactory3);
		layerFactory3 = SmoothLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1000L), layerFactory3);
		layerFactory4 = AddSunflowerPlainsLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1001L), layerFactory4);

		for (int i = 0; i < biomeSize; ++i) {
			layerFactory4 = ScaleLayer.NORMAL.create((LayerSampleContext) contextProvider.apply((long) (1000 + i)), layerFactory4);
			if (i == 0) {
				layerFactory4 = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(3L), layerFactory4);
			}

			if (i == 1 || biomeSize == 1) {
				layerFactory4 = AddEdgeBiomesLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1000L), layerFactory4);
			}
		}

		layerFactory4 = SmoothLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1000L), layerFactory4);
		layerFactory4 = AddRiversLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(100L), layerFactory4, layerFactory3);
		layerFactory4 = ApplyOceanTemperatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(100L), layerFactory4, layerFactory2);
		return layerFactory4;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider) {
		LayerFactory<T> layerFactory = parent;

		for (int i = 0; i < count; ++i) {
			layerFactory = layer.create((LayerSampleContext) contextProvider.apply(seed + (long) i), layerFactory);
		}

		return layerFactory;
	}

	@Environment(EnvType.CLIENT)
	public BiomeSource withSeed(long seed) {
		return new ThePrehistoricBiomeSource(null, seed);
	}

//	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
//		int i = this.threeDimensionalSampling ? biomeY : 0;
//		Biome.MixedNoisePoint mixedNoisePoint = new Biome.MixedNoisePoint((float) this.temperatureNoise.sample((double) biomeX, (double) i, (double) biomeZ), (float) this.humidityNoise.sample((double) biomeX, (double) i, (double) biomeZ), (float) this.altitudeNoise.sample((double) biomeX, (double) i, (double) biomeZ), (float) this.weirdnessNoise.sample((double) biomeX, (double) i, (double) biomeZ), 0.0F);
//		return (Biome) this.biomePoints.stream().min(Comparator.comparing((pair) -> {
//			return ((Biome.MixedNoisePoint) pair.getFirst()).calculateDistanceTo(mixedNoisePoint);
//		})).map(Pair::getSecond).map(Supplier::get).orElse(BuiltinBiomes.THE_VOID);
//	}

	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
		return this.biomeSampler.sample(this.biomeRegistry, biomeX, biomeZ);
	}

	public static float getNoiseAt(SimplexNoiseSampler simplexNoiseSampler, int i, int j) {
		int k = i / 2;
		int l = j / 2;
		int m = i % 2;
		int n = j % 2;
		float f = 100.0F - MathHelper.sqrt((float) (i * i + j * j)) * 8.0F;
		f = MathHelper.clamp(f, -100.0F, 80.0F);

		for (int o = -12; o <= 12; ++o) {
			for (int p = -12; p <= 12; ++p) {
				long q = (long) (k + o);
				long r = (long) (l + p);
				if (q * q + r * r > 4096L && simplexNoiseSampler.sample((double) q, (double) r) < -0.8999999761581421D) {
					float g = (MathHelper.abs((float) q) * 3439.0F + MathHelper.abs((float) r) * 147.0F) % 13.0F + 9.0F;
					float h = (float) (m - o * 2);
					float s = (float) (n - p * 2);
					float t = 100.0F - MathHelper.sqrt(h * h + s * s) * g;
					t = MathHelper.clamp(t, -100.0F, 80.0F);
					f = Math.max(f, t);
				}
			}
		}
		return f;
	}

	@Override
	protected Codec<? extends BiomeSource> getCodec() {
		return CODEC;
	}

	static {
		BIOMES = ImmutableList.of(EymbraBiomes.RAINFOREST, EymbraBiomes.BOG, EymbraBiomes.SNOW_MOUNTAINS, EymbraBiomes.RED_DESERT, EymbraBiomes.ISLAND, EymbraBiomes.OCEAN, EymbraBiomes.HILLS, EymbraBiomes.SWAMP, EymbraBiomes.PLAINS);
	}
}
package net.eymbra.features.trees;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import net.eymbra.blocks.EymbraBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

public class PrehistoricTreeFeature extends Feature<TreeFeatureConfig> {
	public PrehistoricTreeFeature() {
		super(TreeFeatureConfig.CODEC);
	}

	public static boolean canTreeReplace(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> {
			Block block = state.getBlock();
			return state.isAir() || state.isIn(BlockTags.LEAVES) || isSoil(block) || state.isIn(BlockTags.LOGS) || state.isIn(BlockTags.SAPLINGS) || state.isOf(Blocks.VINE) || state.isOf(Blocks.WATER);
		});
	}

	private static boolean isVine(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> {
			return state.isOf(Blocks.VINE);
		});
	}

	private static boolean isWater(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> {
			return state.isOf(Blocks.WATER);
		});
	}

	public static boolean isAirOrLeaves(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> {
			return state.isAir() || state.isIn(BlockTags.LEAVES);
		});
	}

	private static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> {
			Block block = state.getBlock();
			return isSoil(block) || block == Blocks.FARMLAND || block == EymbraBlocks.PREHISTORIC_GRASS_BLOCK || block == EymbraBlocks.PREHISTORIC_DIRT_BLOCK;
		});
	}

	private static boolean isReplaceablePlant(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> {
			Material material = state.getMaterial();
			return material == Material.REPLACEABLE_PLANT;
		});
	}

	public static void setBlockStateWithoutUpdatingNeighbors(ModifiableWorld world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state, 19);
	}

	public static boolean canReplace(ModifiableTestableWorld world, BlockPos pos) {
		return isAirOrLeaves(world, pos) || isReplaceablePlant(world, pos) || isWater(world, pos);
	}

	int r;

	private boolean generate(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> logPositions, Set<BlockPos> leavesPositions, BlockBox box, TreeFeatureConfig config) {
		int i = config.trunkPlacer.getHeight(random);
		int j = config.foliagePlacer.getRandomHeight(random, i, config);
		int k = i - j;
		int l = config.foliagePlacer.getRandomRadius(random, k);
		BlockPos blockPos2;
		if (!config.skipFluidCheck) {
			int m = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR, pos).getY();
			int n = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).getY();
			if (n - m > config.maxWaterDepth) {
				return false;
			}

			if (config.heightmap == Heightmap.Type.OCEAN_FLOOR) {
				r = m;
			} else if (config.heightmap == Heightmap.Type.WORLD_SURFACE) {
				r = n;
			} else {
				r = world.getTopPosition(config.heightmap, pos).getY();
			}

			blockPos2 = new BlockPos(pos.getX(), r, pos.getZ());
		} else {
			blockPos2 = pos;
		}

		if (blockPos2.getY() >= 1 && blockPos2.getY() + i + 1 <= 256) {
			if (!isDirtOrGrass(world, blockPos2.down())) {
				return false;
			} else {
				BlockPos.Mutable mutable = new BlockPos.Mutable();
				OptionalInt optionalInt = config.minimumSize.getMinClippedHeight();
				r = i;

				for (int s = 0; s <= i + 1; ++s) {
					int t = config.minimumSize.method_27378(i, s);

					for (int u = -t; u <= t; ++u) {
						for (int v = -t; v <= t; ++v) {
							mutable.set(blockPos2, u, s, v);
							if (!canTreeReplace(world, mutable) || !config.ignoreVines && isVine(world, mutable)) {
								if (!optionalInt.isPresent() || s - 1 < optionalInt.getAsInt() + 1) {
									return false;
								}

								r = s - 2;
								break;
							}
						}
					}
				}

				List<FoliagePlacer.TreeNode> list = config.trunkPlacer.generate(world, random, r, blockPos2, logPositions, box, config);
				list.forEach((treeNode) -> {
					config.foliagePlacer.generate(world, random, config, r, treeNode, j, l, leavesPositions, box);
				});

				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
		setBlockStateWithoutUpdatingNeighbors(world, pos, state);
	}

	@Override
	public boolean generate(StructureWorldAccess structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, TreeFeatureConfig PrehistoricTreeFeatureConfig) {
		Set<BlockPos> set = Sets.newHashSet();
		Set<BlockPos> set2 = Sets.newHashSet();
		Set<BlockPos> set3 = Sets.newHashSet();
		BlockBox blockBox = BlockBox.empty();
		boolean bl = this.generate(structureAccessor, random, blockPos, set, set2, blockBox, PrehistoricTreeFeatureConfig);
		if (blockBox.minX <= blockBox.maxX && bl && !set.isEmpty()) {
			if (!PrehistoricTreeFeatureConfig.decorators.isEmpty()) {
				List<BlockPos> list = Lists.newArrayList(set);
				List<BlockPos> list2 = Lists.newArrayList(set2);
				list.sort(Comparator.comparingInt(Vec3i::getY));
				list2.sort(Comparator.comparingInt(Vec3i::getY));
				PrehistoricTreeFeatureConfig.decorators.forEach((decorator) -> {
					decorator.generate(structureAccessor, random, list, list2, set3, blockBox);
				});
			}

			VoxelSet voxelSet = this.placeLogsAndLeaves(structureAccessor, blockBox, set, set3);
			Structure.updateCorner(structureAccessor, 3, voxelSet, blockBox.minX, blockBox.minY, blockBox.minZ);
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	private VoxelSet placeLogsAndLeaves(WorldAccess world, BlockBox box, Set<BlockPos> logs, Set<BlockPos> leaves) {
		List<Set<BlockPos>> list = Lists.newArrayList();
		VoxelSet voxelSet = new BitSetVoxelSet(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());

		for (int j = 0; j < 6; ++j) {
			list.add(Sets.newHashSet());
		}

		BlockPos.Mutable mutable = new BlockPos.Mutable();
		Iterator var9 = Lists.newArrayList(leaves).iterator();

		BlockPos blockPos2;
		while (var9.hasNext()) {
			blockPos2 = (BlockPos) var9.next();
			if (box.contains(blockPos2)) {
				voxelSet.set(blockPos2.getX() - box.minX, blockPos2.getY() - box.minY, blockPos2.getZ() - box.minZ, true, true);
			}
		}

		var9 = Lists.newArrayList(logs).iterator();

		while (var9.hasNext()) {
			blockPos2 = (BlockPos) var9.next();
			if (box.contains(blockPos2)) {
				voxelSet.set(blockPos2.getX() - box.minX, blockPos2.getY() - box.minY, blockPos2.getZ() - box.minZ, true, true);
			}

			Direction[] var11 = Direction.values();
			int var12 = var11.length;

			for (int var13 = 0; var13 < var12; ++var13) {
				Direction direction = var11[var13];
				mutable.set(blockPos2, direction);
				if (!logs.contains(mutable)) {
					BlockState blockState = world.getBlockState(mutable);
					if (blockState.contains(Properties.DISTANCE_1_7)) {
						((Set) list.get(0)).add(mutable.toImmutable());
						setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState.with(Properties.DISTANCE_1_7, 1));
						if (box.contains(mutable)) {
							voxelSet.set(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
						}
					}
				}
			}
		}

		for (int k = 1; k < 6; ++k) {
			Set<BlockPos> set = list.get(k - 1);
			Set<BlockPos> set2 = list.get(k);
			Iterator var25 = set.iterator();

			while (var25.hasNext()) {
				BlockPos blockPos3 = (BlockPos) var25.next();
				if (box.contains(blockPos3)) {
					voxelSet.set(blockPos3.getX() - box.minX, blockPos3.getY() - box.minY, blockPos3.getZ() - box.minZ, true, true);
				}

				Direction[] var27 = Direction.values();
				int var28 = var27.length;

				for (int var16 = 0; var16 < var28; ++var16) {
					Direction direction2 = var27[var16];
					mutable.set(blockPos3, direction2);
					if (!set.contains(mutable) && !set2.contains(mutable)) {
						BlockState blockState2 = world.getBlockState(mutable);
						if (blockState2.contains(Properties.DISTANCE_1_7)) {
							int l = blockState2.get(Properties.DISTANCE_1_7);
							if (l > k + 1) {
								BlockState blockState3 = blockState2.with(Properties.DISTANCE_1_7, k + 1);
								setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState3);
								if (box.contains(mutable)) {
									voxelSet.set(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
								}

								set2.add(mutable.toImmutable());
							}
						}
					}
				}
			}
		}

		return voxelSet;
	}

	// @Override
	// public boolean generate(StructureWorldAccess world, ChunkGenerator
	// chunkGenerator, Random random, BlockPos pos, TreeFeatureConfig
	// config) {
	// return false;
	// }
}

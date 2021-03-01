package net.eymbra.features.trees;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.eymbra.blocks.EymbraBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class PrehistoricGiantTrunkPlacer extends TrunkPlacer {
	public static final Codec<PrehistoricGiantTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return method_28904(instance).apply(instance, PrehistoricGiantTrunkPlacer::new);
	});

	public PrehistoricGiantTrunkPlacer(int i, int j, int k) {
		super(i, j, k);
	}

	private static boolean override_method_27403(TestableWorld testableWorld, BlockPos blockPos) {
		return testableWorld.testBlockState(blockPos, (blockState) -> {
			Block block = blockState.getBlock();
			return (PrehistoricGiantTrunkPlacer.isSoil(block) && !blockState.isOf(Blocks.GRASS_BLOCK) && !blockState.isOf(Blocks.MYCELIUM)) && !blockState.isOf(EymbraBlocks.PREHISTORIC_GRASS_BLOCK);
		});
	}

	public static boolean isSoil(Block block) {
		return block == Blocks.DIRT || block == Blocks.GRASS_BLOCK || block == Blocks.PODZOL || block == Blocks.COARSE_DIRT || block == Blocks.MYCELIUM || block == EymbraBlocks.PREHISTORIC_GRASS_BLOCK || block == EymbraBlocks.PREHISTORIC_DIRT_BLOCK;
	}

	protected static void override_method_27400(ModifiableTestableWorld modifiableTestableWorld, BlockPos blockPos) {
		if (!override_method_27403(modifiableTestableWorld, blockPos)) {
			TreeFeature.setBlockStateWithoutUpdatingNeighbors(modifiableTestableWorld, blockPos, EymbraBlocks.PREHISTORIC_DIRT_BLOCK_STATE);
		}
	}

	@Override
	public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> set, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
		BlockPos blockPos = pos.down();
		override_method_27400(world, blockPos);
		override_method_27400(world, blockPos.east());
		override_method_27400(world, blockPos.south());
		override_method_27400(world, blockPos.south().east());

		override_method_27400(world, blockPos.add(2, 0, 0));
		override_method_27400(world, blockPos.add(2, 0, 1));

		override_method_27400(world, blockPos.add(0, 0, 2));
		override_method_27400(world, blockPos.add(1, 0, 2));

		override_method_27400(world, blockPos.add(0, 0, -1));
		override_method_27400(world, blockPos.add(1, 0, -1));

		override_method_27400(world, blockPos.add(-1, 0, 0));
		override_method_27400(world, blockPos.add(-1, 0, 1));

		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for (int i = 0; i < trunkHeight; ++i) {
			boolean offset = i < 2 ? false : true;

			if (offset) {
				method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, 0, i, 0);
				if (i < trunkHeight - 1) {
					method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, 1, i, 0);
					method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, 1, i, 1);
					method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, 0, i, 1);
				}
			} else {
				method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, 2, i, 0);
				method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, 2, i, 1);

				method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, 0, i, 2);
				method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, 1, i, 2);

				method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, 0, i, -1);
				method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, 1, i, -1);

				method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, -1, i, 0);
				method_27399(world, random, mutable, set, blockBox, treeFeatureConfig, pos, -1, i, 1);

			}
		}

		return ImmutableList.of(new FoliagePlacer.TreeNode(pos.up(trunkHeight), 0, true));
	}

	private static void method_27399(ModifiableTestableWorld modifiableTestableWorld, Random random, BlockPos.Mutable mutable, Set<BlockPos> set, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig, BlockPos blockPos, int i, int j, int k) {
		mutable.set(blockPos, i, j, k);
		trySetState(modifiableTestableWorld, random, mutable, set, blockBox, treeFeatureConfig);
	}

	@Override
	protected TrunkPlacerType<?> getType() {
		return EymbraTrunkPlacerType.RAINFOREST_TRUNK_PLACER;
	}
}
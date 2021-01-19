package net.eymbra.blocks;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ModifiableGrassBlock extends ModifiableSpreadableBlock implements Fertilizable {
	public ModifiableGrassBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return world.getBlockState(pos.up()).isAir();
	}

	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	//@SuppressWarnings({ "rawtypes", "unchecked" })
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {return;
//		BlockPos blockPos = pos.up();
//		BlockState blockState = EymbraBlocks.PREHISTORIC_GRASS_BLOCK_STATE;
//
//		label48: for (int i = 0; i < 128; ++i) {
//			BlockPos blockPos2 = blockPos;
//
//			for (int j = 0; j < i / 16; ++j) {
//				blockPos2 = blockPos2.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
//				if (!world.getBlockState(blockPos2.down()).isOf(this) || world.getBlockState(blockPos2).isFullCube(world, blockPos2)) {
//					continue label48;
//				}
//			}
//
//			BlockState blockState2 = world.getBlockState(blockPos2);
//			if (blockState2.isOf(blockState.getBlock()) && random.nextInt(10) == 0) {
//				((Fertilizable) blockState.getBlock()).grow(world, random, blockPos2, blockState2);
//			}
//
//			if (blockState2.isAir()) {
//				BlockState blockState4;
//				if (random.nextInt(8) == 0) {
//					List<ConfiguredFeature<?, ?>> list = world.getBiome(blockPos2).getGenerationSettings().getFlowerFeatures();
//					if (list.isEmpty()) {
//						continue;
//					}
//
//					ConfiguredFeature<?, ?> configuredFeature = (ConfiguredFeature) list.get(0);
//					FlowerFeature flowerFeature = (FlowerFeature) configuredFeature.feature;
//					blockState4 = flowerFeature.getFlowerState(random, blockPos2, configuredFeature.getConfig());
//				} else {
//					blockState4 = blockState;
//				}
//
//				if (blockState4.canPlaceAt(world, blockPos2)) {
//					world.setBlockState(blockPos2, blockState4, 3);
//				}
//			}
//		}

	}
}

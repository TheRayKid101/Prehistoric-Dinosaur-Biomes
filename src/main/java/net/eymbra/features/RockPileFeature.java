package net.eymbra.features;

import java.util.Iterator;
import java.util.Random;
import com.mojang.serialization.Codec;
import net.eymbra.blocks.EymbraBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;

public class RockPileFeature extends Feature<SingleStateFeatureConfig> {
	public RockPileFeature(Codec<SingleStateFeatureConfig> codec) {
		super(codec);
	}

	@SuppressWarnings("rawtypes")
	public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, SingleStateFeatureConfig singleStateFeatureConfig) {
		for (; blockPos.getY() > 3; blockPos = blockPos.down()) {
			if (!structureWorldAccess.isAir(blockPos.down())) {
				Block block = structureWorldAccess.getBlockState(blockPos.down()).getBlock();
				if (block == EymbraBlocks.PREHISTORIC_GRASS_BLOCK || block == EymbraBlocks.PREHISTORIC_DIRT_BLOCK) {
					break;
				}
			}
		}

		if (blockPos.getY() <= 3) {
			return false;
		} else {
			for (int i = 0; i < 3; ++i) {
				int j = random.nextInt(2);
				int k = random.nextInt(2);
				int l = random.nextInt(2);
				float f = (float) (j + k + l) * 0.333F + 0.5F;
				Iterator var11 = BlockPos.iterate(blockPos.add(-j, -k, -l), blockPos.add(j, k, l)).iterator();

				while (var11.hasNext()) {
					BlockPos blockPos2 = (BlockPos) var11.next();
					if (blockPos2.getSquaredDistance(blockPos) <= (double) (f * f)) {
						structureWorldAccess.setBlockState(blockPos2, singleStateFeatureConfig.state, 4);
					}
				}

				blockPos = blockPos.add(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
			}

			return true;
		}
	}
}

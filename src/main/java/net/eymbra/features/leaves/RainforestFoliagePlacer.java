package net.eymbra.features.leaves;

import java.util.Random;
import java.util.Set;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.eymbra.blocks.EymbraBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class RainforestFoliagePlacer extends FoliagePlacer {
	public static final Codec<RainforestFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return fillFoliagePlacerFields(instance).and(Codec.INT.fieldOf("height").forGetter((jungleFoliagePlacer) -> {
			return jungleFoliagePlacer.height;
		})).apply(instance, RainforestFoliagePlacer::new);
	});
	protected final int height;

	public RainforestFoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset, int height) {
		super(radius, offset);
		this.height = height;
	}

	@Override
	protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int i, BlockBox blockBox) {
		int j = treeNode.isGiantTrunk() ? foliageHeight : 1 + random.nextInt(2);

		// Bottom
		if (isTrunkBlock(world, treeNode.getCenter().down(trunkHeight / 2))) {
			this.generateSquare(world, random, config, treeNode.getCenter().down(trunkHeight), 2, leaves, 2, treeNode.isGiantTrunk(), blockBox);
			this.generateSquare(world, random, config, treeNode.getCenter().down(trunkHeight), 1, leaves, 3, treeNode.isGiantTrunk(), blockBox);
		}

		// Middle
		if (isTrunkBlock(world, treeNode.getCenter().down(trunkHeight / 2))) {
			this.generateSquare(world, random, config, treeNode.getCenter().down(trunkHeight / 2), 2, leaves, 1, treeNode.isGiantTrunk(), blockBox);
			this.generateSquare(world, random, config, treeNode.getCenter().down(trunkHeight / 2), 3, leaves, 0, treeNode.isGiantTrunk(), blockBox);
		}

		// Top
		for (int k = i; k >= i - j; --k) {
			int l = radius + treeNode.getFoliageRadius() + 1 - k;
			this.generateSquare(world, random, config, treeNode.getCenter(), l, leaves, k, treeNode.isGiantTrunk(), blockBox);
		}
	}

	protected static boolean isTrunkBlock(ModifiableTestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, state -> {
			Block block = state.getBlock();
			return block == EymbraBlocks.PREHISTORIC_LEPIDODENDRALES_LOG;
		});
	}

	public int getHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
		return this.height;
	}

	@Override
	protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
		if (baseHeight + dy >= 7) {
			return true;
		} else {
			return baseHeight * baseHeight + dy * dy > dz * dz;
		}
	}

	@Override
	protected FoliagePlacerType<?> getType() {
		return EymbraFoliagePlacerType.RAINFOREST_FOLIAGE_PLACER;
	}

	@Override
	public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
		return this.height;
	}
}

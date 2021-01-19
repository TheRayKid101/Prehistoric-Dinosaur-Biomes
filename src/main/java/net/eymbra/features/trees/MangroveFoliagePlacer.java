package net.eymbra.features.trees;

import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.features.leaves.EymbraFoliagePlacerType;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class MangroveFoliagePlacer extends FoliagePlacer {
	public static final Codec<MangroveFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return fillFoliagePlacerFields(instance).and(Codec.INT.fieldOf("height").forGetter((jungleFoliagePlacer) -> {
			return jungleFoliagePlacer.height;
		})).apply(instance, MangroveFoliagePlacer::new);
	});
	protected final int height;

	public MangroveFoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset, int height) {
		super(radius, offset);
		this.height = height;
	}

	@Override
	protected FoliagePlacerType<?> getType() {
		return EymbraFoliagePlacerType.MANGROVE_FOLIAGE_PLACER;
	}

	@Override
	protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int offset, BlockBox box) {
		BlockPos blockPos = treeNode.getCenter().up(offset);
		boolean bl = treeNode.isGiantTrunk();
		if (bl) {
			this.generateSquare(world, random, config, blockPos, radius + 2, leaves, -1, bl, box);
			this.generateSquare(world, random, config, blockPos.west(), radius + 3, leaves, 0, bl, box);
			this.generateSquare(world, random, config, blockPos, radius + 2, leaves, 1, bl, box);
			if (random.nextBoolean()) {
				this.generateSquare(world, random, config, blockPos, radius, leaves, 2, bl, box);
			}
		} else {
			this.generateSquare(world, random, config, blockPos, radius + 2, leaves, -1, bl, box);
			this.generateSquare(world, random, config, blockPos, radius + 1, leaves, 0, bl, box);
		}

	}
	
	protected static boolean isTrunkBlock(ModifiableTestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, state -> {
			Block block = state.getBlock();
			return block == EymbraBlocks.PREHISTORIC_MANGROVE_LOG;
		});
	}

	protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
		if (baseHeight + dy >= 7) {
			return true;
		} else {
			return baseHeight * baseHeight + dy * dy > dz * dz;
		}
	}

	@Override
	public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
		return this.height;
	}
}
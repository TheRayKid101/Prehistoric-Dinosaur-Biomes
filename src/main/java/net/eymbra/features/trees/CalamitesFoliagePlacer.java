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
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class CalamitesFoliagePlacer extends FoliagePlacer {
	public static final Codec<CalamitesFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return fillFoliagePlacerFields(instance).and(Codec.INT.fieldOf("height").forGetter((jungleFoliagePlacer) -> {
			return jungleFoliagePlacer.height;
		})).apply(instance, CalamitesFoliagePlacer::new);
	});
	protected final int height;

	public CalamitesFoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset, int height) {
		super(radius, offset);
		this.height = height;
	}

	@Override
	protected FoliagePlacerType<?> getType() {
		return EymbraFoliagePlacerType.CALAMITES_FOLIAGE_PLACER;
	}

	@Override
	protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int offset, BlockBox box) {
		BlockPos blockPos = treeNode.getCenter().down(4).north(3).west(3);
		this.generateSq(world, random, config, blockPos, 6, leaves, foliageHeight, box);
		this.generateSq(world, random, config, blockPos.east(1).south(1), 4, leaves, foliageHeight + 1, box);
		this.generateSq(world, random, config, blockPos.east(2).south(2), 2, leaves, foliageHeight + 2, box);
		
		this.generateDot(world, random, config, blockPos, radius, leaves, box);
		this.generateDot(world, random, config, blockPos.north(), radius, leaves, box);
		this.generateDot(world, random, config, blockPos.south(), radius, leaves, box);
		this.generateDot(world, random, config, blockPos.west(), radius, leaves, box);
		this.generateDot(world, random, config, blockPos.east(), radius, leaves, box);
	}

	public void generateDot(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, BlockPos blockPos, int radius, Set<BlockPos> leaves, BlockBox box) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		mutable.set((Vec3i) blockPos.east(3).south(3).up(5));
		world.setBlockState(mutable, config.leavesProvider.getBlockState(random, mutable), 19);
		box.encompass(new BlockBox(mutable, mutable));
		leaves.add(mutable.toImmutable());
	}
	
	/**
	 * Generates a square of leaves with the given radius. Sub-classes can use the
	 * method {@code isInvalidForLeaves} to exclude certain positions, such as
	 * corners.
	 */
	protected void generateSq(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, BlockPos pos, int radius, Set<BlockPos> leaves, int y, BlockBox box) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for (int j = 0; j <= radius; j++) {
			for (int k = 0; k <= radius; k++) {
				if (!this.isPositionInvalid(random, j, y, k, radius, true) || true) {
					mutable.set((Vec3i) pos, j, y, k);
					if (TreeFeature.canReplace(world, mutable)) {
						world.setBlockState(mutable, config.leavesProvider.getBlockState(random, mutable), 19);
						box.encompass(new BlockBox(mutable, mutable));
						leaves.add(mutable.toImmutable());
					}
				}
			}
		}
	}

	protected static boolean isTrunkBlock(ModifiableTestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, state -> {
			Block block = state.getBlock();
			return block == EymbraBlocks.PREHISTORIC_CALAMITES_LOG;
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
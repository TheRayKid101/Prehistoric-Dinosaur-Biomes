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

public class DarkwoodFoliagePlacer extends FoliagePlacer {
	public static final Codec<DarkwoodFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return fillFoliagePlacerFields(instance).and(Codec.INT.fieldOf("height").forGetter((jungleFoliagePlacer) -> {
			return jungleFoliagePlacer.height;
		})).apply(instance, DarkwoodFoliagePlacer::new);
	});
	protected final int height;

	public DarkwoodFoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset, int height) {
		super(radius, offset);
		this.height = height;
	}

	@Override
	protected FoliagePlacerType<?> getType() {
		return EymbraFoliagePlacerType.DARKWOOD_FOLIAGE_PLACER;
	}

	@Override
	protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int offset, BlockBox box) {
		BlockPos blockPos = treeNode.getCenter().up(offset);
		boolean bl = treeNode.isGiantTrunk();
		if (bl) {
			this.generatePatch(world, random, config, blockPos, 0, leaves, bl, box);
			this.generatePatch(world, random, config, blockPos, 1, leaves, bl, box);
			this.generatePatch(world, random, config, blockPos, 2, leaves, bl, box);
			this.generatePatch(world, random, config, blockPos, 3, leaves, bl, box);
			this.generateLeaf(world, random, config, blockPos.up(4), leaves, bl, box);
			
			//this.generateSquare(world, random, config, blockPos, radius + 2, leaves, -1, bl, box);
			//this.generateSquare(world, random, config, blockPos.west(), radius + 3, leaves, 0, bl, box);
			//this.generateSquare(world, random, config, blockPos, radius + 2, leaves, 1, bl, box);
			//if (random.nextBoolean()) {
			//	this.generateSquare(world, random, config, blockPos, radius, leaves, 2, bl, box);
			//}
		} else {
			//this.generateSquare(world, random, config, blockPos, radius + 2, leaves, -1, bl, box);
			//this.generateLeaf(world, random, config, blockPos, leaves, bl, box);
		}

	}

	private void generatePatch(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, BlockPos blockPos, int distance, Set<BlockPos> leaves, boolean bl, BlockBox box) {
		//Patch
		this.generateLeaf(world, random, config, blockPos.north().up(distance), leaves, bl, box);
		this.generateLeaf(world, random, config, blockPos.west().up(distance), leaves, bl, box);
		this.generateLeaf(world, random, config, blockPos.east().up(distance), leaves, bl, box);
		this.generateLeaf(world, random, config, blockPos.south().up(distance), leaves, bl, box);
	}
	
	private void generateLeaf(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, BlockPos blockPos, Set<BlockPos> leaves, boolean bl, BlockBox box) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		
		mutable.set((Vec3i) blockPos);
		if (TreeFeature.canReplace(world, mutable)) {
			world.setBlockState(mutable, config.leavesProvider.getBlockState(random, mutable), 19);
			box.encompass(new BlockBox(mutable, mutable));
			leaves.add(mutable.toImmutable());
		}
	}

	protected static boolean isTrunkBlock(ModifiableTestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, state -> {
			Block block = state.getBlock();
			return block == EymbraBlocks.PREHISTORIC_DARKWOOD_LOG;
		});
	}

	protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
		return false;
	}

	@Override
	public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
		return this.height + 5;
	}
}
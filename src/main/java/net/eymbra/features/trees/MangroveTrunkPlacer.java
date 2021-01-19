package net.eymbra.features.trees;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

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
import net.minecraft.world.gen.foliage.FoliagePlacer.TreeNode;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class MangroveTrunkPlacer extends TrunkPlacer {
	public static final Codec<MangroveTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return method_28904(instance).apply(instance, MangroveTrunkPlacer::new);
	});
	protected int baseHeight;
	
	public MangroveTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.baseHeight = baseHeight;
	}

	@Override
	protected TrunkPlacerType<?> getType() {
		return EymbraTrunkPlacerType.MANGROVE_TRUNK_PLACER;
	}
	
	private static boolean override_method_27403(TestableWorld testableWorld, BlockPos blockPos) {
		return testableWorld.testBlockState(blockPos, (blockState) -> {
			Block block = blockState.getBlock();
			return (PrehistoricGiantTrunkPlacer.isSoil(block) && !blockState.isOf(Blocks.GRASS_BLOCK) && !blockState.isOf(Blocks.MYCELIUM)) && !blockState.isOf(EymbraBlocks.PREHISTORIC_GRASS_BLOCK);
		});
	}
	
	protected static void setToDirt(ModifiableTestableWorld modifiableTestableWorld, BlockPos blockPos) {
		if (!override_method_27403(modifiableTestableWorld, blockPos)) {
			TreeFeature.setBlockStateWithoutUpdatingNeighbors(modifiableTestableWorld, blockPos, EymbraBlocks.PREHISTORIC_DIRT_BLOCK_STATE);
		}
	}
	
	@Override
	public List<TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config) {
		int iDown = 0;
		int var3 = pos.getX();
		int var4 = pos.getY();
		int var5 = pos.getZ();
		
		int var6 = config.trunkPlacer.getHeight(random);
		
		List<FoliagePlacer.TreeNode> list = Lists.newArrayList();

		int p_225557_1_3;
		for (p_225557_1_3 = var4 - 3 + var6; p_225557_1_3 <= var4 + var6; p_225557_1_3++)
			;
		for (p_225557_1_3 = 0; p_225557_1_3 < var6; p_225557_1_3++) {
			getAndSetState(world, random, new BlockPos(var3, var4 + p_225557_1_3 - iDown, var5), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 - 1, var4 + p_225557_1_3 - iDown, var5), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 + 1, var4 + p_225557_1_3 - iDown, var5), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3, var4 + p_225557_1_3 - iDown, var5 - 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3, var4 + p_225557_1_3 - iDown, var5 + 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 - 1, var4 - iDown, var5 - 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 + 1, var4 - iDown, var5 + 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 - 1, var4 - iDown, var5 + 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 + 1, var4 - iDown, var5 - 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 - 1, var4 - iDown + 1, var5 - 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 + 1, var4 - iDown + 1, var5 + 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 - 1, var4 - iDown + 1, var5 + 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 + 1, var4 - iDown + 1, var5 - 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 - 2, var4 - iDown, var5), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 + 2, var4 - iDown, var5), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3, var4 - iDown, var5 - 2), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3, var4 - iDown, var5 + 2), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 - 1, var4 - iDown + var6 - 4, var5 - 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 + 1, var4 - iDown + var6 - 4, var5 + 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 - 1, var4 - iDown + var6 - 4, var5 + 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 + 1, var4 - iDown + var6 - 4, var5 - 1), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 - 2, var4 - iDown + var6 - 4, var5), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 + 2, var4 - iDown + var6 - 4, var5), placedStates, box, config);

			setToDirt(world, new BlockPos(var3 - 1, var4 - iDown - 1, var5 - 1));
			setToDirt(world, new BlockPos(var3 + 1, var4 - iDown - 1, var5 + 1));
			setToDirt(world, new BlockPos(var3 - 1, var4 - iDown - 1, var5 + 1));
			setToDirt(world, new BlockPos(var3 + 1, var4 - iDown - 1, var5 - 1));
			setToDirt(world, new BlockPos(var3 - 2, var4 - iDown - 1, var5));
			setToDirt(world, new BlockPos(var3 + 2, var4 - iDown - 1, var5));
			setToDirt(world, new BlockPos(var3, var4 - iDown - 1, var5 - 2));
			setToDirt(world, new BlockPos(var3, var4 - iDown - 1, var5 + 2));
			setToDirt(world, new BlockPos(var3 + 1, var4 - iDown - 1, var5));
			setToDirt(world, new BlockPos(var3 - 1, var4 - iDown - 1, var5));
			setToDirt(world, new BlockPos(var3, var4 - iDown - 1, var5 + 1));
			setToDirt(world, new BlockPos(var3, var4 - iDown - 1, var5 - 1));
			setToDirt(world, new BlockPos(var3, var4 - iDown - 1, var5));

//			setToDirt(world, new BlockPos(var3 - 1, var4 - iDown - 2, var5 - 1));
//			setToDirt(world, new BlockPos(var3 + 1, var4 - iDown - 2, var5 + 1));
//			setToDirt(world, new BlockPos(var3 - 1, var4 - iDown - 2, var5 + 1));
//			setToDirt(world, new BlockPos(var3 + 1, var4 - iDown - 2, var5 - 1));
//			setToDirt(world, new BlockPos(var3 - 2, var4 - iDown - 2, var5));
//			setToDirt(world, new BlockPos(var3 + 2, var4 - iDown - 2, var5));
//			setToDirt(world, new BlockPos(var3, var4 - iDown - 2, var5 - 2));
//			setToDirt(world, new BlockPos(var3, var4 - iDown - 2, var5 + 2));
//			setToDirt(world, new BlockPos(var3 + 1, var4 - iDown - 2, var5));
//			setToDirt(world, new BlockPos(var3 - 1, var4 - iDown - 2, var5));
//			setToDirt(world, new BlockPos(var3, var4 - iDown - 2, var5 + 1));
//			setToDirt(world, new BlockPos(var3, var4 - iDown - 2, var5 - 1));
//			setToDirt(world, new BlockPos(var3, var4 - iDown - 2, var5));
			
			getAndSetState(world, random, new BlockPos(var3, var4 - iDown + var6 - 4, var5 - 2), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3, var4 - iDown + var6 - 4, var5 + 2), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 - 3, var4 - iDown + var6 - 3, var5), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3 + 3, var4 - iDown + var6 - 3, var5), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3, var4 - iDown + var6 - 3, var5 - 3), placedStates, box, config);
			getAndSetState(world, random, new BlockPos(var3, var4 - iDown + var6 - 3, var5 + 3), placedStates, box, config);
		}
		
		list.add(new FoliagePlacer.TreeNode(new BlockPos(var3, var4 + p_225557_1_3 - iDown - 1, var5), 0, true));

		return list;
	}
	
	protected int func_227256_a_(Random p_227256_1_, TreeFeatureConfig p_227256_2_, int var6) {
		int i = p_227256_1_.nextInt(3) + var6;
		if (2 > 1) {
			i += p_227256_1_.nextInt(2);
		}
		
		return i;
	}
}
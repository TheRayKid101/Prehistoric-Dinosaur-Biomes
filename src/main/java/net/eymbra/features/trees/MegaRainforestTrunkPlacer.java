package net.eymbra.features.trees;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

public class MegaRainforestTrunkPlacer extends PrehistoricGiantTrunkPlacer {
	public static final Codec<MegaRainforestTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return method_28904(instance).apply(instance, MegaRainforestTrunkPlacer::new);
	});

	public MegaRainforestTrunkPlacer(int i, int j, int k) {
		super(i, j, k);
	}

	@Override
	public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> set, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
		List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
		list.addAll(super.generate(world, random, trunkHeight, pos, set, blockBox, treeFeatureConfig));

		for (int i = trunkHeight - 2 - random.nextInt(4); i > trunkHeight / 2; i -= 2 + random.nextInt(4)) {
			float f = random.nextFloat() * 6.2831855F;
			int j = 0;
			int k = 0;

			for (int l = 0; l < 5; ++l) {
				j = (int) (1.5F + MathHelper.cos(f) * l);
				k = (int) (1.5F + MathHelper.sin(f) * l);
				BlockPos blockPos = pos.add(j, i - 3 + l / 2, k);
				getAndSetState(world, random, blockPos, set, blockBox, treeFeatureConfig);
			}

			list.add(new FoliagePlacer.TreeNode(pos.add(j, i, k), -2, false));
		}

		return list;
	}
}
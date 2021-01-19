package net.eymbra.generator;

import java.util.Random;
import net.eymbra.features.EymbraDefaultFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

public class DarkwoodSaplingGenerator extends SaplingGenerator {
	@SuppressWarnings("unchecked")
	@Nullable
	protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
		return (@Nullable ConfiguredFeature<TreeFeatureConfig, ?>) EymbraDefaultFeatures.DARKWOOD_TREE;
	}
}
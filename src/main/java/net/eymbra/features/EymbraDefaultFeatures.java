package net.eymbra.features;

import com.google.common.collect.ImmutableList;
import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.features.leaves.RainforestFoliagePlacer;
import net.eymbra.features.trees.CalamitesFoliagePlacer;
import net.eymbra.features.trees.CalamitesTrunkPlacer;
import net.eymbra.features.trees.DarkwoodFoliagePlacer;
import net.eymbra.features.trees.DarkwoodTrunkPlacer;
import net.eymbra.features.trees.MangroveFoliagePlacer;
import net.eymbra.features.trees.MangroveTrunkPlacer;
import net.eymbra.features.trees.MegaRainforestTrunkPlacer;
import net.eymbra.features.trees.PrehistoricTreeFeature;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.GenerationSettings.Builder;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.tree.BeehiveTreeDecorator;
import net.minecraft.world.gen.tree.LeaveVineTreeDecorator;
import net.minecraft.world.gen.tree.TrunkVineTreeDecorator;

public class EymbraDefaultFeatures {
	public static final BeehiveTreeDecorator MORE_BEEHIVES_TREES = new BeehiveTreeDecorator(0.05F);

	public static final PrehistoricTreeFeature PREHISTORIC_TREE = register("prehistoric_tree", new PrehistoricTreeFeature());

	public static final TreeFeatureConfig MEGA_RAINFOREST_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(EymbraBlocks.PREHISTORIC_LEPIDODENDRALES_LOG_STATE),
			new SimpleBlockStateProvider(EymbraBlocks.PREHISTORIC_LEPIDODENDRALES_LEAVES_STATE), new RainforestFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 2), new MegaRainforestTrunkPlacer(5, 1, 19),
			new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(MORE_BEEHIVES_TREES, TrunkVineTreeDecorator.INSTANCE, LeaveVineTreeDecorator.INSTANCE)).build();

	public static final ConfiguredFeature<?, ?> MEGA_RAINFOREST_TREE = EymbraDefaultFeatures.registerTree("rainforest_tree",
			MEGA_RAINFOREST_TREE_CONFIG.setTreeDecorators(ImmutableList.of(MORE_BEEHIVES_TREES, TrunkVineTreeDecorator.INSTANCE, LeaveVineTreeDecorator.INSTANCE)));

	public static final ConfiguredFeature<?, ?> MEGA_TREES_RAINFOREST = register("mega_rainforest_tree",
			Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(EymbraDefaultFeatures.MEGA_RAINFOREST_TREE.withChance(1.0F)), EymbraDefaultFeatures.MEGA_RAINFOREST_TREE)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
					.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(50, 0.5F, 1))));

	public static final RandomPatchFeatureConfig SHORT_BUSH_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(EymbraBlocks.PREHISTORIC_SHORT_BUSH_STATE), SimpleBlockPlacer.INSTANCE)).tries(32).build();
	@SuppressWarnings({
			"unchecked", "rawtypes"
	})
	public static final ConfiguredFeature<?, ?> PATCH_SHORT_BUSH = register("patch_short_bush",
			(ConfiguredFeature) Feature.RANDOM_PATCH.configure(EymbraDefaultFeatures.SHORT_BUSH_CONFIG).decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(7));

	public static final RandomPatchFeatureConfig DEAD_BUSH_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(EymbraBlocks.PREHISTORIC_DEAD_BUSH_STATE), SimpleBlockPlacer.INSTANCE)).tries(32).build();

	@SuppressWarnings({
			"unchecked", "rawtypes"
	})
	public static final ConfiguredFeature<?, ?> PATCH_DEAD_BUSH = register("patch_dead_bush",
			(ConfiguredFeature) Feature.RANDOM_PATCH.configure(EymbraDefaultFeatures.DEAD_BUSH_CONFIG).decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(7));

	public static final TreeFeatureConfig MANGROVE_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(EymbraBlocks.PREHISTORIC_MANGROVE_LOG_STATE), new SimpleBlockStateProvider(EymbraBlocks.PREHISTORIC_MANGROVE_LEAVES_STATE),
			new MangroveFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 2), new MangroveTrunkPlacer(3, 2, 6), new TwoLayersFeatureSize(1, 1, 2)))
					.decorators(ImmutableList.of(MORE_BEEHIVES_TREES, TrunkVineTreeDecorator.INSTANCE, LeaveVineTreeDecorator.INSTANCE)).build();

	public static final ConfiguredFeature<?, ?> MANGROVE_TREE = EymbraDefaultFeatures.registerTree("mangrove_tree",
			MANGROVE_TREE_CONFIG.setTreeDecorators(ImmutableList.of(MORE_BEEHIVES_TREES, TrunkVineTreeDecorator.INSTANCE, LeaveVineTreeDecorator.INSTANCE)));

	public static final ConfiguredFeature<?, ?> TREES_MANGROVE = register("mangrove_trees",
			Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(EymbraDefaultFeatures.MANGROVE_TREE.withChance(1.0F)), EymbraDefaultFeatures.MANGROVE_TREE)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
					.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(50, 0.5F, 1))));

	public static final TreeFeatureConfig DARKWOOD_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(EymbraBlocks.PREHISTORIC_DARKWOOD_LOG_STATE), new SimpleBlockStateProvider(EymbraBlocks.PREHISTORIC_DARKWOOD_LEAVES_STATE),
			new DarkwoodFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 2), new DarkwoodTrunkPlacer(5, 6, 10), new TwoLayersFeatureSize(1, 1, 2)))
					.decorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE, LeaveVineTreeDecorator.INSTANCE)).build();

	public static final ConfiguredFeature<?, ?> DARKWOOD_TREE = EymbraDefaultFeatures.registerTree("darkwood_tree", DARKWOOD_TREE_CONFIG.setTreeDecorators(ImmutableList.of(TrunkVineTreeDecorator.INSTANCE, LeaveVineTreeDecorator.INSTANCE)));

	public static final ConfiguredFeature<?, ?> TREES_DARKWOOD = register("darkwood_trees",
			Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(EymbraDefaultFeatures.DARKWOOD_TREE.withChance(0.10F)), EymbraDefaultFeatures.DARKWOOD_TREE)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
					.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.05F, 1))));

	public static final Feature<SingleStateFeatureConfig> ROCK_PILE = register("rock_pile", new RockPileFeature());

	@SuppressWarnings({
			"unchecked", "rawtypes"
	})
	public static final ConfiguredFeature<?, ?> ROCK_PILES = register("rock_piles",
			(ConfiguredFeature) EymbraDefaultFeatures.ROCK_PILE.configure(new SingleStateFeatureConfig(Blocks.MOSSY_COBBLESTONE.getDefaultState())).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeatRandomly(2));

	public static final TreeFeatureConfig CALAMITES_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(EymbraBlocks.PREHISTORIC_CALAMITES_LOG_STATE),
			new SimpleBlockStateProvider(EymbraBlocks.PREHISTORIC_CALAMITES_LEAVES_STATE), new CalamitesFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 2), new CalamitesTrunkPlacer(3, 2, 6),
			new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(MORE_BEEHIVES_TREES, TrunkVineTreeDecorator.INSTANCE, LeaveVineTreeDecorator.INSTANCE)).build();

	public static final ConfiguredFeature<?, ?> CALAMITES_TREE = EymbraDefaultFeatures.registerTree("calamites_tree",
			CALAMITES_TREE_CONFIG.setTreeDecorators(ImmutableList.of(MORE_BEEHIVES_TREES, TrunkVineTreeDecorator.INSTANCE, LeaveVineTreeDecorator.INSTANCE)));

	public static final ConfiguredFeature<?, ?> TREES_CALAMITES = register("calamites_trees",
			Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(EymbraDefaultFeatures.CALAMITES_TREE.withChance(1.0F)), EymbraDefaultFeatures.CALAMITES_TREE)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
					.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(50, 0.5F, 1))));

	public static void init() {
	}

	private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(EymbraPrehistoric.MODID, id), configuredFeature);
	}

	private static ConfiguredFeature<TreeFeatureConfig, ?> registerTree(String name, TreeFeatureConfig config) {
		ConfiguredFeature<TreeFeatureConfig, ?> configured = EymbraDefaultFeatures.PREHISTORIC_TREE.configure(config);
		Identifier id = new Identifier(EymbraPrehistoric.MODID, name);

		BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, id, configured);

		return configured;
	}

	private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
		return Registry.register(Registry.FEATURE, new Identifier(EymbraPrehistoric.MODID, name), feature);
	}

	public static void addRainforestTrees(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, EymbraDefaultFeatures.MEGA_TREES_RAINFOREST);
	}

	public static void addPrehistoricVegetation(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, EymbraDefaultFeatures.PATCH_SHORT_BUSH);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, EymbraDefaultFeatures.PATCH_DEAD_BUSH);
	}

	public static void addBogTrees(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, EymbraDefaultFeatures.TREES_MANGROVE);
	}

	public static void addDarkWoodTrees(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, EymbraDefaultFeatures.TREES_DARKWOOD);
	}

	public static void addCalamitesTrees(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, EymbraDefaultFeatures.TREES_CALAMITES);
	}

	public static void addRockPiles(Builder builder) {
		builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, EymbraDefaultFeatures.ROCK_PILES);
	}
}
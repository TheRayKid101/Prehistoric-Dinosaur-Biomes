package net.eymbra.biomes;

import com.google.common.collect.ImmutableSet;
import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.entities.EymbraEntities;
import net.eymbra.features.EymbraDefaultFeatures;
import net.eymbra.particles.EymbraParticles;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.eymbra.sounds.EymbraSoundEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.SpawnSettings.Builder;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.NetherrackReplaceBlobsFeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class EymbraBiomes {
	public static final RegistryKey<Biome> RAINFOREST = register("ph_rainforest");
	public static final RegistryKey<Biome> BOG = register("ph_bog");
	public static final RegistryKey<Biome> SNOW_MOUNTAINS = register("ph_snow_mountains");
	public static final RegistryKey<Biome> RED_DESERT = register("ph_red_desert");
	public static final RegistryKey<Biome> ISLAND = register("ph_island");
	public static final RegistryKey<Biome> OCEAN = register("ph_ocean");
	public static final RegistryKey<Biome> HILLS = register("ph_hills");
	public static final RegistryKey<Biome> SWAMP = register("ph_swamp");
	public static final RegistryKey<Biome> PLAINS = register("ph_plains");
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> PREHISTORIC_GRASS = register("prehistoric_grass",
			SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(EymbraBlocks.PREHISTORIC_GRASS_BLOCK_STATE, EymbraBlocks.PREHISTORIC_DIRT_BLOCK_STATE, Blocks.GRAVEL.getDefaultState())));
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> PREHISTORIC_DESERT = register("prehistoric_desert",
			SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(EymbraBlocks.PREHISTORIC_RED_ROCK_STATE, EymbraBlocks.PREHISTORIC_RED_ROCK_STATE, Blocks.GRAVEL.getDefaultState())));
	public static final ConfiguredFeature<?, ?> PATCH_FIRE = register("prehistoric_patch_fire",
			Feature.RANDOM_PATCH.configure(
					(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.FIRE.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(96).whitelist(ImmutableSet.of(EymbraBlocks.PREHISTORIC_RED_ROCK)).cannotProject().build())
					.decorate(ConfiguredFeatures.Decorators.FIRE));
	@SuppressWarnings({
			"unchecked", "rawtypes"
	})
	public static final ConfiguredFeature<?, ?> BASALT_BLOBS = register("prehistoric_basalt_blobs", (ConfiguredFeature) ((ConfiguredFeature) ((ConfiguredFeature) Feature.NETHERRACK_REPLACE_BLOBS
			.configure(new NetherrackReplaceBlobsFeatureConfig(Blocks.MAGMA_BLOCK.getDefaultState(), Blocks.BASALT.getDefaultState(), UniformIntDistribution.of(3, 4))).rangeOf(128)).spreadHorizontally()).repeat(32));
	public static final ConfiguredFeature<?, ?> ORE_MAGMA = register("prehistoric_ore_magma", Feature.RANDOM_PATCH.configure(
			(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.MAGMA_BLOCK.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(EymbraBlocks.PREHISTORIC_RED_ROCK)).cannotProject().build())
			.decorate(ConfiguredFeatures.Decorators.FIRE));
	@SuppressWarnings({
			"unchecked", "rawtypes"
	})
	public static final ConfiguredFeature<?, ?> MAGMA_BLOBS = register("prehistoric_magma_blobs", (ConfiguredFeature) ((ConfiguredFeature) ((ConfiguredFeature) Feature.NETHERRACK_REPLACE_BLOBS
			.configure(new NetherrackReplaceBlobsFeatureConfig(EymbraBlocks.PREHISTORIC_RED_ROCK_STATE, EymbraBlocks.VOLCANIC_ROCK_STATE, UniformIntDistribution.of(3, 4))).rangeOf(128)).spreadHorizontally()).repeat(8));
	@SuppressWarnings({
			"unchecked", "rawtypes"
	})
	public static final ConfiguredFeature<?, ?> LAVA_BLOBS = register("prehistoric_lava_blobs", (ConfiguredFeature) ((ConfiguredFeature) ((ConfiguredFeature) Feature.NETHERRACK_REPLACE_BLOBS
			.configure(new NetherrackReplaceBlobsFeatureConfig(EymbraBlocks.VOLCANIC_ROCK_STATE, Blocks.LAVA.getDefaultState(), UniformIntDistribution.of(3, 4))).rangeOf(128)).spreadHorizontally()).repeat(14));

	public static void init() {
	}

	private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(EymbraPrehistoric.MODID, id), configuredFeature);
	}

	private static RegistryKey<Biome> register(String name) {
		return RegistryKey.of(Registry.BIOME_KEY, new Identifier(EymbraPrehistoric.MODID, name));
	}

	private static Biome register(RegistryKey<Biome> registryKey, Biome biome) {
		Registry.register(BuiltinRegistries.BIOME, registryKey.getValue(), biome);
		return biome;
	}

	private static <SC extends SurfaceConfig> ConfiguredSurfaceBuilder<SC> register(String id, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder) {
		return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(EymbraPrehistoric.MODID, id), configuredSurfaceBuilder);
	}

	private static Biome createRainforest(float depth, float scale, int hadrosaurWeight, int hadrosaurMaxGroupSize, int dragonflyMaxGroupSize) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.HADROSAUR, hadrosaurWeight, 2, hadrosaurMaxGroupSize));
		builder.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EymbraEntities.DRAGONFLY, 10, 3, dragonflyMaxGroupSize));
		builder.playerSpawnFriendly();
		return createRainforestFeatures(depth, scale, 0.9F, false, false, false, builder);
	}

	private static Biome createRainforestFeatures(float depth, float scale, float downfall, boolean bl, boolean bl2, boolean bl3, SpawnSettings.Builder builder) {
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(EymbraBiomes.PREHISTORIC_GRASS);
		EymbraDefaultFeatures.addRainforestTrees(builder2);
		EymbraDefaultFeatures.addPrehistoricVegetation(builder2);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDefaultLakes(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		builder2.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, EymbraDefaultFeatures.ROCK_PILES);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.JUNGLE).depth(depth).scale(scale).temperature(0.95F).downfall(downfall)
				.effects((new BiomeEffects.Builder()).particleConfig(new BiomeParticleConfig(EymbraParticles.RAINFOREST_DUST, 0.118093334F)).loopSound(EymbraSoundEvents.AMBIENCE_PREHISTORIC_RAINFOREST)
						.additionsSound(new BiomeAdditionsSound(EymbraSoundEvents.ADDITIONAL_PREHISTORIC_RAINFOREST, 0.0011D)).waterColor(3368550).waterFogColor(3368550).fogColor(0x308753).skyColor(0x4ACE80).moodSound(BiomeMoodSound.CAVE).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	private static Biome createBog(float depth, float scale, int tarSlimeWeight, int tarSlimeMaxGroupSize, int dragonflyMaxGroupSize) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.TAR_SLIME, tarSlimeWeight, 1, tarSlimeMaxGroupSize));
		builder.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EymbraEntities.DRAGONFLY, 10, 9, dragonflyMaxGroupSize));
		builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.ICHTHYOSAURUS, 1, 1, 2));
		builder.playerSpawnFriendly();
		return createBogFeatures(depth, scale, 0.9F, false, false, false, builder);
	}

	private static Biome createBogFeatures(float depth, float scale, float downfall, boolean bl, boolean bl2, boolean bl3, SpawnSettings.Builder builder) {
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(EymbraBiomes.PREHISTORIC_GRASS);
		EymbraDefaultFeatures.addBogTrees(builder2);
		EymbraDefaultFeatures.addPrehistoricVegetation(builder2);
		DefaultBiomeFeatures.addSprings(builder2);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDefaultLakes(builder2);
		DefaultBiomeFeatures.addSwampFeatures(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		builder2.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, EymbraDefaultFeatures.ROCK_PILES);

		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.SWAMP).depth(depth).scale(scale).temperature(0.8F).downfall(downfall)
				.effects((new BiomeEffects.Builder()).particleConfig(new BiomeParticleConfig(EymbraParticles.RAINFOREST_DUST, 0.118093334F)).loopSound(EymbraSoundEvents.AMBIENCE_PREHISTORIC_BOG)
						.additionsSound(new BiomeAdditionsSound(EymbraSoundEvents.ADDITIONAL_PREHISTORIC_RAINFOREST, 0.0011D)).waterColor(0x465914).waterFogColor(0x465914).fogColor(0x308753).skyColor(0x4ACE80).moodSound(BiomeMoodSound.CAVE).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	private static Biome createSnowMountains(float depth, float scale) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		return createSnowMountainsFeatures(depth, scale, 0.1F, false, false, false, builder);
	}

	private static Biome createSnowMountainsFeatures(float depth, float scale, float downfall, boolean bl, boolean bl2, boolean bl3, SpawnSettings.Builder builder) {
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(EymbraBiomes.PREHISTORIC_GRASS);
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		builder2.feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.ICE_SPIKE);
		builder2.feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.ICE_PATCH);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.SNOW).category(Biome.Category.ICY).depth(depth).scale(scale).temperature(0.0F).downfall(downfall)
				.effects((new BiomeEffects.Builder()).loopSound(EymbraSoundEvents.AMBIENCE_PREHISTORIC_SNOW_MOUNTAIN).waterColor(3368550).waterFogColor(3368550).fogColor(0x308753).skyColor(0x4ACE80).moodSound(BiomeMoodSound.CAVE).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	private static Biome createRedDesert(float depth, float scale) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.PACHYCEPALOSAURUS, 10, 2, 4));
		return createRedDesertFeatures(depth, scale, builder);
	}

	private static Biome createRedDesertFeatures(float depth, float scale, Builder builder) {
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(EymbraBiomes.PREHISTORIC_DESERT).structureFeature(ConfiguredStructureFeatures.NETHER_FOSSIL)
				.feature(GenerationStep.Feature.SURFACE_STRUCTURES, EymbraBiomes.LAVA_BLOBS).feature(GenerationStep.Feature.SURFACE_STRUCTURES, EymbraBiomes.MAGMA_BLOBS).feature(GenerationStep.Feature.SURFACE_STRUCTURES, EymbraBiomes.BASALT_BLOBS)
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, EymbraBiomes.PATCH_FIRE).feature(GenerationStep.Feature.VEGETAL_DECORATION, EymbraBiomes.ORE_MAGMA);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.NONE).category(Biome.Category.DESERT).depth(depth).scale(scale).temperature(2.0F).downfall(0.0F)
				.effects((new BiomeEffects.Builder()).particleConfig(new BiomeParticleConfig(EymbraParticles.RED_SAND, 0.118093334F)).loopSound(EymbraSoundEvents.AMBIENCE_PREHISTORIC_RED_DESERT).waterColor(3368550).waterFogColor(3368550)
						.fogColor(0xF29776).skyColor(0xF29776).moodSound(BiomeMoodSound.CAVE).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	private static Biome createIsland(float depth, float scale) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.DRAGONFLY, 10, 9, 10));
		builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.ICHTHYOSAURUS, 10, 5, 7));
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.HADROSAUR, 3, 2, 3));
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.DODO, 3, 3, 6));
		return createIslandFeatures(depth, scale, builder);
	}

	private static Biome createIslandFeatures(float depth, float scale, SpawnSettings.Builder builder) {
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(EymbraBiomes.PREHISTORIC_GRASS);
		EymbraDefaultFeatures.addDarkWoodTrees(builder2);
		EymbraDefaultFeatures.addPrehistoricVegetation(builder2);
		DefaultBiomeFeatures.addSprings(builder2);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDefaultLakes(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addSeagrassOnStone(builder2);
		DefaultBiomeFeatures.addKelp(builder2);
		builder2.structureFeature(ConfiguredStructureFeatures.NETHER_FOSSIL);
		builder2.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, EymbraDefaultFeatures.ROCK_PILES);

		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.BEACH).depth(depth).scale(scale).temperature(0.5F).downfall(0.9F)
				.effects((new BiomeEffects.Builder()).particleConfig(new BiomeParticleConfig(EymbraParticles.RAINFOREST_DUST, 0.118093334F)).loopSound(EymbraSoundEvents.AMBIENCE_PREHISTORIC_BOG)
						.additionsSound(new BiomeAdditionsSound(EymbraSoundEvents.ADDITIONAL_PREHISTORIC_RAINFOREST, 0.0011D)).waterColor(3368550).waterFogColor(3368550).fogColor(0x308753).skyColor(0x4ACE80).moodSound(BiomeMoodSound.CAVE).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createNormalOcean(boolean deep) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.ICHTHYOSAURUS, 10, 5, 7));
		GenerationSettings.Builder builder2 = createOceanGenerationSettings(EymbraBiomes.PREHISTORIC_GRASS, deep, false, true);
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		return createOcean(builder, deep, builder2);
	}

	private static Biome createOcean(SpawnSettings.Builder builder, boolean deep, GenerationSettings.Builder builder2) {
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.OCEAN).depth(deep ? -1.8F : -1.0F).scale(0.1F).temperature(0.5F).downfall(0.5F)
				.effects((new BiomeEffects.Builder()).waterColor(3368550).waterFogColor(3368550).fogColor(0x308753).skyColor(0x4ACE80).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build())
				.build();
	}

	private static GenerationSettings.Builder createOceanGenerationSettings(ConfiguredSurfaceBuilder<TernarySurfaceConfig> configuredSurfaceBuilder, boolean bl, boolean bl2, boolean bl3) {
		GenerationSettings.Builder builder = (new GenerationSettings.Builder()).surfaceBuilder(configuredSurfaceBuilder);
		DefaultBiomeFeatures.addOceanCarvers(builder);
		DefaultBiomeFeatures.addDefaultLakes(builder);
		DefaultBiomeFeatures.addDefaultOres(builder);
		DefaultBiomeFeatures.addDefaultDisks(builder);
		DefaultBiomeFeatures.addDefaultMushrooms(builder);
		DefaultBiomeFeatures.addSprings(builder);
		DefaultBiomeFeatures.addSeagrassOnStone(builder);
		DefaultBiomeFeatures.addKelp(builder);
		return builder;
	}

	private static Biome createMountains(float depth, float scale) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		return createMountainsFeatures(depth, scale, 0.1F, false, false, false, builder);
	}

	private static Biome createMountainsFeatures(float depth, float scale, float downfall, boolean bl, boolean bl2, boolean bl3, SpawnSettings.Builder builder) {
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(EymbraBiomes.PREHISTORIC_GRASS);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, EymbraDefaultFeatures.PATCH_SHORT_BUSH);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDefaultLakes(builder2);
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.HADROSAUR, 2, 2, 3));
		builder.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EymbraEntities.DRAGONFLY, 10, 3, 6));
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.DODO, 6, 3, 6));
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.ANKYLOSAURUS, 3, 3, 6));
		builder2.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, EymbraDefaultFeatures.ROCK_PILES);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.EXTREME_HILLS).depth(depth).scale(scale).temperature(0.2F).downfall(downfall)
				.effects((new BiomeEffects.Builder()).loopSound(EymbraSoundEvents.AMBIENCE_PREHISTORIC_RAINFOREST).waterColor(3368550).waterFogColor(3368550).fogColor(0x308753).skyColor(0x4ACE80).moodSound(BiomeMoodSound.CAVE).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	private static Biome createSwamp(float depth, float scale, int tarSlimeWeight, int tarSlimeMaxGroupSize, int dragonflyMaxGroupSize) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.TAR_SLIME, tarSlimeWeight, 1, tarSlimeMaxGroupSize));
		builder.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EymbraEntities.DRAGONFLY, 10, 9, dragonflyMaxGroupSize));
		builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.ICHTHYOSAURUS, 1, 1, 2));
		builder.playerSpawnFriendly();
		return createSwampFeatures(depth, scale, 0.2F, false, false, false, builder);
	}

	private static Biome createSwampFeatures(float depth, float scale, float downfall, boolean bl, boolean bl2, boolean bl3, SpawnSettings.Builder builder) {
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(EymbraBiomes.PREHISTORIC_GRASS);
		EymbraDefaultFeatures.addCalamitesTrees(builder2);
		EymbraDefaultFeatures.addPrehistoricVegetation(builder2);
		DefaultBiomeFeatures.addSprings(builder2);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDefaultLakes(builder2);
		DefaultBiomeFeatures.addSwampFeatures(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addSeagrassOnStone(builder2);
		DefaultBiomeFeatures.addKelp(builder2);
		builder2.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, EymbraDefaultFeatures.ROCK_PILES);

		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.SWAMP).depth(depth).scale(scale).temperature(0.8F).downfall(downfall)
				.effects((new BiomeEffects.Builder()).particleConfig(new BiomeParticleConfig(EymbraParticles.RAINFOREST_DUST, 0.118093334F)).loopSound(EymbraSoundEvents.AMBIENCE_PREHISTORIC_RAINFOREST)
						.additionsSound(new BiomeAdditionsSound(EymbraSoundEvents.ADDITIONAL_PREHISTORIC_RAINFOREST, 0.0011D)).waterColor(0x465914).waterFogColor(0x465914).fogColor(0x308753).skyColor(0x4ACE80).moodSound(BiomeMoodSound.CAVE).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	private static Biome createPlains(float depth, float scale) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		return createPlainsFeatures(depth, scale, 0.1F, false, false, false, builder);
	}

	private static Biome createPlainsFeatures(float depth, float scale, float downfall, boolean bl, boolean bl2, boolean bl3, SpawnSettings.Builder builder) {
		GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(EymbraBiomes.PREHISTORIC_GRASS);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, EymbraDefaultFeatures.PATCH_SHORT_BUSH);
		DefaultBiomeFeatures.addLandCarvers(builder2);
		DefaultBiomeFeatures.addDefaultLakes(builder2);
		builder.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EymbraEntities.DRAGONFLY, 5, 3, 6));
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.DODO, 6, 3, 6));
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EymbraEntities.ANKYLOSAURUS, 6, 3, 6));
		builder2.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, EymbraDefaultFeatures.ROCK_PILES);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.EXTREME_HILLS).depth(depth).scale(scale).temperature(0.8F).downfall(downfall)
				.effects((new BiomeEffects.Builder()).loopSound(EymbraSoundEvents.AMBIENCE_PREHISTORIC_RAINFOREST).waterColor(3368550).waterFogColor(3368550).fogColor(0x308753).skyColor(0x4ACE80).moodSound(BiomeMoodSound.CAVE).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	static {
		register(EymbraBiomes.RAINFOREST, EymbraBiomes.createRainforest(1.0F, 0.5F, 10, 5, 10));
		register(EymbraBiomes.BOG, EymbraBiomes.createBog(-0.1F, 0.5F, 1, 2, 10));
		register(EymbraBiomes.SNOW_MOUNTAINS, EymbraBiomes.createSnowMountains(1.2F, 0.2F));
		register(EymbraBiomes.RED_DESERT, EymbraBiomes.createRedDesert(1.3F, 0.2F));
		register(EymbraBiomes.ISLAND, EymbraBiomes.createIsland(-0.3F, 0.1F));
		register(EymbraBiomes.OCEAN, EymbraBiomes.createNormalOcean(false));
		register(EymbraBiomes.HILLS, EymbraBiomes.createMountains(0.9F, 0.1F));
		register(EymbraBiomes.SWAMP, EymbraBiomes.createSwamp(-0.15F, 0.2F, 1, 2, 10));
		register(EymbraBiomes.PLAINS, EymbraBiomes.createPlains(0.125F, 0.05F));
	}
}
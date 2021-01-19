package net.eymbra.prehistoric.mixin;

import java.util.function.BiFunction;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.serialization.Lifecycle;

import net.eymbra.dimensions.EymbraDimensionType;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {
	@Shadow
	@Final
	public static RegistryKey<DimensionType> THE_NETHER_REGISTRY_KEY;
	@Shadow
	@Final
	public static RegistryKey<DimensionType> THE_END_REGISTRY_KEY;

	@Inject(at = @At("TAIL"), method = "addRegistryDefaults")
	private static void addRegistryDefaults(DynamicRegistryManager.Impl registryManager, CallbackInfoReturnable<DynamicRegistryManager.Impl> ci) {
		MutableRegistry<DimensionType> mutableRegistry = ci.getReturnValue().get(Registry.DIMENSION_TYPE_KEY);
		EymbraDimensionType.DIMENSION_REGISTRY.forEach(pair -> {
			mutableRegistry.add(pair.getRight(), pair.getLeft(), Lifecycle.stable());
		});
	}

	private static ChunkGenerator createCustomGenerator(BiFunction<Registry<Biome>, Long, BiomeSource> preset, Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed) {
		return new NoiseChunkGenerator(preset.apply(biomeRegistry, seed), seed, () -> {
			return (ChunkGeneratorSettings) chunkGeneratorSettingsRegistry.getOrThrow(ChunkGeneratorSettings.OVERWORLD);
		});
	}

	@Inject(at = @At("RETURN"), method = "createDefaultDimensionOptions", cancellable = true)
	private static void createDefaultDimensionOptions(Registry<DimensionType> dimensionRegistry, Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed, CallbackInfoReturnable<SimpleRegistry<DimensionOptions>> cir) {
		SimpleRegistry<DimensionOptions> simpleRegistry = cir.getReturnValue();
		EymbraDimensionType.DIMENSION_REGISTRY.forEach(pair -> {
			RegistryKey<DimensionOptions> custom_options = RegistryKey.of(Registry.DIMENSION_OPTIONS, pair.getRight().getValue());
			EymbraDimensionType.DIMENSION_OPTIONS_REGISTRY.put(pair.getRight(), custom_options);
			EymbraDimensionType.DIMENSION_WORLD_REGISTRY.put(pair.getRight(), RegistryKey.of(Registry.DIMENSION, pair.getRight().getValue()));
			DimensionType custom_type = (DimensionType) dimensionRegistry.getOrThrow(pair.getRight());
			EymbraDimensionType.DIMENSION_TYPE_REGISTRY.put(pair.getRight(), custom_type);
			simpleRegistry.add(custom_options, new DimensionOptions(() -> {
				return (DimensionType) custom_type;
			}, createCustomGenerator(EymbraDimensionType.DIMENSION_PRESET_REGISTRY.get(pair.getRight()), biomeRegistry, chunkGeneratorSettingsRegistry, seed)), Lifecycle.stable());
		});
		cir.setReturnValue(simpleRegistry);
	}
}
package net.eymbra.prehistoric.mixin;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.google.common.collect.ImmutableList;
import net.eymbra.dimensions.EymbraDimensionType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Shadow
	@Final
	protected SaveProperties field_24372;

	@Shadow
	@Final
	private Executor workerExecutor;

	@Shadow
	@Final
	protected LevelStorage.Session session;

	@Shadow
	@Final
	private Map<RegistryKey<World>, ServerWorld> worlds;

	@Shadow
	@Final
	protected DynamicRegistryManager.Impl registryManager;

	@Inject(at = @At("TAIL"), method = "createWorlds")
	protected void worldCreationController(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo info) {
		EymbraDimensionType.DIMENSION_REGISTRY.forEach(pair -> {
			ServerWorldProperties serverWorldProperties = this.field_24372.getMainWorldProperties();
			GeneratorOptions generatorOptions = this.field_24372.getGeneratorOptions();
			boolean bl = generatorOptions.isDebugWorld();
			long l = generatorOptions.getSeed();
			long m = BiomeAccess.hashSeed(l);
			SimpleRegistry<DimensionOptions> simpleRegistry = generatorOptions.getDimensions();
			DimensionOptions dimensionOptions = (DimensionOptions) simpleRegistry.get(EymbraDimensionType.DIMENSION_OPTIONS_REGISTRY.get(pair.getRight()));
			Object chunkGenerator2;
			DimensionType dimensionType2;
			if (dimensionOptions == null) {
				dimensionType2 = (DimensionType) this.registryManager.getDimensionTypes().getOrThrow(DimensionType.OVERWORLD_REGISTRY_KEY);
				chunkGenerator2 = GeneratorOptions.createOverworldGenerator(this.registryManager.get(Registry.BIOME_KEY), this.registryManager.get(Registry.NOISE_SETTINGS_WORLDGEN), (new Random()).nextLong());
			} else {
				dimensionType2 = dimensionOptions.getDimensionType();
				chunkGenerator2 = dimensionOptions.getChunkGenerator();
			}

			RegistryKey<World> world = EymbraDimensionType.DIMENSION_WORLD_REGISTRY.get(pair.getRight());
			ServerWorld serverWorld = new ServerWorld(((MinecraftServer) (Object) this), this.workerExecutor, this.session, serverWorldProperties, world, dimensionType2, worldGenerationProgressListener, (ChunkGenerator)chunkGenerator2, bl, m, ImmutableList.of(), true);
			this.worlds.put(world, serverWorld);
		});
	}
}
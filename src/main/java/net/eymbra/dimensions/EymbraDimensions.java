package net.eymbra.dimensions;

import net.eymbra.prehistoric.EymbraPrehistoric;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class EymbraDimensions {
	public static final Identifier MOD_DIMENSION_ID = new Identifier(EymbraPrehistoric.MODID, "prehistoric");
	public static final RegistryKey<World> EYMBRA_WORLD_KEY = RegistryKey.of(Registry.DIMENSION, EymbraDimensions.MOD_DIMENSION_ID);

	public static void init() {
		EymbraBiomeProvider.registerEymbraBiomeProvider();
		EymbraChunkGenerator.registerEymbraChunkGenerator();
	}
}
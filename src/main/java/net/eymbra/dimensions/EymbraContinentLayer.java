package net.eymbra.dimensions;

import net.eymbra.biomes.EymbraBiomes;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum EymbraContinentLayer implements InitLayer {
	INSTANCE;

	@Override
	public int sample(LayerRandomnessSource context, int x, int y) {
		if (x == 0 && y == 0) {
			return EymbraBiomeProvider.layersBiomeRegistry.getRawId(EymbraBiomeProvider.layersBiomeRegistry.get(EymbraBiomes.PLAINS.getValue()));
		} else {
			return context.nextInt(10) == 0 ? EymbraBiomeProvider.layersBiomeRegistry.getRawId(EymbraBiomeProvider.layersBiomeRegistry.get(EymbraBiomes.PLAINS.getValue()))
					: EymbraBiomeProvider.layersBiomeRegistry.getRawId(EymbraBiomeProvider.layersBiomeRegistry.get(EymbraBiomes.OCEAN.getValue()));
		}
	}
}
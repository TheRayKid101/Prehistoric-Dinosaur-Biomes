package net.eymbra.dimensions;

import net.eymbra.biomes.EymbraBiomes;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum EymbraBiomeSwampLayer implements CrossSamplingLayer {
	INSTANCE;

	@Override
	public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
		if (context.nextInt(2) == 0 && n == center && e == center && s == center && w == center) {
			return EymbraBiomeProvider.layersBiomeRegistry.getRawId(EymbraBiomeProvider.layersBiomeRegistry.get(EymbraBiomes.BOG.getValue()));
		}

		return center;
	}
}
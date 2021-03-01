package net.eymbra.dimensions;

import net.eymbra.biomes.EymbraBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public class EymbraSetBaseBiomesLayer implements IdentitySamplingLayer {
	private Registry<Biome> biomeRegistry;
	private final int[] biomes;

	public EymbraSetBaseBiomesLayer(Registry<Biome> biomeRegistry) {
		this.biomeRegistry = biomeRegistry;

		this.biomes = new int[] {
				this.getBiomeId(EymbraBiomes.RAINFOREST), this.getBiomeId(EymbraBiomes.BOG), this.getBiomeId(EymbraBiomes.RED_DESERT), this.getBiomeId(EymbraBiomes.ISLAND), this.getBiomeId(EymbraBiomes.HILLS), this.getBiomeId(EymbraBiomes.PLAINS),
				this.getBiomeId(EymbraBiomes.OCEAN), this.getBiomeId(EymbraBiomes.SNOW_MOUNTAINS), this.getBiomeId(EymbraBiomes.SWAMP),
		};
	}

	@Override
	public int sample(LayerRandomnessSource context, int value) {
		return this.biomes[context.nextInt(this.biomes.length)];
	}

	protected int getBiomeId(RegistryKey<Biome> biome) {
		return this.biomeRegistry.getRawId(this.biomeRegistry.get(biome.getValue()));
	}
}

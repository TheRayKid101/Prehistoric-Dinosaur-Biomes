package net.eymbra.biomes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.CachingLayerSampler;
import net.minecraft.world.biome.layer.util.LayerFactory;

public class PrehistoricBiomeLayerSampler {
	private static final Logger LOGGER = LogManager.getLogger();
	private final CachingLayerSampler sampler;

	public PrehistoricBiomeLayerSampler(LayerFactory<CachingLayerSampler> layerFactory) {
		this.sampler = (CachingLayerSampler) layerFactory.make();
	}

	public Biome sample(Registry<Biome> registry, int i, int j) {
		int k = this.sampler.sample(i, j);
		
		if (k >= ThePrehistoricBiomeSource.BIOMES.size()) k = ThePrehistoricBiomeSource.BIOMES.size() - 1;
		
		RegistryKey<Biome> registryKey = ThePrehistoricBiomeSource.BIOMES.get(k);
		if (registryKey == null) {
			throw new IllegalStateException("Unknown biome id emitted by layers: " + k);
		} else {
			Biome biome = (Biome) registry.get(registryKey);
			if (biome == null) {
				if (SharedConstants.isDevelopment) {
					throw (IllegalStateException) Util.throwOrPause(new IllegalStateException("Unknown biome id: " + k));
				} else {
					LOGGER.warn("Unknown biome id: ", k);
					return (Biome) registry.get(ThePrehistoricBiomeSource.BIOMES.get(0));
				}
			} else {
				return biome;
			}
		}
	}
}

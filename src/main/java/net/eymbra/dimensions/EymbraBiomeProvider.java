package net.eymbra.dimensions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.eymbra.prehistoric.mixin.BiomeLayerSamplerAccessor;
import net.minecraft.SharedConstants;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.layer.AddEdgeBiomesLayer;
import net.minecraft.world.biome.layer.AddIslandLayer;
import net.minecraft.world.biome.layer.IncreaseEdgeCurvatureLayer;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.SmoothLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.CachingLayerContext;
import net.minecraft.world.biome.layer.util.CachingLayerSampler;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

public class EymbraBiomeProvider extends BiomeSource {
	public static void registerEymbraBiomeProvider() {
		Registry.register(Registry.BIOME_SOURCE, new Identifier(EymbraPrehistoric.MODID, "biome_source"), EymbraBiomeProvider.CODEC);
	}

	public static final Codec<EymbraBiomeProvider> CODEC = RecordCodecBuilder
			.create((instance) -> instance.group(RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((biomeSource) -> biomeSource.biomeRegistry)).apply(instance, instance.stable(EymbraBiomeProvider::new)));

	private final Registry<Biome> biomeRegistry;
	private final BiomeLayerSampler biomeSampler;
	public static Registry<Biome> layersBiomeRegistry;

	public EymbraBiomeProvider(Registry<Biome> biomeRegistry) {
		this(0, biomeRegistry);
	}

	public EymbraBiomeProvider(long seed, Registry<Biome> biomeRegistry) {
		super(biomeRegistry.getEntries().stream().filter(entry -> entry.getKey().getValue().getNamespace().equals(EymbraPrehistoric.MODID)).map(Map.Entry::getValue).collect(Collectors.toList()));
		this.biomeRegistry = biomeRegistry;
		this.biomeSampler = buildWorldProcedure(seed);
		EymbraBiomeProvider.layersBiomeRegistry = this.biomeRegistry;
	}

	public BiomeLayerSampler buildWorldProcedure(long seed) {
		LayerFactory<CachingLayerSampler> layerFactory = build((salt) -> new CachingLayerContext(25, seed, salt));
		return new BiomeLayerSampler(layerFactory);
	}

	@SuppressWarnings({
			"unchecked", "rawtypes", "unused"
	})
	private <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider) {
		LayerFactory<T> layerFactory = parent;

		for (int i = 0; i < count; ++i) {
			layerFactory = layer.create((LayerSampleContext) contextProvider.apply(seed + i), layerFactory);
		}

		return layerFactory;
	}

	@SuppressWarnings({
			"unchecked", "rawtypes"
	})
	public <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(LongFunction<C> contextProvider) {
		LayerFactory<T> layerFactory = EymbraContinentLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1L));
		layerFactory = ScaleLayer.FUZZY.create((LayerSampleContext) contextProvider.apply(2000L), layerFactory);
		layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1L), layerFactory);
		layerFactory = ScaleLayer.NORMAL.create((LayerSampleContext) contextProvider.apply(2001L), layerFactory);
		layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(2L), layerFactory);
		layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(50L), layerFactory);
		layerFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(70L), layerFactory);
		layerFactory = AddIslandLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(2L), layerFactory);
		// NO OCEAN TEMPERATURE LAYERS YET!
		layerFactory = ScaleLayer.NORMAL.create((LayerSampleContext) contextProvider.apply(2002L), layerFactory);
		layerFactory = ScaleLayer.NORMAL.create((LayerSampleContext) contextProvider.apply(2003L), layerFactory);
		LayerFactory<T> layerFactory4 = (new EymbraSetBaseBiomesLayer(this.biomeRegistry)).create((LayerSampleContext) contextProvider.apply(200L), layerFactory);
		// LayerFactory<T> layerFactory2 =
		// EymbraBiomeSwampLayer.INSTANCE.create(contextProvider.apply(250L),
		// layerFactory);
		// layerFactory2 = stack(2001L, EymbraBiomeSwampLayer.INSTANCE, layerFactory, 6,
		// contextProvider);

		for (int i = 0; i < 4; ++i) {
			layerFactory4 = ScaleLayer.NORMAL.create((LayerSampleContext) contextProvider.apply(1000 + i), layerFactory4);
			if (i == 0) {
				layerFactory4 = IncreaseEdgeCurvatureLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(3L), layerFactory4);
			}

			if (i == 1) {
				layerFactory4 = AddEdgeBiomesLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1000L), layerFactory4);
			}
		}

		layerFactory4 = SmoothLayer.INSTANCE.create((LayerSampleContext) contextProvider.apply(1000L), layerFactory4);

		return layerFactory4;
	}

	@Override
	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
		return this.sample(this.biomeRegistry, biomeX, biomeZ);
	}

	public Biome sample(Registry<Biome> registry, int i, int j) {
		int k = ((BiomeLayerSamplerAccessor) this.biomeSampler).getSampler().sample(i, j);
		Biome biome = registry.get(k);
		if (biome == null) {
			if (SharedConstants.isDevelopment) {
				throw Util.throwOrPause(new IllegalStateException("Unknown biome id: " + k));
			} else {
				return registry.get(BuiltinBiomes.fromRawId(0));
			}
		} else {
			return biome;
		}
	}

	@Override
	protected Codec<? extends BiomeSource> getCodec() {
		return EymbraBiomeProvider.CODEC;
	}

	@Override
	public BiomeSource withSeed(long seed) {
		return new EymbraBiomeProvider(seed, this.biomeRegistry);
	}
}
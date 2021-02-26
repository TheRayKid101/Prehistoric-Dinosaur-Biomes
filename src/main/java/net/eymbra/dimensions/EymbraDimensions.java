package net.eymbra.dimensions;

import java.util.OptionalLong;

import net.eymbra.biomes.ThePrehistoricBiomeSource;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.source.VoronoiBiomeAccessType;
import net.minecraft.world.dimension.DimensionType;

public class EymbraDimensions {
	public static final Identifier THE_PREHISTORIC_ID = new Identifier(EymbraPrehistoric.MODID, "the_prehistoric");
	public static final RegistryKey<DimensionType> THE_PREHISTORIC_REGISTRY_KEY;
	protected static final EymbraDimensionType THE_PREHISTORIC;
	protected static final boolean LIGHT = false;
	// OptionalLong.of(13000L)
	static {
		THE_PREHISTORIC_REGISTRY_KEY = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier(EymbraPrehistoric.MODID, "the_prehistoric"));
		THE_PREHISTORIC = new EymbraDimensionType(OptionalLong.empty(), LIGHT, false, false, true, 1.0D, false, false, false, true, false, 256, VoronoiBiomeAccessType.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getId(), THE_PREHISTORIC_ID, 0.0F);
		EymbraDimensionType.registerDimensionType(THE_PREHISTORIC, THE_PREHISTORIC_REGISTRY_KEY);
		EymbraDimensionType.registerDimensionGenerator(THE_PREHISTORIC_REGISTRY_KEY, ThePrehistoricBiomeSource::new);
	}

	public static final class PrehistoricSkyProperties extends SkyProperties {
		private float sunHeight;

		public PrehistoricSkyProperties() {
			super(128.0F, true, SkyType.NORMAL, LIGHT, !LIGHT);// false, true
		}

		@Override
		public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
			this.sunHeight = sunHeight;

			return color.multiply((sunHeight * 0.94F + 0.06F) * 0.8, (sunHeight * 0.94F + 0.06F) * 0.8, (sunHeight * 0.91F + 0.09F) * 0.8);
		}

//		@Override
//		public boolean isDarkened() {
//			return this.sunHeight <= 0;
//		}
//
//		@Override
//		public boolean shouldBrightenLighting() {
//			return this.sunHeight > 0;
//		}

		@Override
		public boolean useThickFog(int camX, int camY) {
			return true;// true
		}
	}

	public static void init() {
	}
}
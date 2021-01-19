package net.eymbra.prehistoric.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.eymbra.dimensions.EymbraDimensions;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.dimension.DimensionType;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Mixin(SkyProperties.class)
public class SkyPropertiesMixin {
	@Shadow
	@Final
	private static Object2ObjectMap<Identifier, SkyProperties> BY_IDENTIFIER;
	
	static {
		BY_IDENTIFIER = (Object2ObjectMap) Util.make(new Object2ObjectArrayMap(), (object2ObjectArrayMap) -> {
			SkyProperties.Overworld overworld = new SkyProperties.Overworld();
			object2ObjectArrayMap.defaultReturnValue(overworld);
			object2ObjectArrayMap.put(DimensionType.OVERWORLD_ID, overworld);
			object2ObjectArrayMap.put(DimensionType.THE_NETHER_ID, new SkyProperties.Nether());
			object2ObjectArrayMap.put(DimensionType.THE_END_ID, new SkyProperties.End());
			object2ObjectArrayMap.put(EymbraDimensions.THE_PREHISTORIC_ID, new EymbraDimensions.PrehistoricSkyProperties());
		});
	}
}
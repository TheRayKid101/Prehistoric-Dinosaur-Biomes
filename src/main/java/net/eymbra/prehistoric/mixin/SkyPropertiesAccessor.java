package net.eymbra.prehistoric.mixin;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SkyProperties.class)
public interface SkyPropertiesAccessor {
	@Accessor("BY_IDENTIFIER")
	static Object2ObjectMap<Identifier, SkyProperties> getIdentifier() {
		throw new UnsupportedOperationException();
	}
}
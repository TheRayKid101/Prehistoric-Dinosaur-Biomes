package net.eymbra.prehistoric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.serialization.Codec;

import net.eymbra.features.leaves.EymbraFoliagePlacerType;
import net.eymbra.features.leaves.RainforestFoliagePlacer;
import net.eymbra.features.trees.CalamitesFoliagePlacer;
import net.eymbra.features.trees.DarkwoodFoliagePlacer;
import net.eymbra.features.trees.MangroveFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

@Mixin(FoliagePlacerType.class)
public class FoliagePlacerTypeMixin {
	@Shadow
	private static <P extends FoliagePlacer> FoliagePlacerType<P> register(String id, Codec<P> codec) {
		return null;
	}
	
	static {
		EymbraFoliagePlacerType.RAINFOREST_FOLIAGE_PLACER = register("rainforest_foliage_placer", RainforestFoliagePlacer.CODEC);
		EymbraFoliagePlacerType.MANGROVE_FOLIAGE_PLACER = register("mangrove_foliage_placer", MangroveFoliagePlacer.CODEC);
		EymbraFoliagePlacerType.DARKWOOD_FOLIAGE_PLACER = register("darkwood_foliage_placer", DarkwoodFoliagePlacer.CODEC);
		EymbraFoliagePlacerType.CALAMITES_FOLIAGE_PLACER = register("calamites_foliage_placer", CalamitesFoliagePlacer.CODEC);
	}
}
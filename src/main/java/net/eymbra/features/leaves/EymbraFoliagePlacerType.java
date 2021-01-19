package net.eymbra.features.leaves;

import net.eymbra.features.trees.CalamitesFoliagePlacer;
import net.eymbra.features.trees.DarkwoodFoliagePlacer;
import net.eymbra.features.trees.MangroveFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class EymbraFoliagePlacerType<P extends FoliagePlacer> {
	public static FoliagePlacerType<RainforestFoliagePlacer> RAINFOREST_FOLIAGE_PLACER;
	public static FoliagePlacerType<MangroveFoliagePlacer> MANGROVE_FOLIAGE_PLACER;
	public static FoliagePlacerType<DarkwoodFoliagePlacer> DARKWOOD_FOLIAGE_PLACER;
	public static FoliagePlacerType<CalamitesFoliagePlacer> CALAMITES_FOLIAGE_PLACER;
}
package net.eymbra.prehistoric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.serialization.Codec;

import net.eymbra.features.trees.CalamitesTrunkPlacer;
import net.eymbra.features.trees.DarkwoodTrunkPlacer;
import net.eymbra.features.trees.EymbraTrunkPlacerType;
import net.eymbra.features.trees.MangroveTrunkPlacer;
import net.eymbra.features.trees.MegaRainforestTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

@Mixin(TrunkPlacerType.class)
public class TrunkPlacerTypeMixin {
	@Shadow
	private static <P extends TrunkPlacer> TrunkPlacerType<P> register(String id, Codec<P> codec) {
		return null;
	}

	static {
		EymbraTrunkPlacerType.RAINFOREST_TRUNK_PLACER = register("rainforest_trunk_placer", MegaRainforestTrunkPlacer.CODEC);
		EymbraTrunkPlacerType.MANGROVE_TRUNK_PLACER = register("mangrove_trunk_placer", MangroveTrunkPlacer.CODEC);
		EymbraTrunkPlacerType.DARKWOOD_TRUNK_PLACER = register("darkwood_trunk_placer", DarkwoodTrunkPlacer.CODEC);
		EymbraTrunkPlacerType.CALAMITES_TRUNK_PLACER = register("calamites_trunk_placer", CalamitesTrunkPlacer.CODEC);
	}
}
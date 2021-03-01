package net.eymbra.prehistoric;

import net.eymbra.biomes.EymbraBiomes;
import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.dimensions.EymbraDimensions;
import net.eymbra.entities.EymbraEntities;
import net.eymbra.features.EymbraDefaultFeatures;
import net.eymbra.items.EymbraItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class EymbraPrehistoric implements ModInitializer {
	public static final ItemGroup PREHISTORIC_GROUP = FabricItemGroupBuilder.build(new Identifier(EymbraPrehistoric.MODID, "prehistoric"), () -> new ItemStack(EymbraBlocks.PREHISTORIC_TIME_BOX));
	public static final String MODID = "eymbra";

	@Override
	public void onInitialize() {
		EymbraEntities.init();
		EymbraBlocks.init();
		EymbraItems.init();
		EymbraDefaultFeatures.init();
		EymbraBiomes.init();
		EymbraDimensions.init();
	}
}
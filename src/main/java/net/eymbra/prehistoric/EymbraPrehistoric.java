package net.eymbra.prehistoric;

import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.dimensions.EymbraDimensions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class EymbraPrehistoric implements ModInitializer {
	public static final String MODID = "eymbra";
	public static final ItemGroup PREHISTORIC_GROUP = FabricItemGroupBuilder.build(new Identifier(EymbraPrehistoric.MODID, "prehistoric"), () -> new ItemStack(EymbraBlocks.PREHISTORIC_TIME_BOX));

	@Override
	public void onInitialize() {
		EymbraDimensions.init();
	}
}
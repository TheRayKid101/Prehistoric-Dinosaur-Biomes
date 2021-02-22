package net.eymbra.prehistoric;

import net.eymbra.biomes.EymbraBiomes;
import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.dimensions.EymbraDimensions;
import net.eymbra.entities.EymbraEntities;
import net.eymbra.features.EymbraDefaultFeatures;
import net.eymbra.gui.screen.EymbraScreenHanderType;
import net.eymbra.items.EymbraItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class EymbraPrehistoric implements ModInitializer {
	public static final String MODID = "eymbra";
	public static final ItemGroup PREHISTORIC_GROUP = FabricItemGroupBuilder.build(new Identifier(EymbraPrehistoric.MODID, "prehistoric"), () -> new ItemStack(EymbraBlocks.PREHISTORIC_TIME_BOX));

	@Override
	public void onInitialize() {
		EymbraScreenHanderType.init();
		EymbraEntities.init();
		EymbraBlocks.init();
		EymbraItems.init();
		EymbraDefaultFeatures.init();
		EymbraBiomes.init();
		EymbraDimensions.init();

		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_SHORT_BUSH, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_DEAD_BUSH, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_PORTAL_BLOCK, RenderLayer.getTranslucent());

		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_CALAMITES_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_DARKWOOD_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_LEPIDODENDRALES_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_MANGROVE_SAPLING, RenderLayer.getCutout());

		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_CALAMITES_LEAVES, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_LEPIDODENDRALES_LEAVES, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_DARKWOOD_LEAVES, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_MANGROVE_LEAVES, RenderLayer.getCutoutMipped());
	}
}
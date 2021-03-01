package net.eymbra.prehistoric;

import net.eymbra.biomes.EymbraBiomes;
import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.entities.EymbraEntities;
import net.eymbra.features.EymbraDefaultFeatures;
import net.eymbra.gui.screen.EymbraScreenHanderType;
import net.eymbra.items.EymbraItems;
import net.eymbra.prehistoric.mixin.SkyPropertiesAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.client.render.SkyProperties.SkyType;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockRenderView;

public class EymbraClientPrehistoric implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EymbraScreenHanderType.init();
		EymbraEntities.init();
		EymbraBlocks.init();
		EymbraItems.init();
		EymbraDefaultFeatures.init();
		EymbraBiomes.init();

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

		BlockRenderLayerMap.INSTANCE.putBlock(EymbraBlocks.PREHISTORIC_GRASS_BLOCK, RenderLayer.getCutoutMipped());

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
			return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5D, 1.0D);
		}, EymbraBlocks.PREHISTORIC_GRASS_BLOCK, EymbraBlocks.PREHISTORIC_SHORT_BUSH);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			BlockState blockState = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
			return ColorProviderRegistry.BLOCK.get(EymbraBlocks.PREHISTORIC_GRASS_BLOCK).getColor(blockState, (BlockRenderView) null, (BlockPos) null, tintIndex);
		}, EymbraBlocks.PREHISTORIC_GRASS_BLOCK, EymbraBlocks.PREHISTORIC_SHORT_BUSH);

		SkyPropertiesAccessor.getIdentifier().put(new Identifier(EymbraPrehistoric.MODID, "sky_property"), new SkyProperties(128.0F, true, SkyType.NORMAL, true, false) {
			@Override
			public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
				return color.multiply((sunHeight * 0.94F + 0.06F) * 0.8, (sunHeight * 0.94F + 0.06F) * 0.8, (sunHeight * 0.91F + 0.09F) * 0.8);
			}

			@Override
			public boolean useThickFog(int camX, int camY) {
				return true;
			}
		});
	}
}
package net.eymbra.prehistoric;

import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.entities.EymbraEntities;
import net.eymbra.entities.renderer.AnkylosaurusEntityRenderer;
import net.eymbra.entities.renderer.DodoEntityRenderer;
import net.eymbra.entities.renderer.DragonflyEntityRenderer;
import net.eymbra.entities.renderer.HadrosaurEntityRenderer;
import net.eymbra.entities.renderer.IchthyosaurusEntityRenderer;
import net.eymbra.entities.renderer.PachycepalosaurusEntityRenderer;
import net.eymbra.entities.renderer.TarSlimeEntityRenderer;
import net.eymbra.gui.screen.EymbraScreenHanderType;
import net.eymbra.particles.EymbraCustomSuspendParticle;
import net.eymbra.particles.EymbraParticles;
import net.eymbra.prehistoric.mixin.SkyPropertiesAccessor;
import net.eymbra.sounds.EymbraSoundEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.client.render.SkyProperties.SkyType;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockRenderView;

@Environment(EnvType.CLIENT)
public class EymbraClientPrehistoric implements ClientModInitializer {
	@Environment(EnvType.CLIENT)
	public static final PositionedSoundInstance TM_OPEN = PositionedSoundInstance.ambient(EymbraSoundEvents.TIME_MACHINE_OPEN);
	@Environment(EnvType.CLIENT)
	public static final PositionedSoundInstance TM_CLOSE = PositionedSoundInstance.ambient(EymbraSoundEvents.TIME_MACHINE_CLOSE);

	@Override
	public void onInitializeClient() {
		EymbraScreenHanderType.init();

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

		EntityRendererRegistry.INSTANCE.register(EymbraEntities.HADROSAUR, (entityRenderDispatcher, context) -> new HadrosaurEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(EymbraEntities.DRAGONFLY, (entityRenderDispatcher, context) -> new DragonflyEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(EymbraEntities.TAR_SLIME, (entityRenderDispatcher, context) -> new TarSlimeEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(EymbraEntities.ICHTHYOSAURUS, (entityRenderDispatcher, context) -> new IchthyosaurusEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(EymbraEntities.PACHYCEPALOSAURUS, (entityRenderDispatcher, context) -> new PachycepalosaurusEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(EymbraEntities.DODO, (entityRenderDispatcher, context) -> new DodoEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(EymbraEntities.ANKYLOSAURUS, (entityRenderDispatcher, context) -> new AnkylosaurusEntityRenderer(entityRenderDispatcher));

		ParticleFactoryRegistry.getInstance().register(EymbraParticles.RED_SAND, EymbraCustomSuspendParticle.RedSandFactory::new);
		ParticleFactoryRegistry.getInstance().register(EymbraParticles.RAINFOREST_DUST, EymbraCustomSuspendParticle.RainforestDustFactory::new);

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
package net.eymbra.entities.renderer;

import net.eymbra.entities.model.TarSlimeEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class TarSlimeOverlayFeatureRenderer<T extends LivingEntity> extends FeatureRenderer<T, TarSlimeEntityModel<T>> {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private final EntityModel<T> model = new TarSlimeEntityModel(0);

	public TarSlimeOverlayFeatureRenderer(FeatureRendererContext<T, TarSlimeEntityModel<T>> featureRendererContext) {
		super(featureRendererContext);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
		if (!livingEntity.isInvisible()) {
			((TarSlimeEntityModel) this.getContextModel()).copyStateTo(this.model);
			this.model.animateModel(livingEntity, f, g, h);
			this.model.setAngles(livingEntity, f, g, j, k, l);
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(livingEntity)));
			this.model.render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(livingEntity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
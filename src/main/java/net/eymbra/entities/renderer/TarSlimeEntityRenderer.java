package net.eymbra.entities.renderer;

import net.eymbra.entities.TarSlimeEntity;
import net.eymbra.entities.model.TarSlimeEntityModel;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class TarSlimeEntityRenderer extends MobEntityRenderer<TarSlimeEntity, TarSlimeEntityModel<TarSlimeEntity>> {
	private static final Identifier TEXTURE = new Identifier(EymbraPrehistoric.MODID, "textures/entity/tarslime/tarslime.png");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TarSlimeEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new TarSlimeEntityModel(16), 0.25F);
		this.addFeature(new TarSlimeOverlayFeatureRenderer(this));
	}

	public void render(TarSlimeEntity TarSlimeEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		this.shadowRadius = 0.25F * (float) TarSlimeEntity.getSize();
		super.render((net.eymbra.entities.TarSlimeEntity) TarSlimeEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	protected void scale(TarSlimeEntity TarSlimeEntity, MatrixStack matrixStack, float f) {
		matrixStack.scale(0.999F, 0.999F, 0.999F);
		matrixStack.translate(0.0D, 0.0010000000474974513D, 0.0D);
		float h = (float) TarSlimeEntity.getSize();
		float i = MathHelper.lerp(f, TarSlimeEntity.lastStretch, TarSlimeEntity.stretch) / (h * 0.5F + 1.0F);
		float j = 1.0F / (i + 1.0F);
		matrixStack.scale(j * h, 1.0F / j * h, j * h);
	}

	public Identifier getTexture(TarSlimeEntity TarSlimeEntity) {
		return TEXTURE;
	}
}
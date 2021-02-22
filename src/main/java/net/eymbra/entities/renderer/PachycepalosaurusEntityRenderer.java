package net.eymbra.entities.renderer;

import net.eymbra.entities.PachycepalosaurusEntity;
import net.eymbra.entities.model.PachycepalosaurusEntityModel;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PachycepalosaurusEntityRenderer extends MobEntityRenderer<PachycepalosaurusEntity, PachycepalosaurusEntityModel<PachycepalosaurusEntity>> {
	private static final Identifier _PACHYCEPALOSAURUS_TEXTURE = new Identifier(EymbraPrehistoric.MODID, "textures/entity/pachycephalosaurus/pachycephalosaurus.png");
	private final float scale;

	public PachycepalosaurusEntityRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher, new PachycepalosaurusEntityModel<>(), 0.5F);
		this.scale = 1.0F;
	}

	@Override
	protected void scale(PachycepalosaurusEntity entity, MatrixStack matrixStack, float amount) {
		if (!entity.isBaby()) {
			matrixStack.scale(this.scale, this.scale, this.scale);
		} else {
			matrixStack.scale(this.scale / 2, this.scale / 2, this.scale / 2);
		}
	}

	@Override
	public Identifier getTexture(PachycepalosaurusEntity mobEntity) {
		return _PACHYCEPALOSAURUS_TEXTURE;
	}
}
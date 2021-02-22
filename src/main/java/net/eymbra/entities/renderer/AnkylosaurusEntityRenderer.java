package net.eymbra.entities.renderer;

import net.eymbra.entities.AnkylosaurusEntity;
import net.eymbra.entities.model.AnkylosaurusEntityModel;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AnkylosaurusEntityRenderer extends MobEntityRenderer<AnkylosaurusEntity, AnkylosaurusEntityModel<AnkylosaurusEntity>> {
	private static final Identifier _ANKYLOSAURUS_TEXTURE = new Identifier(EymbraPrehistoric.MODID, "textures/entity/ankylosaurus/ankylosaurus.png");
	private final float scale;

	public AnkylosaurusEntityRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher, new AnkylosaurusEntityModel<>(), 0.5F);
		this.scale = 1.0F;
	}

	@Override
	protected void scale(AnkylosaurusEntity entity, MatrixStack matrixStack, float amount) {
		if (!entity.isBaby()) {
			matrixStack.scale(this.scale, this.scale, this.scale);
		} else {
			matrixStack.scale(this.scale / 2, this.scale / 2, this.scale / 2);
		}
	}

	@Override
	public Identifier getTexture(AnkylosaurusEntity mobEntity) {
		return _ANKYLOSAURUS_TEXTURE;
	}
}
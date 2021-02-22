package net.eymbra.entities.renderer;

import net.eymbra.entities.DodoEntity;
import net.eymbra.entities.model.DodoEntityModel;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DodoEntityRenderer extends MobEntityRenderer<DodoEntity, DodoEntityModel<DodoEntity>> {
	private static final Identifier _DODO_TEXTURE = new Identifier(EymbraPrehistoric.MODID, "textures/entity/dodo/dodo.png");
	private final float scale;

	public DodoEntityRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher, new DodoEntityModel<>(), 0.3F);
		this.scale = 1.0F;
	}

	@Override
	protected void scale(DodoEntity entity, MatrixStack matrixStack, float amount) {
		if (!entity.isBaby()) {
			matrixStack.scale(this.scale, this.scale, this.scale);
		} else {
			matrixStack.scale(this.scale / 2, this.scale / 2, this.scale / 2);
		}
	}

	@Override
	public Identifier getTexture(DodoEntity entity) {
		return _DODO_TEXTURE;
	}
}
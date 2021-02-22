package net.eymbra.entities.renderer;

import net.eymbra.entities.HadrosaurEntity;
import net.eymbra.entities.model.HadrosaurEntityModel;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HadrosaurEntityRenderer extends MobEntityRenderer<HadrosaurEntity, HadrosaurEntityModel<HadrosaurEntity>> {
	private static final Identifier _HADROSAUR_TEXTURE = new Identifier(EymbraPrehistoric.MODID, "textures/entity/hadrosaur/parasaurolophus.png");
	private final float scale;

	public HadrosaurEntityRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher, new HadrosaurEntityModel<>(), 1.0F);
		this.scale = 1.0F;
	}

	@Override
	protected void scale(HadrosaurEntity entity, MatrixStack matrixStack, float amount) {
		if (!entity.isBaby()) {
			matrixStack.scale(this.scale, this.scale, this.scale);
		} else {
			matrixStack.scale(this.scale / 2, this.scale / 2, this.scale / 2);
		}
	}

	@Override
	public Identifier getTexture(HadrosaurEntity mobEntity) {
		return _HADROSAUR_TEXTURE;
	}
}
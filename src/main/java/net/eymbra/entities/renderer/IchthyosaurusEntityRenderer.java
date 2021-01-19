package net.eymbra.entities.renderer;

import net.eymbra.entities.IchthyosaurusEntity;
import net.eymbra.entities.model.IchthyosaurusEntityModel;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class IchthyosaurusEntityRenderer extends MobEntityRenderer<IchthyosaurusEntity, IchthyosaurusEntityModel<IchthyosaurusEntity>> {
	private static final Identifier TEXTURE = new Identifier(EymbraPrehistoric.MODID, "textures/entity/ichthyosaurus/ichthyosaurus.png");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public IchthyosaurusEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new IchthyosaurusEntityModel(), 0.7F);
	}

	public Identifier getTexture(IchthyosaurusEntity IchthyosaurusEntity) {
		return TEXTURE;
	}
}
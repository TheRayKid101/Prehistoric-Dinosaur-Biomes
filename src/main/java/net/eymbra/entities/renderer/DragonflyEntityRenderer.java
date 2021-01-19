package net.eymbra.entities.renderer;

import net.eymbra.entities.DragonflyEntity;
import net.eymbra.entities.model.DragonflyEntityModel;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DragonflyEntityRenderer extends MobEntityRenderer<DragonflyEntity, DragonflyEntityModel<DragonflyEntity>> {
	private static final Identifier _DRAGONFLY_TEXTURE = new Identifier(EymbraPrehistoric.MODID, "textures/entity/dragonfly/dragonfly.png");
	
	public DragonflyEntityRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher, new DragonflyEntityModel<>(), 1.0F);
	}
	
	@Override
	public Identifier getTexture(DragonflyEntity mobEntity) {
		return _DRAGONFLY_TEXTURE;
	}
}
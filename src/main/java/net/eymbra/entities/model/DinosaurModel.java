package net.eymbra.entities.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public abstract class DinosaurModel<T extends Entity> extends EntityModel<T> {
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.getBodyParts().forEach((p_228272_8_) -> {
			p_228272_8_.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		});

		Iterable<ModelPart> tail_parts = this.getTailParts();

		if (tail_parts != null) {
			tail_parts.forEach((p_228272_8_) -> {
				p_228272_8_.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			});
		}
	}

	public abstract Iterable<ModelPart> getBodyParts();

	public abstract Iterable<ModelPart> getTailParts();
}
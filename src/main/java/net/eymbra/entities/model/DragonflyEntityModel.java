package net.eymbra.entities.model;

import java.util.Arrays;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.eymbra.entities.DragonflyEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class DragonflyEntityModel<T extends Entity> extends EntityModel<T> {
	// private final ModelPart this.bodyParts[0];
	// private final ModelPart tailPart2234234;
	private final ModelPart leftWingLower;
	private final ModelPart leftWingUpper;
	private final ModelPart rightWingLower;
	private final ModelPart rightWingUpper;
	private ModelPart[] bodyParts;
	private ImmutableList<ModelPart> parts;

	public DragonflyEntityModel() {
		this.bodyParts = new ModelPart[8];

		this.bodyParts[0] = new ModelPart(this, 10, 0);
		this.bodyParts[0].addCuboid(0.0F, 0.0F, 0.0F, 2, 1, 2);
		this.bodyParts[0].setPivot(0.4666667F, 0.0F, 4.0F);
		this.bodyParts[0].pitch = 0.0F;
		this.bodyParts[0].roll = 0.0F;
		this.bodyParts[0].yaw = 0.0F;
		this.bodyParts[0].mirror = false;

		this.bodyParts[1] = new ModelPart(this, 18, 0);
		this.bodyParts[1].addCuboid(0.0F, 0.0F, 0.0F, 2, 1, 2);
		this.bodyParts[1].setPivot(0.5F, 0.0F, 6.0F);
		this.bodyParts[1].pitch = -0.22307F;
		this.bodyParts[1].roll = 0.0F;
		this.bodyParts[1].yaw = 0.0F;
		this.bodyParts[1].mirror = false;

		this.bodyParts[2] = new ModelPart(this, 27, 0);
		this.bodyParts[2].addCuboid(0.0F, 0.0F, 0.0F, 2, 1, 2);
		this.bodyParts[2].setPivot(0.5F, 0.5F, 8.0F);
		this.bodyParts[2].pitch = -0.44614F;
		this.bodyParts[2].roll = 0.0F;
		this.bodyParts[2].yaw = 0.0F;
		this.bodyParts[2].mirror = false;

		this.bodyParts[3] = new ModelPart(this, 36, 0);
		this.bodyParts[3].addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 2);
		this.bodyParts[3].setPivot(1.0F, 1.5F, 9.8F);
		this.bodyParts[3].pitch = -0.66922F;
		this.bodyParts[3].roll = 0.0F;
		this.bodyParts[3].yaw = 0.0F;
		this.bodyParts[3].mirror = false;

		this.bodyParts[4] = new ModelPart(this, 0, 0);
		this.bodyParts[4].addCuboid(0.0F, 0.0F, 0.0F, 3, 2, 2);
		this.bodyParts[4].setPivot(0.0F, 0.2F, -1.0F);
		this.bodyParts[4].pitch = 0.18589F;
		this.bodyParts[4].roll = 0.0F;
		this.bodyParts[4].yaw = 0.0F;
		this.bodyParts[4].mirror = false;

		this.bodyParts[5] = new ModelPart(this, 0, 4);
		this.bodyParts[5].addCuboid(0.0F, 0.0F, 0.0F, 3, 2, 3);
		this.bodyParts[5].setPivot(0.0F, 0.0F, 1.0F);
		this.bodyParts[5].pitch = 0.0F;
		this.bodyParts[5].roll = 0.0F;
		this.bodyParts[5].yaw = 0.0F;
		this.bodyParts[5].mirror = false;

		this.bodyParts[6] = new ModelPart(this, 48, 0);
		this.bodyParts[6].addCuboid(0.0F, 0.0F, 0.0F, 0, 1, 2);
		this.bodyParts[6].setPivot(0.91F, 2.533333F, 11.0F);
		this.bodyParts[6].pitch = -0.89229F;
		this.bodyParts[6].roll = 0.0F;
		this.bodyParts[6].yaw = 0.0F;
		this.bodyParts[6].mirror = false;

		this.bodyParts[7] = new ModelPart(this, 43, 0);
		this.bodyParts[7].addCuboid(0.0F, 0.0F, 0.0F, 0, 1, 2);
		this.bodyParts[7].setPivot(2.0F, 2.5F, 11.0F);
		this.bodyParts[7].pitch = -0.89229F;
		this.bodyParts[7].roll = 0.0F;
		this.bodyParts[7].yaw = 0.0F;
		this.bodyParts[7].mirror = false;

		this.leftWingLower = new ModelPart(this, 0, 10);
		this.leftWingLower.addCuboid(0.0F, 0.0F, 0.0F, 5, 1, 2);
		this.leftWingLower.setPivot(3.0F, 0.1F, 2.0F);
		this.leftWingLower.pitch = 0.0F;
		this.leftWingLower.roll = 0.0F;
		this.leftWingLower.yaw = 0.0F;
		this.leftWingLower.mirror = false;

		this.leftWingUpper = new ModelPart(this, 15, 10);
		this.leftWingUpper.addCuboid(0.0F, 0.0F, 0.0F, 8, 1, 2);
		this.leftWingUpper.setPivot(2.0F, 0.1F, 1.0F);
		this.leftWingUpper.pitch = -0.14871F;
		this.leftWingUpper.roll = 0.18589F;
		this.leftWingUpper.yaw = -0.11154F;
		this.leftWingUpper.mirror = false;

		this.rightWingLower = new ModelPart(this, 0, 14);
		this.rightWingLower.addCuboid(-5.0F, 0.0F, 0.0F, 5, 1, 2);
		this.rightWingLower.setPivot(0.0F, 0.0F, 2.0F);
		this.rightWingLower.pitch = 0.0F;
		this.rightWingLower.roll = 0.0F;
		this.rightWingLower.yaw = 0.0F;
		this.rightWingLower.mirror = false;

		this.rightWingUpper = new ModelPart(this, 15, 14);
		this.rightWingUpper.addCuboid(0.0F, 0.0F, 0.0F, 8, 1, 2);
		this.rightWingUpper.setPivot(0.5F, 0.6F, 3.0F);
		this.rightWingUpper.pitch = 0.24335F;
		this.rightWingUpper.roll = 2.91728F;
		this.rightWingUpper.yaw = -0.09563F;
		this.rightWingUpper.mirror = false;

		Builder<ModelPart> builderParts = ImmutableList.builder();
		builderParts.addAll(Arrays.asList(this.bodyParts));
		builderParts.add(this.leftWingLower);
		builderParts.add(this.leftWingUpper);
		builderParts.add(this.rightWingLower);
		builderParts.add(this.rightWingUpper);
		this.parts = builderParts.build();
	}

	@Override
	public void animateModel(T entity, float limbAngle, float limbDistance, float tickDelta) {
		DragonflyEntity entitydragonfly = (DragonflyEntity) entity;
		updateWings(entitydragonfly);
		flexTail(entitydragonfly);
	}

	private void flexTail(DragonflyEntity entitydragonfly) {
		if (entitydragonfly.isFlappingWings()) {
			entitydragonfly.tailState += 0.5F;
			if (entitydragonfly.tailState > 5.0F) {
				entitydragonfly.tailState = 5.0F;
			}
		} else {
			entitydragonfly.tailState -= 0.5F;
			if (entitydragonfly.tailState < 0.0F) {
				entitydragonfly.tailState = 0.0F;
			}
		}
		this.bodyParts[0].roll = 0.0F + 0.0F * entitydragonfly.tailState;
		this.bodyParts[0].pitch = 0.0F + 0.0F * entitydragonfly.tailState;
		this.bodyParts[1].roll = 0.0F + 0.01F * entitydragonfly.tailState;
		this.bodyParts[1].pitch = -0.22307F + 0.03F * entitydragonfly.tailState;
		this.bodyParts[2].roll = 0.5F - 0.06F * entitydragonfly.tailState;
		this.bodyParts[2].pitch = -0.44614F + 0.05F * entitydragonfly.tailState;
		this.bodyParts[3].roll = 1.5F - 0.2F * entitydragonfly.tailState;
		this.bodyParts[3].pitch = -0.66922F + 0.05F * entitydragonfly.tailState;
		this.bodyParts[6].roll = 2.533333F - 0.3F * entitydragonfly.tailState;
		this.bodyParts[6].pitch = -0.89229F + 0.05F * entitydragonfly.tailState;
		this.bodyParts[7].roll = 2.5F - 0.3F * entitydragonfly.tailState;
		this.bodyParts[7].pitch = -0.89229F + 0.05F * entitydragonfly.tailState;
	}

	private void updateWings(DragonflyEntity entitydragonfly) {
		if (entitydragonfly.isFlappingWings()) {

			entitydragonfly.wingState++;
			if (++entitydragonfly.wingState >= 10) {
				entitydragonfly.wingState = -10;
			}
		} else {
			entitydragonfly.wingState = 0;
		}

		float f = entitydragonfly.wingState * 0.1F;
		this.leftWingLower.yaw = 0.0F + f;
		this.leftWingUpper.yaw = -0.11154F + f;
		this.rightWingLower.yaw = 0.0F - f;
		this.rightWingUpper.yaw = 0.14871F - f;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		// Align Dragonfly in hitbox
		matrices.translate(0, 1.15, -0.3);

		this.parts.forEach(part -> {
			part.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		});
	}
}
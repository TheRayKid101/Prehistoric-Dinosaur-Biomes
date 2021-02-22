package net.eymbra.entities.model;

import net.eymbra.entities.DragonflyEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class MeganeuraEntityModel<T extends Entity> extends EntityModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart snout;
	private final ModelPart tail1;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart tail4;
	private final ModelPart tailEndLeft;
	private final ModelPart tailEndRight;
	private final ModelPart tailStubLeft;
	private final ModelPart tailStubRight;
	private final ModelPart legs;
	private final ModelPart wingLeft;
	private final ModelPart wingLeft2;
	private final ModelPart wingRight;
	private final ModelPart wingRight2;

	public MeganeuraEntityModel() {
		textureWidth = 64;
		textureHeight = 32;

		root = new ModelPart(this);
		root.setPivot(0.0F, 0.0F, 0.0F);

		body = new ModelPart(this);
		body.setPivot(0.0F, 17.0F, 0.0F);
		root.addChild(body);
		body.setTextureOffset(0, 4).addCuboid(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 5.0F, 0.0F, false);

		head = new ModelPart(this);
		head.setPivot(0.0F, 0.0F, 0.0F);
		body.addChild(head);
		setRotationAngle(head, 0.4098F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addCuboid(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);

		snout = new ModelPart(this);
		snout.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(snout);
		snout.setTextureOffset(8, 0).addCuboid(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		tail1 = new ModelPart(this);
		tail1.setPivot(0.0F, 0.5F, 5.0F);
		body.addChild(tail1);
		setRotationAngle(tail1, -0.182F, 0.0F, 0.0F);
		tail1.setTextureOffset(0, 18).addCuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		tail2 = new ModelPart(this);
		tail2.setPivot(0.0F, 0.0F, 2.0F);
		tail1.addChild(tail2);
		setRotationAngle(tail2, -0.182F, 0.0F, 0.0F);
		tail2.setTextureOffset(0, 21).addCuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		tail3 = new ModelPart(this);
		tail3.setPivot(0.0F, 0.0F, 2.0F);
		tail2.addChild(tail3);
		setRotationAngle(tail3, 0.182F, 0.0F, 0.0F);
		tail3.setTextureOffset(0, 24).addCuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		tail4 = new ModelPart(this);
		tail4.setPivot(0.0F, 0.0F, 2.0F);
		tail3.addChild(tail4);
		setRotationAngle(tail4, 0.2276F, 0.0F, 0.0F);
		tail4.setTextureOffset(0, 27).addCuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		tailEndLeft = new ModelPart(this);
		tailEndLeft.setPivot(0.0F, 0.0F, 0.0F);
		tail4.addChild(tailEndLeft);
		setRotationAngle(tailEndLeft, 0.0F, -0.2618F, 0.0F);
		tailEndLeft.setTextureOffset(6, 18).addCuboid(-0.6F, 0.5F, 0.2F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		tailEndRight = new ModelPart(this);
		tailEndRight.setPivot(0.0F, 0.0F, 0.0F);
		tail4.addChild(tailEndRight);
		setRotationAngle(tailEndRight, 0.0F, 0.2618F, 0.0F);
		tailEndRight.setTextureOffset(6, 22).addCuboid(-0.5F, 0.5F, 0.2F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		tailStubLeft = new ModelPart(this);
		tailStubLeft.setPivot(0.0F, 0.0F, 0.0F);
		tail3.addChild(tailStubLeft);
		tailStubLeft.setTextureOffset(6, 26).addCuboid(-1.5F, -0.5F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		tailStubRight = new ModelPart(this);
		tailStubRight.setPivot(0.0F, 0.0F, 0.0F);
		tail3.addChild(tailStubRight);
		tailStubRight.setTextureOffset(6, 28).addCuboid(0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		legs = new ModelPart(this);
		legs.setPivot(0.0F, 0.0F, 0.0F);
		body.addChild(legs);
		legs.setTextureOffset(0, 11).addCuboid(-2.5F, 1.0F, 0.0F, 5.0F, 2.0F, 5.0F, 0.0F, false);

		wingLeft = new ModelPart(this);
		wingLeft.setPivot(-1.5F, -1.0F, 0.5F);
		body.addChild(wingLeft);
		setRotationAngle(wingLeft, 0.0F, 0.0F, 0.182F);
		wingLeft.setTextureOffset(11, 0).addCuboid(-9.0F, 0.0F, -2.5F, 9.0F, 1.0F, 5.0F, 0.0F, true);

		wingLeft2 = new ModelPart(this);
		wingLeft2.setPivot(-1.5F, -1.0F, 4.5F);
		body.addChild(wingLeft2);
		setRotationAngle(wingLeft2, 0.0F, 0.0F, -0.182F);
		wingLeft2.setTextureOffset(20, 12).addCuboid(-7.0F, 0.0F, -2.5F, 7.0F, 1.0F, 5.0F, 0.0F, true);

		wingRight = new ModelPart(this);
		wingRight.setPivot(1.5F, -1.0F, 0.5F);
		body.addChild(wingRight);
		setRotationAngle(wingRight, 0.0F, 0.0F, -0.182F);
		wingRight.setTextureOffset(16, 6).addCuboid(0.0F, 0.0F, -2.5F, 9.0F, 1.0F, 5.0F, 0.0F, true);

		wingRight2 = new ModelPart(this);
		wingRight2.setPivot(1.5F, -1.0F, 4.5F);
		body.addChild(wingRight2);
		setRotationAngle(wingRight2, 0.0F, 0.0F, 0.2276F);
		wingRight2.setTextureOffset(14, 18).addCuboid(0.0F, 0.0F, -2.5F, 7.0F, 1.0F, 5.0F, 0.0F, true);
	}

	@Override
	public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		DragonflyEntity entitydragonfly = (DragonflyEntity) entity;

		if (entitydragonfly.isFlappingWings()) {
			entitydragonfly.wingState++;
			if (++entitydragonfly.wingState >= 10) {
				entitydragonfly.wingState = -10;
			}
		} else {
			entitydragonfly.wingState += 0.1F;
			if (++entitydragonfly.wingState >= 10) {
				entitydragonfly.wingState = -10;
			}
		}

		float f = entitydragonfly.wingState * 0.1F;
		this.wingLeft.roll = 0.0F + f;
		this.wingLeft2.roll = -0.11154F + f;
		this.wingRight.roll = 0.0F - f;
		this.wingRight2.roll = 0.14871F - f;

		// Tail
		this.tail1.pitch = (float) (Math.cos(Math.toRadians(90)) + (Math.cos(limbAngle * 0.6662F + 3.14F) * limbDistance) * 0.09F);
		this.tail2.pitch = (float) (Math.cos(Math.toRadians(95)) + (Math.cos(limbAngle * 0.6662F + 3.14F) * limbDistance) * 0.07F);
		this.tail3.pitch = (float) (Math.cos(Math.toRadians(97)) + (Math.cos(limbAngle * 0.6662F + 3.14F) * limbDistance) * 0.06F);
		this.tail4.pitch = (float) (Math.cos(Math.toRadians(100)) + (Math.cos(limbAngle * 0.6662F + 3.14F) * limbDistance) * 0.04F);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.translate(-.1, .3, -.1);
		root.render(matrices, vertices, light, overlay);
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.pitch = x;
		modelRenderer.roll = y;
		modelRenderer.yaw = z;
	}
}
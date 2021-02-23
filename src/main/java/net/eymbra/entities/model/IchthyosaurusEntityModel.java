package net.eymbra.entities.model;

import net.eymbra.entities.IchthyosaurusEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class IchthyosaurusEntityModel<T extends IchthyosaurusEntity> extends EntityModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart body2;
	private final ModelPart finLeftBack;
	private final ModelPart finRightBack;
	private final ModelPart tail;
	private final ModelPart tailFin;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart snout;
	private final ModelPart finLeft;
	private final ModelPart finRight;
	private final ModelPart bodyFin;

	public IchthyosaurusEntityModel() {
		textureWidth = 64;
		textureHeight = 64;

		root = new ModelPart(this);
		root.setPivot(0.0F, 0.0F, 0.0F);

		body = new ModelPart(this);
		body.setPivot(0.0F, 18.0F, -4.0F);
		root.addChild(body);
		body.setTextureOffset(0, 0).addCuboid(-3.5F, -4.5F, 0.0F, 7.0F, 9.0F, 10.0F, 0.0F, false);

		body2 = new ModelPart(this);
		body2.setPivot(0.0F, 0.0F, 10.0F);
		body.addChild(body2);
		body2.setTextureOffset(34, 12).addCuboid(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 4.0F, 0.0F, false);

		finLeftBack = new ModelPart(this);
		finLeftBack.setPivot(-3.0F, 2.0F, 2.0F);
		body2.addChild(finLeftBack);
		setRotationAngle(finLeftBack, 0.0F, 0.0F, 0.8029F);
		finLeftBack.setTextureOffset(0, 0).addCuboid(-1.0F, 0.0F, -1.5F, 1.0F, 6.0F, 3.0F, 0.0F, false);

		finRightBack = new ModelPart(this);
		finRightBack.setPivot(3.0F, 2.0F, 2.0F);
		body2.addChild(finRightBack);
		setRotationAngle(finRightBack, 0.0F, 0.0F, -0.8029F);
		finRightBack.setTextureOffset(0, 0).addCuboid(0.0F, 0.0F, -1.5F, 1.0F, 6.0F, 3.0F, 0.0F, false);

		tail = new ModelPart(this);
		tail.setPivot(0.0F, 0.0F, 4.0F);
		body2.addChild(tail);
		tail.setTextureOffset(0, 36).addCuboid(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 9.0F, 0.0F, false);

		tailFin = new ModelPart(this);
		tailFin.setPivot(0.0F, 0.0F, 8.0F);
		tail.addChild(tailFin);
		tailFin.setTextureOffset(24, 32).addCuboid(0.0F, -6.5F, 0.0F, 0.0F, 13.0F, 6.0F, 0.0F, false);

		neck = new ModelPart(this);
		neck.setPivot(0.0F, 0.0F, 0.0F);
		body.addChild(neck);
		neck.setTextureOffset(34, 0).addCuboid(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 5.0F, 0.0F, false);

		head = new ModelPart(this);
		head.setPivot(0.0F, 0.0F, -5.0F);
		neck.addChild(head);
		head.setTextureOffset(0, 19).addCuboid(-2.5F, -2.5F, -3.0F, 5.0F, 5.0F, 3.0F, 0.0F, false);

		jaw = new ModelPart(this);
		jaw.setPivot(0.0F, 0.5F, -3.0F);
		head.addChild(jaw);
		jaw.setTextureOffset(26, 24).addCuboid(-1.0F, 0.0F, -6.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);

		snout = new ModelPart(this);
		snout.setPivot(0.0F, 0.5F, -3.0F);
		head.addChild(snout);
		snout.setTextureOffset(10, 24).addCuboid(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);

		finLeft = new ModelPart(this);
		finLeft.setPivot(-3.0F, 2.0F, 1.0F);
		body.addChild(finLeft);
		setRotationAngle(finLeft, 0.0F, 0.0F, 0.8029F);
		finLeft.setTextureOffset(20, 19).addCuboid(-1.0F, 0.0F, -2.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);

		finRight = new ModelPart(this);
		finRight.setPivot(3.0F, 2.0F, 1.0F);
		body.addChild(finRight);
		setRotationAngle(finRight, 0.0F, 0.0F, -0.8029F);
		finRight.setTextureOffset(20, 19).addCuboid(0.0F, 0.0F, -2.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);

		bodyFin = new ModelPart(this);
		bodyFin.setPivot(0.0F, 0.0F, 0.0F);
		body.addChild(bodyFin);
		setRotationAngle(bodyFin, -0.4363F, 0.0F, 0.0F);
		bodyFin.setTextureOffset(0, 27).addCuboid(-0.5F, -9.5F, 0.0F, 1.0F, 5.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setAngles(IchthyosaurusEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		// previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrices, vertices, light, overlay);
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.pitch = x;
		modelRenderer.roll = y;
		modelRenderer.yaw = z;
	}
}
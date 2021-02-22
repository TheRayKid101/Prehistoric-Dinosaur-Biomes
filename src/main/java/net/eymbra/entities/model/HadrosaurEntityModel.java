package net.eymbra.entities.model;

import net.eymbra.entities.HadrosaurEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class HadrosaurEntityModel<T extends HadrosaurEntity> extends EntityModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart body2;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart legLeft;
	private final ModelPart footLeft;
	private final ModelPart legRight;
	private final ModelPart footRight;
	private final ModelPart armLeft;
	private final ModelPart armLeft2;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart crest;
	private final ModelPart crest2;
	private final ModelPart jaw;
	private final ModelPart snout;
	private final ModelPart armRight;
	private final ModelPart armRight2;

	public HadrosaurEntityModel() {
		textureWidth = 256;
		textureHeight = 256;

		root = new ModelPart(this);
		root.setPivot(0.0F, 0.0F, 0.0F);

		body = new ModelPart(this);
		body.setPivot(0.0F, -4.0F, -5.0F);
		root.addChild(body);
		body.setTextureOffset(0, 0).addCuboid(-7.5F, -9.0F, 0.0F, 15.0F, 18.0F, 15.0F, 0.0F, false);

		body2 = new ModelPart(this);
		body2.setPivot(0.0F, -3.0F, 15.0F);
		body.addChild(body2);
		body2.setTextureOffset(0, 33).addCuboid(-9.0F, -12.0F, 0.0F, 18.0F, 24.0F, 32.0F, 0.0F, false);

		tail = new ModelPart(this);
		tail.setPivot(0.0F, -5.5F, 32.0F);
		body2.addChild(tail);
		tail.setTextureOffset(0, 90).addCuboid(-5.0F, -6.5F, 0.0F, 10.0F, 13.0F, 29.0F, 0.0F, false);

		tail2 = new ModelPart(this);
		tail2.setPivot(0.0F, 0.0F, 29.0F);
		tail.addChild(tail2);
		tail2.setTextureOffset(0, 134).addCuboid(-3.5F, -4.5F, 0.0F, 7.0F, 9.0F, 29.0F, 0.0F, false);

		legLeft = new ModelPart(this);
		legLeft.setPivot(-8.0F, 0.0F, 20.5F);
		body2.addChild(legLeft);
		legLeft.setTextureOffset(104, 40).addCuboid(-6.0F, -4.0F, -7.5F, 8.0F, 20.0F, 15.0F, 0.0F, false);

		footLeft = new ModelPart(this);
		footLeft.setPivot(-2.0F, 16.0F, 3.5F);
		legLeft.addChild(footLeft);
		footLeft.setTextureOffset(104, 75).addCuboid(-4.0F, 0.0F, -4.0F, 8.0F, 15.0F, 8.0F, 0.0F, false);

		legRight = new ModelPart(this);
		legRight.setPivot(8.0F, 0.0F, 20.5F);
		body2.addChild(legRight);
		legRight.setTextureOffset(104, 40).addCuboid(-2.0F, -4.0F, -7.5F, 8.0F, 20.0F, 15.0F, 0.0F, false);

		footRight = new ModelPart(this);
		footRight.setPivot(2.0F, 16.0F, 3.5F);
		legRight.addChild(footRight);
		footRight.setTextureOffset(104, 75).addCuboid(-4.0F, 0.0F, -4.0F, 8.0F, 15.0F, 8.0F, 0.0F, false);

		armLeft = new ModelPart(this);
		armLeft.setPivot(-7.5F, 7.0F, 7.5F);
		body.addChild(armLeft);
		armLeft.setTextureOffset(0, 35).addCuboid(-3.5F, -2.0F, -2.5F, 5.0F, 12.0F, 5.0F, 0.0F, false);

		armLeft2 = new ModelPart(this);
		armLeft2.setPivot(-1.0F, 10.0F, 0.0F);
		armLeft.addChild(armLeft2);
		armLeft2.setTextureOffset(0, 90).addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);

		neck = new ModelPart(this);
		neck.setPivot(0.0F, 1.0F, 0.0F);
		body.addChild(neck);
		neck.setTextureOffset(72, 26).addCuboid(-4.5F, -14.0F, -9.0F, 9.0F, 19.0F, 9.0F, 0.0F, false);

		head = new ModelPart(this);
		head.setPivot(0.0F, -8.0F, -3.0F);
		neck.addChild(head);
		head.setTextureOffset(60, 0).addCuboid(-5.0F, -11.0F, -13.0F, 10.0F, 11.0F, 15.0F, 0.0F, false);

		crest = new ModelPart(this);
		crest.setPivot(0.0F, -6.5F, -10.5F);
		head.addChild(crest);
		setRotationAngle(crest, 0.4363F, 0.0F, 0.0F);
		crest.setTextureOffset(130, 0).addCuboid(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 15.0F, 0.0F, false);

		crest2 = new ModelPart(this);
		crest2.setPivot(0.0F, 0.0F, 0.0F);
		crest.addChild(crest2);
		setRotationAngle(crest2, -0.1745F, 0.0F, 0.0F);
		crest2.setTextureOffset(170, 0).addCuboid(-2.5F, -7.5F, 13.7F, 5.0F, 5.0F, 15.0F, 0.0F, false);

		jaw = new ModelPart(this);
		jaw.setPivot(0.0F, -4.0F, -13.0F);
		head.addChild(jaw);
		jaw.setTextureOffset(98, 0).addCuboid(-2.0F, 0.0F, -6.0F, 4.0F, 4.0F, 6.0F, 0.0F, false);

		snout = new ModelPart(this);
		snout.setPivot(0.0F, -4.0F, -13.0F);
		head.addChild(snout);
		snout.setTextureOffset(121, 0).addCuboid(-2.0F, -4.0F, -6.0F, 4.0F, 4.0F, 6.0F, 0.0F, false);

		armRight = new ModelPart(this);
		armRight.setPivot(7.5F, 7.0F, 7.5F);
		body.addChild(armRight);
		armRight.setTextureOffset(0, 35).addCuboid(-0.5F, -2.0F, -2.5F, 5.0F, 12.0F, 5.0F, 0.0F, false);

		armRight2 = new ModelPart(this);
		armRight2.setPivot(2.0F, 10.0F, 0.0F);
		armRight.addChild(armRight2);
		armRight2.setTextureOffset(0, 90).addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.legLeft.pitch = MathHelper.cos(limbAngle * 0.6662F * 0.5F + (float) Math.PI) * 1.4F * limbDistance * 0.5F;
		this.legRight.pitch = MathHelper.cos(limbAngle * 0.6662F * 0.5F) * 1.4F * limbDistance * 0.5F;

		this.footLeft.pitch = MathHelper.abs(MathHelper.cos(limbAngle * 0.6662F * 0.25F + (float) Math.PI) * 1.4F * limbDistance * 0.35F);
		this.footRight.pitch = MathHelper.abs(MathHelper.cos(limbAngle * 0.6662F * 0.25F) * 1.4F * limbDistance * 0.35F);

		this.armLeft.pitch = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance * 0.5F;
		this.armRight.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;

		this.tail.pitch = MathHelper.cos((float) Math.toRadians(95)) + MathHelper.cos(animationProgress * 0.02F) * 0.05F;
		this.tail2.pitch = MathHelper.cos((float) Math.toRadians(95)) + MathHelper.cos(animationProgress * 0.01F) * 0.025F;

		this.tail.yaw = MathHelper.cos(animationProgress * 0.07F) * 0.2F;
		this.tail2.yaw = MathHelper.cos(animationProgress * 0.07F) * 0.1F;

		this.jaw.pitch = Math.abs(MathHelper.cos(animationProgress * 0.07F) * 0.05F);

		this.head.yaw = headYaw * 0.015F;
		this.head.pitch = headPitch * 0.01F;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.translate(0, 0, -2.0);

		root.render(matrices, vertices, light, overlay);
	}

	public void setRotationAngle(ModelPart ModelPart, float x, float y, float z) {
		ModelPart.pitch = x;
		ModelPart.roll = y;
		ModelPart.yaw = z;
	}
}
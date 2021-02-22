package net.eymbra.entities.model;

import net.eymbra.entities.AnkylosaurusEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class AnkylosaurusEntityModel<T extends AnkylosaurusEntity> extends EntityModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart headhornLeftUp;
	private final ModelPart headhornLeftDown;
	private final ModelPart headHornRightUp;
	private final ModelPart headHornRightDown;
	private final ModelPart jaw;
	private final ModelPart snout;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart maceTailLeft;
	private final ModelPart maceTailRight;
	private final ModelPart backSpikes1;
	private final ModelPart backSpikes2;
	private final ModelPart backSpikes3;
	private final ModelPart backSpikesLeft;
	private final ModelPart backSpikesRight;
	private final ModelPart armLeft;
	private final ModelPart armRight;
	private final ModelPart legLeft;
	private final ModelPart legRight;
	private float tailLift;
	private float tailShake;

	public AnkylosaurusEntityModel() {
		textureWidth = 256;
		textureHeight = 128;

		root = new ModelPart(this);
		root.setPivot(0.0F, 3.0F, 0.0F);

		body = new ModelPart(this);
		body.setPivot(0.0F, 0.0F, -7.7F);
		root.addChild(body);
		body.setTextureOffset(0, 50).addCuboid(-13.0F, -8.0F, 0.0F, 26.0F, 16.0F, 46.0F, 0.0F, false);

		head = new ModelPart(this);
		head.setPivot(0.0F, 1.0F, 0.0F);
		body.addChild(head);
		head.setTextureOffset(0, 0).addCuboid(-5.0F, -5.0F, -11.0F, 10.0F, 10.0F, 11.0F, 0.0F, false);

		headhornLeftUp = new ModelPart(this);
		headhornLeftUp.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(headhornLeftUp);
		headhornLeftUp.setTextureOffset(62, 0).addCuboid(-6.0F, -6.0F, -3.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		headhornLeftDown = new ModelPart(this);
		headhornLeftDown.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(headhornLeftDown);
		headhornLeftDown.setTextureOffset(62, 0).addCuboid(-6.0F, 2.0F, -3.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		headHornRightUp = new ModelPart(this);
		headHornRightUp.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(headHornRightUp);
		headHornRightUp.setTextureOffset(62, 0).addCuboid(2.0F, -6.0F, -3.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		headHornRightDown = new ModelPart(this);
		headHornRightDown.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(headHornRightDown);
		headHornRightDown.setTextureOffset(62, 0).addCuboid(2.0F, 2.0F, -3.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		jaw = new ModelPart(this);
		jaw.setPivot(0.0F, 2.8F, -11.0F);
		head.addChild(jaw);
		jaw.setTextureOffset(0, 21).addCuboid(-2.5F, 0.0F, -4.6F, 5.0F, 2.0F, 5.0F, 0.0F, false);

		snout = new ModelPart(this);
		snout.setPivot(0.0F, 2.8F, -11.0F);
		head.addChild(snout);
		snout.setTextureOffset(0, 28).addCuboid(-2.5F, -6.0F, -4.6F, 5.0F, 6.0F, 5.0F, 0.0F, false);

		tail = new ModelPart(this);
		tail.setPivot(0.0F, 0.0F, 46.0F);
		body.addChild(tail);
		tail.setTextureOffset(144, 76).addCuboid(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 25.0F, 0.0F, false);

		tail2 = new ModelPart(this);
		tail2.setPivot(0.0F, 0.0F, 25.0F);
		tail.addChild(tail2);
		tail2.setTextureOffset(195, 92).addCuboid(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 19.0F, 0.0F, false);

		tail3 = new ModelPart(this);
		tail3.setPivot(0.0F, 0.0F, 19.0F);
		tail2.addChild(tail3);
		tail3.setTextureOffset(0, 74).addCuboid(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 17.0F, 0.0F, false);

		maceTailLeft = new ModelPart(this);
		maceTailLeft.setPivot(0.0F, 0.0F, 0.0F);
		tail3.addChild(maceTailLeft);
		maceTailLeft.setTextureOffset(62, 21).addCuboid(-10.0F, -1.5F, 7.0F, 8.0F, 3.0F, 9.0F, 0.0F, true);

		maceTailRight = new ModelPart(this);
		maceTailRight.setPivot(0.0F, 0.0F, 0.0F);
		tail3.addChild(maceTailRight);
		maceTailRight.setTextureOffset(62, 8).addCuboid(2.0F, -1.5F, 7.0F, 8.0F, 3.0F, 9.0F, 0.0F, true);

		backSpikes1 = new ModelPart(this);
		backSpikes1.setPivot(0.0F, 0.0F, 0.0F);
		body.addChild(backSpikes1);
		backSpikes1.setTextureOffset(108, 0).addCuboid(-1.0F, -11.0F, 0.0F, 1.0F, 3.0F, 46.0F, 0.0F, false);

		backSpikes2 = new ModelPart(this);
		backSpikes2.setPivot(0.0F, 0.0F, 0.0F);
		body.addChild(backSpikes2);
		backSpikes2.setTextureOffset(108, 0).addCuboid(-10.0F, -11.0F, 0.0F, 1.0F, 3.0F, 46.0F, 0.0F, false);

		backSpikes3 = new ModelPart(this);
		backSpikes3.setPivot(0.0F, 0.0F, 0.0F);
		body.addChild(backSpikes3);
		backSpikes3.setTextureOffset(108, 0).addCuboid(8.0F, -11.0F, 0.0F, 1.0F, 3.0F, 46.0F, 0.0F, false);

		backSpikesLeft = new ModelPart(this);
		backSpikesLeft.setPivot(0.0F, 0.0F, 0.0F);
		body.addChild(backSpikesLeft);
		backSpikesLeft.setTextureOffset(0, 0).addCuboid(-20.0F, 4.0F, 0.0F, 7.0F, 1.0F, 46.0F, 0.0F, false);

		backSpikesRight = new ModelPart(this);
		backSpikesRight.setPivot(0.0F, 0.0F, 0.0F);
		body.addChild(backSpikesRight);
		backSpikesRight.setTextureOffset(0, 0).addCuboid(13.0F, 4.0F, 0.0F, 7.0F, 1.0F, 46.0F, 0.0F, true);

		armLeft = new ModelPart(this);
		armLeft.setPivot(-8.0F, 8.0F, 8.0F);
		body.addChild(armLeft);
		armLeft.setTextureOffset(100, 0).addCuboid(-4.0F, 0.0F, -4.0F, 8.0F, 13.0F, 8.0F, 0.0F, false);

		armRight = new ModelPart(this);
		armRight.setPivot(8.0F, 8.0F, 8.0F);
		body.addChild(armRight);
		armRight.setTextureOffset(100, 0).addCuboid(-4.0F, 0.0F, -4.0F, 8.0F, 13.0F, 8.0F, 0.0F, false);

		legLeft = new ModelPart(this);
		legLeft.setPivot(-8.0F, 8.0F, 38.0F);
		body.addChild(legLeft);
		legLeft.setTextureOffset(100, 0).addCuboid(-4.0F, 0.0F, -4.0F, 8.0F, 13.0F, 8.0F, 0.0F, false);

		legRight = new ModelPart(this);
		legRight.setPivot(8.0F, 8.0F, 38.0F);
		body.addChild(legRight);
		legRight.setTextureOffset(100, 0).addCuboid(-4.0F, 0.0F, -4.0F, 8.0F, 13.0F, 8.0F, 0.0F, false);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.legLeft.pitch = MathHelper.cos(limbAngle * 0.6662F * 0.5F + (float) Math.PI) * 1.4F * limbDistance * 0.5F;
		this.legRight.pitch = MathHelper.cos(limbAngle * 0.6662F * 0.5F) * 1.4F * limbDistance * 0.5F;

		this.armLeft.pitch = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance * 0.5F;
		this.armRight.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;

		this.tail.pitch = this.tailLift + MathHelper.cos((float) Math.toRadians(95)) + MathHelper.cos(animationProgress * 0.02F) * 0.05F;
		this.tail2.pitch = this.tailLift + MathHelper.cos((float) Math.toRadians(95)) + MathHelper.cos(animationProgress * 0.01F) * 0.025F;

		// Walking speed tail movement + Natural Movement Tail
		this.tail.yaw = this.tailShake + (MathHelper.cos(limbAngle * 0.25F) * limbDistance * 0.5F) + (MathHelper.cos(animationProgress * 0.07F) * 0.2F);
		this.tail2.yaw = this.tailShake + (MathHelper.cos(limbAngle * 0.25F) * limbDistance * 0.5F) + (MathHelper.cos(animationProgress * 0.07F) * 0.1F);

		this.jaw.pitch = Math.abs(MathHelper.cos(animationProgress * 0.07F) * 0.05F);

		this.head.yaw = headYaw * 0.015F;
		this.head.pitch = headPitch * 0.01F;
	}

	@Override
	public void animateModel(T entity, float limbAngle, float limbDistance, float tickDelta) {
		super.animateModel(entity, limbAngle, limbDistance, tickDelta);
		// this.tailLift = false ? MathHelper.cos(limbAngle * 0.05F) : 0;
		// this.tailShake = false ? MathHelper.cos(limbAngle * 0.05F) : 0;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.translate(0, 0, -1.0);

		root.render(matrices, vertices, light, overlay);
	}

	public void setRotationAngle(ModelPart ModelPart, float x, float y, float z) {
		ModelPart.pitch = x;
		ModelPart.roll = y;
		ModelPart.yaw = z;
	}
}
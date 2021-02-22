package net.eymbra.entities.model;

import net.eymbra.entities.PachycepalosaurusEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class PachycepalosaurusEntityModel<T extends PachycepalosaurusEntity> extends EntityModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart headCollar;
	private final ModelPart headBone;
	private final ModelPart jaw;
	private final ModelPart snout;
	private final ModelPart snouthornRight;
	private final ModelPart shouthornLeft;
	private final ModelPart headCollarSpikes;
	private final ModelPart legLeft;
	private final ModelPart legLeft2;
	private final ModelPart footLeft;
	private final ModelPart legRight;
	private final ModelPart legRight2;
	private final ModelPart footRight;
	private final ModelPart armRight;
	private final ModelPart armRight2;
	private final ModelPart clawRight;
	private final ModelPart armLeft;
	private final ModelPart armLeft2;
	private final ModelPart clawLeft;
	private float headPitchModifier;
	private float neckPitchModifier;

	public PachycepalosaurusEntityModel() {
		textureWidth = 128;
		textureHeight = 128;

		root = new ModelPart(this);
		root.setPivot(0.0F, 0.0F, 0.0F);

		body = new ModelPart(this);
		body.setPivot(0.0F, 9.5F, -5.0F);
		root.addChild(body);
		body.setTextureOffset(0, 12).addCuboid(-4.5F, -4.5F, 0.0F, 9.0F, 9.0F, 20.0F, 0.0F, false);

		tail = new ModelPart(this);
		tail.setPivot(0.0F, -1.0F, 20.0F);
		body.addChild(tail);
		tail.setTextureOffset(0, 41).addCuboid(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 14.0F, 0.0F, false);

		tail2 = new ModelPart(this);
		tail2.setPivot(0.0F, -0.5F, 12.0F);
		tail.addChild(tail2);
		tail2.setTextureOffset(0, 62).addCuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 14.0F, 0.0F, false);

		neck = new ModelPart(this);
		neck.setPivot(0.0F, -1.0F, 2.0F);
		body.addChild(neck);
		neck.setTextureOffset(40, 16).addCuboid(-2.5F, -7.0F, -5.0F, 5.0F, 10.0F, 6.0F, 0.0F, false);

		head = new ModelPart(this);
		head.setPivot(0.0F, -4.0F, -1.0F);
		neck.addChild(head);
		head.setTextureOffset(0, 0).addCuboid(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 7.0F, 0.0F, false);

		headCollar = new ModelPart(this);
		headCollar.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(headCollar);
		headCollar.setTextureOffset(55, 0).addCuboid(-4.0F, -5.0F, -2.0F, 8.0F, 2.0F, 5.0F, 0.0F, false);

		headBone = new ModelPart(this);
		headBone.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(headBone);
		headBone.setTextureOffset(62, 10).addCuboid(-3.0F, -7.0F, -6.0F, 6.0F, 2.0F, 8.0F, 0.0F, false);

		jaw = new ModelPart(this);
		jaw.setPivot(0.0F, -2.0F, -6.0F);
		head.addChild(jaw);
		jaw.setTextureOffset(26, 0).addCuboid(-1.5F, 0.0F, -4.0F, 3.0F, 2.0F, 4.0F, 0.0F, false);

		snout = new ModelPart(this);
		snout.setPivot(0.0F, -2.0F, -6.0F);
		head.addChild(snout);
		snout.setTextureOffset(40, 0).addCuboid(-1.5F, -2.0F, -4.0F, 3.0F, 2.0F, 4.0F, 0.0F, false);

		snouthornRight = new ModelPart(this);
		snouthornRight.setPivot(0.0F, 0.0F, 0.0F);
		snout.addChild(snouthornRight);
		snouthornRight.setTextureOffset(0, 0).addCuboid(0.5F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		shouthornLeft = new ModelPart(this);
		shouthornLeft.setPivot(0.0F, 0.0F, 0.0F);
		snout.addChild(shouthornLeft);
		shouthornLeft.setTextureOffset(0, 0).addCuboid(-1.5F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		headCollarSpikes = new ModelPart(this);
		headCollarSpikes.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(headCollarSpikes);
		headCollarSpikes.setTextureOffset(78, 1).addCuboid(-5.0F, -5.0F, -2.0F, 10.0F, 1.0F, 6.0F, 0.0F, false);

		legLeft = new ModelPart(this);
		legLeft.setPivot(-4.5F, 2.0F, 15.0F);
		body.addChild(legLeft);
		legLeft.setTextureOffset(0, 81).addCuboid(-2.5F, -4.5F, -4.0F, 5.0F, 9.0F, 8.0F, 0.0F, false);

		legLeft2 = new ModelPart(this);
		legLeft2.setPivot(0.3F, 1.0F, 4.0F);
		legLeft.addChild(legLeft2);
		legLeft2.setTextureOffset(0, 98).addCuboid(-2.0F, -2.0F, -1.0F, 4.0F, 13.0F, 4.0F, 0.0F, false);

		footLeft = new ModelPart(this);
		footLeft.setPivot(0.0F, 10.5F, -1.0F);
		legLeft2.addChild(footLeft);
		footLeft.setTextureOffset(0, 116).addCuboid(-2.5F, -1.0F, -6.0F, 5.0F, 2.0F, 7.0F, 0.0F, false);

		legRight = new ModelPart(this);
		legRight.setPivot(4.5F, 2.0F, 15.0F);
		body.addChild(legRight);
		legRight.setTextureOffset(0, 81).addCuboid(-2.5F, -4.5F, -4.0F, 5.0F, 9.0F, 8.0F, 0.0F, false);

		legRight2 = new ModelPart(this);
		legRight2.setPivot(0.3F, 1.0F, 4.0F);
		legRight.addChild(legRight2);
		legRight2.setTextureOffset(0, 98).addCuboid(-2.0F, -2.0F, -1.0F, 4.0F, 13.0F, 4.0F, 0.0F, false);

		footRight = new ModelPart(this);
		footRight.setPivot(0.0F, 10.5F, -1.0F);
		legRight2.addChild(footRight);
		footRight.setTextureOffset(0, 116).addCuboid(-2.5F, -1.0F, -6.0F, 5.0F, 2.0F, 7.0F, 0.0F, false);

		armRight = new ModelPart(this);
		armRight.setPivot(-4.0F, 2.5F, 3.0F);
		body.addChild(armRight);
		armRight.setTextureOffset(0, 12).addCuboid(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

		armRight2 = new ModelPart(this);
		armRight2.setPivot(0.0F, 0.0F, 0.0F);
		armRight.addChild(armRight2);
		armRight2.setTextureOffset(0, 21).addCuboid(-1.0F, 3.0F, -5.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

		clawRight = new ModelPart(this);
		clawRight.setPivot(0.0F, 0.0F, 0.0F);
		armRight.addChild(clawRight);
		clawRight.setTextureOffset(0, 28).addCuboid(-1.0F, 5.0F, -5.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		armLeft = new ModelPart(this);
		armLeft.setPivot(4.0F, 2.5F, 3.0F);
		body.addChild(armLeft);
		armLeft.setTextureOffset(0, 12).addCuboid(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

		armLeft2 = new ModelPart(this);
		armLeft2.setPivot(0.0F, 0.0F, 0.0F);
		armLeft.addChild(armLeft2);
		armLeft2.setTextureOffset(0, 21).addCuboid(-1.0F, 3.0F, -5.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

		clawLeft = new ModelPart(this);
		clawLeft.setPivot(0.0F, 0.0F, 0.0F);
		armLeft.addChild(clawLeft);
		clawLeft.setTextureOffset(0, 28).addCuboid(-1.0F, 5.0F, -5.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.legLeft.pitch = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance;
		this.legRight.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

		this.tail.yaw = MathHelper.cos(animationProgress * 0.07F) * 0.2F;
		this.tail2.yaw = MathHelper.cos(animationProgress * 0.07F) * 0.1F;

		this.neck.pitch = neckPitchModifier + headPitch * 0.005F + MathHelper.cos(animationProgress * 0.03F) * 0.1F;
		this.neck.yaw = headYaw * 0.0025F;

		this.head.yaw = headYaw * 0.015F;
		this.head.pitch = headPitchModifier + headPitch * 0.01F;

		this.jaw.pitch = Math.abs(MathHelper.cos(animationProgress * 0.07F) * 0.05F);

		this.armLeft.pitch = MathHelper.cos(animationProgress * 0.07F) * 0.15F;
		this.armRight.pitch = MathHelper.cos(animationProgress * 0.08F) * 0.1F;
	}

	@Override
	public void animateModel(T entity, float f, float g, float h) {
		super.animateModel(entity, f, g, h);
		this.neckPitchModifier = entity.isDrinking() ? entity.getNeckAngle(163.0F * 0.1F) : 0;
		this.headPitchModifier = entity.isDrinking() ? entity.getHeadAngle(163.0F * 0.04F) + MathHelper.sin(f * 0.2F) * 0.1F : 0;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrices, vertices, light, overlay);
	}

	public void setRotationAngle(ModelPart ModelPart, float x, float y, float z) {
		ModelPart.pitch = x;
		ModelPart.roll = y;
		ModelPart.yaw = z;
	}
}
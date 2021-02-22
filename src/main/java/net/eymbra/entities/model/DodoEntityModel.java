package net.eymbra.entities.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class DodoEntityModel<T extends Entity> extends EntityModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart beakJaw;
	private final ModelPart beakSnout;
	private final ModelPart beakSnout2;
	private final ModelPart tailFluff;
	private final ModelPart wingLeft;
	private final ModelPart wingLeft2;
	private final ModelPart wingRight;
	private final ModelPart wingRight2;
	private final ModelPart legLeft;
	private final ModelPart legRight;

	public DodoEntityModel() {
		textureWidth = 64;
		textureHeight = 32;

		root = new ModelPart(this);
		root.setPivot(0.0F, 0.0F, 0.0F);

		body = new ModelPart(this);
		body.setPivot(0.0F, 19.5F, 0.0F);
		root.addChild(body);
		body.setTextureOffset(0, 6).addCuboid(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 5.0F, 0.0F, false);

		neck = new ModelPart(this);
		neck.setPivot(0.0F, 0.0F, 0.0F);
		body.addChild(neck);
		neck.setTextureOffset(0, 16).addCuboid(-1.0F, -3.5F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		head = new ModelPart(this);
		head.setPivot(0.0F, -3.0F, 0.5F);
		neck.addChild(head);
		head.setTextureOffset(0, 0).addCuboid(-1.5F, -3.0F, -2.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		beakJaw = new ModelPart(this);
		beakJaw.setPivot(0.0F, -1.0F, -2.0F);
		head.addChild(beakJaw);
		beakJaw.setTextureOffset(22, 0).addCuboid(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);

		beakSnout = new ModelPart(this);
		beakSnout.setPivot(0.0F, -1.0F, -2.0F);
		head.addChild(beakSnout);
		beakSnout.setTextureOffset(12, 0).addCuboid(-1.0F, -1.0F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);

		beakSnout2 = new ModelPart(this);
		beakSnout2.setPivot(0.0F, 0.0F, 0.0F);
		beakSnout.addChild(beakSnout2);
		beakSnout2.setTextureOffset(15, 4).addCuboid(-1.0F, -2.0F, -4.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		tailFluff = new ModelPart(this);
		tailFluff.setPivot(0.0F, 0.0F, 0.0F);
		body.addChild(tailFluff);
		tailFluff.setTextureOffset(8, 16).addCuboid(-2.5F, -1.5F, 5.0F, 5.0F, 4.0F, 1.0F, 0.0F, false);

		wingLeft = new ModelPart(this);
		wingLeft.setPivot(-2.5F, -1.5F, 2.0F);
		body.addChild(wingLeft);
		wingLeft.setTextureOffset(0, 22).addCuboid(-1.0F, 0.0F, -1.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);

		wingLeft2 = new ModelPart(this);
		wingLeft2.setPivot(0.0F, 0.0F, 0.0F);
		wingLeft.addChild(wingLeft2);
		wingLeft2.setTextureOffset(0, 27).addCuboid(-1.0F, 1.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		wingRight = new ModelPart(this);
		wingRight.setPivot(2.5F, -1.5F, 2.0F);
		body.addChild(wingRight);
		wingRight.setTextureOffset(0, 22).addCuboid(0.0F, 0.0F, -1.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);

		wingRight2 = new ModelPart(this);
		wingRight2.setPivot(0.0F, 0.0F, 0.0F);
		wingRight.addChild(wingRight2);
		wingRight2.setTextureOffset(0, 27).addCuboid(0.0F, 1.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		legLeft = new ModelPart(this);
		legLeft.setPivot(-1.5F, 1.5F, 4.0F);
		body.addChild(legLeft);
		legLeft.setTextureOffset(32, 0).addCuboid(-1.5F, 0.0F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		legRight = new ModelPart(this);
		legRight.setPivot(1.5F, 1.5F, 4.0F);
		body.addChild(legRight);
		legRight.setTextureOffset(32, 0).addCuboid(-1.5F, 0.0F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.head.pitch = headPitch * 0.017453292F;
		this.head.yaw = headYaw * 0.017453292F;
		this.legRight.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
		this.legLeft.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
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
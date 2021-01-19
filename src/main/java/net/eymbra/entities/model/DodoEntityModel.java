package net.eymbra.entities.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class DodoEntityModel<T extends Entity> extends EntityModel<T> {
	private final ModelPart Body;
	private final ModelPart Neck;
	private final ModelPart Tail_Poof;
	private final ModelPart Beak;
	private final ModelPart Bottom;
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;
	private final ModelPart WingLeft;
	private final ModelPart WingRight;
	private final ModelPart Head;

	public DodoEntityModel() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.Body = new ModelPart(this, 0, 0);
		this.Body.addCuboid(0.0F, 0.0F, 0.0F, 7, 7, 9);
		this.Body.setPivot(-3.0F, 11.0F, -6.0F);
		this.Body.setTextureSize(64, 32);
		this.Body.mirror = true;

		this.Neck = new ModelPart(this, 0, 16);
		this.Neck.addCuboid(0.0F, 0.0F, 0.0F, 4, 8, 3);
		this.Neck.setPivot(-1.5F, 6.0F, -7.0F);
		this.Neck.setTextureSize(64, 32);
		this.Neck.mirror = true;
		setRotation(this.Neck, -0.2230717F, 0.0F, 0.0F);

		this.Tail_Poof = new ModelPart(this, 14, 16);
		this.Tail_Poof.addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 4);
		this.Tail_Poof.setPivot(-1.5F, 10.0F, 1.0F);
		this.Tail_Poof.setTextureSize(64, 32);
		this.Tail_Poof.mirror = true;
		setRotation(this.Tail_Poof, -0.1858931F, 0.0F, 0.0F);

		this.Beak = new ModelPart(this, 23, 1);
		this.Beak.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 5);
		this.Beak.setPivot(-1.0F, 6.0F, -14.0F);
		this.Beak.setTextureSize(64, 32);
		this.Beak.mirror = true;
		setRotation(this.Beak, 0.0F, 0.0F, 0.0F);

		this.Bottom = new ModelPart(this, 0, 27);
		this.Bottom.addCuboid(0.0F, 0.0F, 0.0F, 7, 1, 4);
		this.Bottom.setPivot(-3.0F, 18.0F, -3.0F);
		this.Bottom.setTextureSize(64, 32);
		this.Bottom.mirror = true;
		setRotation(this.Bottom, 0.0F, 0.0F, 0.0F);

		this.LeftLeg = new ModelPart(this, 22, 24);
		this.LeftLeg.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 3);
		this.LeftLeg.setPivot(1.0F, 19.0F, -3.0F);
		this.LeftLeg.setTextureSize(64, 32);
		this.LeftLeg.mirror = true;
		setRotation(this.LeftLeg, 0.0F, 0.0F, 0.0F);

		this.RightLeg = new ModelPart(this, 22, 24);
		this.RightLeg.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 3);
		this.RightLeg.setPivot(-3.0F, 19.0F, -3.0F);
		this.RightLeg.setTextureSize(64, 32);
		this.RightLeg.mirror = true;
		setRotation(this.RightLeg, 0.0F, 0.0F, 0.0F);

		this.WingLeft = new ModelPart(this, 34, 25);
		this.WingLeft.addCuboid(0.0F, 0.0F, 0.0F, 1, 3, 4);
		this.WingLeft.setPivot(4.0F, 13.0F, -5.0F);
		this.WingLeft.setTextureSize(64, 32);
		this.WingLeft.mirror = true;
		setRotation(this.WingLeft, 0.0F, 0.0F, 0.0F);

		this.WingRight = new ModelPart(this, 34, 25);
		this.WingRight.addCuboid(0.0F, 0.0F, 0.0F, 1, 3, 4);
		this.WingRight.setPivot(-4.0F, 13.0F, -5.0F);
		this.WingRight.setTextureSize(64, 32);
		this.WingRight.mirror = true;
		setRotation(this.WingRight, 0.0F, 0.0F, 0.0F);

		this.Head = new ModelPart(this, 35, 5);
		this.Head.addCuboid(0.0F, 0.0F, 0.0F, 5, 4, 4);
		this.Head.setPivot(-2.0F, 5.0F, -9.0F);
		this.Head.setTextureSize(64, 32);
		this.Head.mirror = true;
		setRotation(this.Head, 0.0F, 0.0F, 0.0F);
	}

	private void setRotation(ModelPart model, float x, float y, float z) {
		model.pitch = x;
		model.roll = y;
		model.yaw = z;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.RightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
		this.LeftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.Body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.Neck.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.Tail_Poof.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.Beak.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.Bottom.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.LeftLeg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.RightLeg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.WingLeft.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.WingRight.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.Head.render(matrices, vertices, light, overlay, red, green, blue, alpha);

	}
}
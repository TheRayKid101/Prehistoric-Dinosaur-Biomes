package net.eymbra.entities.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class IchthyosaurusEntityModel<T extends Entity> extends EntityModel<T> {
	public ModelPart Body;

	public ModelPart Head;

	public ModelPart Tail;

	public ModelPart DorsalFin;

	public ModelPart TailFin2;

	public ModelPart TailFin;

	public ModelPart Beak;

	public ModelPart BackFlipper1;

	public ModelPart BackFlipper2;

	public ModelPart FrontFlipper1;

	public ModelPart FrontFlipper2;

	public IchthyosaurusEntityModel() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.Body = new ModelPart(this, 34, 15);
		this.Body.addCuboid(0.0F, 0.0F, 0.0F, 8, 10, 7);
		this.Body.setPivot(-4.0F, 17.0F, -6.0F);
		this.Body.setTextureSize(64, 32);
		this.Body.mirror = true;
		setRotation(this.Body, 1.570796F, 0.0F, 0.0F);
		this.Head = new ModelPart(this, 40, 3);
		this.Head.addCuboid(0.0F, 0.0F, 0.0F, 6, 6, 6);
		this.Head.setPivot(-3.0F, 11.0F, -12.0F);
		this.Head.setTextureSize(64, 32);
		this.Head.mirror = true;
		setRotation(this.Head, 0.0F, 0.0F, 0.0F);
		this.Tail = new ModelPart(this, 18, 0);
		this.Tail.addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 7);
		this.Tail.setPivot(-2.0F, 11.0F, 3.0F);
		this.Tail.setTextureSize(64, 32);
		this.Tail.mirror = true;
		setRotation(this.Tail, 0.0F, 0.0F, 0.0F);
		this.DorsalFin = new ModelPart(this, 13, 11);
		this.DorsalFin.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 3);
		this.DorsalFin.setPivot(-0.5F, 6.0F, 0.0F);
		this.DorsalFin.setTextureSize(64, 32);
		this.DorsalFin.mirror = true;
		setRotation(this.DorsalFin, -0.5205006F, 0.0F, 0.0F);
		this.TailFin2 = new ModelPart(this, 21, 11);
		this.TailFin2.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 3);
		this.TailFin2.setPivot(-0.5F, 15.0F, 7.5F);
		this.TailFin2.setTextureSize(64, 32);
		this.TailFin2.mirror = true;
		setRotation(this.TailFin2, 0.5204921F, 0.0F, 0.0F);
		this.TailFin = new ModelPart(this, 21, 11);
		this.TailFin.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 3);
		this.TailFin.setPivot(-0.5F, 7.0F, 10.0F);
		this.TailFin.setTextureSize(64, 32);
		this.TailFin.mirror = true;
		setRotation(this.TailFin, -0.5205006F, 0.0F, 0.0F);
		this.Beak = new ModelPart(this, 23, 13);
		this.Beak.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 6);
		this.Beak.setPivot(-1.5F, 14.0F, -18.0F);
		this.Beak.setTextureSize(64, 32);
		this.Beak.mirror = true;
		setRotation(this.Beak, 0.0F, 0.0F, 0.0F);
		this.BackFlipper1 = new ModelPart(this, 40, 4);
		this.BackFlipper1.addCuboid(0.0F, 0.0F, 0.0F, 1, 3, 2);
		this.BackFlipper1.setPivot(3.0F, 17.0F, 2.0F);
		this.BackFlipper1.setTextureSize(64, 32);
		this.BackFlipper1.mirror = true;
		setRotation(this.BackFlipper1, 0.5235988F, 0.0F, -0.5235988F);
		this.BackFlipper2 = new ModelPart(this, 40, 4);
		this.BackFlipper2.addCuboid(0.0F, 0.0F, 0.0F, 1, 3, 2);
		this.BackFlipper2.setPivot(-4.0F, 16.5F, 2.0F);
		this.BackFlipper2.setTextureSize(64, 32);
		this.BackFlipper2.mirror = true;
		setRotation(this.BackFlipper2, 0.5235988F, 0.0F, 0.5235988F);
		this.FrontFlipper1 = new ModelPart(this, 26, 22);
		this.FrontFlipper1.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 3);
		this.FrontFlipper1.setPivot(3.0F, 17.0F, -5.0F);
		this.FrontFlipper1.setTextureSize(64, 32);
		this.FrontFlipper1.mirror = true;
		setRotation(this.FrontFlipper1, 0.5235988F, 0.0F, -0.5235988F);
		this.FrontFlipper2 = new ModelPart(this, 26, 22);
		this.FrontFlipper2.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 3);
		this.FrontFlipper2.setPivot(-4.0F, 16.5F, -5.0F);
		this.FrontFlipper2.setTextureSize(64, 32);
		this.FrontFlipper2.mirror = true;
		setRotation(this.FrontFlipper2, 0.5235988F, 0.0F, 0.5235988F);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.Body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.Head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.Tail.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.DorsalFin.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.TailFin2.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.TailFin.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.Beak.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.BackFlipper1.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.BackFlipper2.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.FrontFlipper1.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.FrontFlipper2.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	private void setRotation(ModelPart model, float x, float y, float z) {
		model.pitch = x;
		model.roll = y;
		model.yaw = z;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
//		this.Body.pitch = headPitch * 0.017453292F;
//		this.Body.yaw = headYaw * 0.017453292F;
//		if (Entity.squaredHorizontalLength(entity.getVelocity()) > 1.0E-7D) {
//			ModelPart var10000 = this.Body;
//			var10000.pitch += -0.05F + -0.05F * MathHelper.cos(animationProgress * 0.3F);
//			this.Tail.yaw = -0.1F * MathHelper.cos(animationProgress * 0.3F);
//			this.TailFin.yaw = -0.2F * MathHelper.cos(animationProgress * 0.3F);
//		}

	}
}
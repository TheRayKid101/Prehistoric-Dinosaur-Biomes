package net.eymbra.entities.model;

import java.util.Arrays;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class HadrosaurEntityModel<T extends Entity> extends DinosaurModel<T> {
	private ModelPart[] bodyParts;
	private ImmutableList<ModelPart> bodyPartList;
	private ModelPart[] legParts1;
	private ModelPart[] legParts2;

	public HadrosaurEntityModel() {
		this.bodyParts = new ModelPart[6];
		this.legParts1 = new ModelPart[4];
		this.legParts2 = new ModelPart[4];

		this.textureWidth = 128;
		this.textureHeight = 64;

		/*
		 * *********************************** BODY PARTS
		 * ***********************************
		 */
		this.bodyParts[0] = new ModelPart(this, 0, 0);
		this.bodyParts[0].addCuboid(-4.0F, -4.0F, -8.0F, 5, 5, 10);
		this.bodyParts[0].setPivot(1.5F, 12.0F, -11.0F);
		this.bodyParts[0].setTextureSize(128, 64);
		this.bodyParts[0].mirror = true;
		setRotation(this.bodyParts[0], 0.0872665F, 0.0F, 0.0F);

		this.bodyParts[1] = new ModelPart(this, 35, 0);
		this.bodyParts[1].addCuboid(-5.0F, -10.0F, -7.0F, 10, 16, 8);
		this.bodyParts[1].setPivot(0.0F, 11.0F, 3.0F);
		this.bodyParts[1].setTextureSize(128, 64);
		this.bodyParts[1].mirror = true;
		setRotation(this.bodyParts[1], 1.5707964F, 0.0F, 0.0F);

		this.bodyParts[2] = new ModelPart(this, 52, 25);
		this.bodyParts[2].addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 6);
		this.bodyParts[2].setPivot(-2.0F, 12.0F, 17.0F);
		this.bodyParts[2].setTextureSize(128, 64);
		this.bodyParts[2].mirror = true;
		setRotation(this.bodyParts[2], -0.0872665F, 0.0F, 0.0F);

		this.bodyParts[3] = new ModelPart(this, 23, 37);
		this.bodyParts[3].addCuboid(0.0F, 0.0F, 0.0F, 5, 5, 9);
		this.bodyParts[3].setPivot(-2.5F, 10.0F, 9.0F);
		this.bodyParts[3].setTextureSize(128, 64);
		this.bodyParts[3].mirror = true;
		setRotation(this.bodyParts[3], -0.1570796F, 0.0F, 0.0F);

		this.bodyParts[4] = new ModelPart(this, 72, 0);
		this.bodyParts[4].addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 8);
		this.bodyParts[4].setPivot(-2.0F, 9.0F, -10.0F);
		this.bodyParts[4].setTextureSize(128, 64);
		this.bodyParts[4].mirror = true;
		setRotation(this.bodyParts[4], -0.7853982F, 0.0F, 0.0F);

		this.bodyParts[5] = new ModelPart(this, 73, 13);
		this.bodyParts[5].addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 15);
		this.bodyParts[5].setPivot(-1.5F, 8.5F, -19.0F);
		this.bodyParts[5].setTextureSize(128, 64);
		this.bodyParts[5].mirror = true;
		setRotation(this.bodyParts[5], 0.1745329F, 0.0F, 0.0F);

		/*
		 * *********************************** LEG PARTS ONE
		 * ***********************************
		 */
		this.legParts1[0] = new ModelPart(this, 0, 17);
		this.legParts1[0].addCuboid(-2.0F, 0.0F, -2.0F, 4, 7, 4);
		this.legParts1[0].setPivot(-4.0F, 14.0F, 7.0F);
		this.legParts1[0].setTextureSize(128, 64);
		this.legParts1[0].mirror = true;
		setRotation(this.legParts1[0], 0.0F, 0.0F, 0.0F);

		this.legParts1[1] = new ModelPart(this, 0, 29);
		this.legParts1[1].addCuboid(-2.0F, 0.0F, -2.0F, 4, 5, 4);
		this.legParts1[1].setPivot(5.0F, 16.0F, -5.0F);
		this.legParts1[1].setTextureSize(128, 64);
		this.legParts1[1].mirror = true;
		setRotation(this.legParts1[1], 0.0F, 0.0F, 0.0F);

		this.legParts1[2] = new ModelPart(this, 0, 39);
		this.legParts1[2].addCuboid(0.0F, 5.0F, 0.0F, 3, 3, 3);
		this.legParts1[2].setPivot(3.5F, 15.25F, -4.25F);
		this.legParts1[2].setTextureSize(128, 64);
		this.legParts1[2].mirror = true;
		setRotation(this.legParts1[2], -0.3490659F, 0.0F, 0.0F);

		this.legParts1[3] = new ModelPart(this, 0, 39);
		this.legParts1[3].addCuboid(0.0F, 5.0F, 0.0F, 3, 4, 3);
		this.legParts1[3].setPivot(-5.5F, 15.0F, 5.75F);
		this.legParts1[3].setTextureSize(128, 64);
		this.legParts1[3].mirror = true;
		setRotation(this.legParts1[3], 0.0F, 0.0F, 0.0F);

		/*
		 * *********************************** LEG PARTS TWO
		 * ***********************************
		 */
		this.legParts2[0] = new ModelPart(this, 0, 17);
		this.legParts2[0].addCuboid(-2.0F, 0.0F, -2.0F, 4, 7, 4);
		this.legParts2[0].setPivot(5.0F, 14.0F, 7.0F);
		this.legParts2[0].setTextureSize(128, 64);
		this.legParts2[0].mirror = true;
		setRotation(this.legParts2[0], 0.0F, 0.0F, 0.0F);

		this.legParts2[1] = new ModelPart(this, 0, 29);
		this.legParts2[1].addCuboid(-2.0F, 0.0F, -2.0F, 4, 5, 4);
		this.legParts2[1].setPivot(-5.0F, 16.0F, -5.0F);
		this.legParts2[1].setTextureSize(128, 64);
		this.legParts2[1].mirror = true;
		setRotation(this.legParts2[1], 0.0F, 0.0F, 0.0F);

		this.legParts2[2] = new ModelPart(this, 0, 39);
		this.legParts2[2].addCuboid(-3.0F, 5.0F, 0.0F, 3, 3, 3);
		this.legParts2[2].setPivot(-3.5F, 15.3F, -4.25F);
		this.legParts2[2].setTextureSize(128, 64);
		this.legParts2[2].mirror = true;
		setRotation(this.legParts2[2], -0.3490659F, 0.0F, 0.0F);

		this.legParts2[3] = new ModelPart(this, 0, 39);
		this.legParts2[3].addCuboid(0.0F, 5.0F, 0.0F, 3, 4, 3);
		this.legParts2[3].setPivot(3.5F, 15.0F, 5.75F);
		this.legParts2[3].setTextureSize(128, 64);
		this.legParts2[3].mirror = true;
		setRotation(this.legParts2[3], 0.0F, 0.0F, 0.0F);

		Builder<ModelPart> builderBody = ImmutableList.builder();
		builderBody.addAll(Arrays.asList(this.bodyParts));
		builderBody.addAll(Arrays.asList(this.legParts1));
		builderBody.addAll(Arrays.asList(this.legParts2));
		this.bodyPartList = builderBody.build();
	}
	
	@Override
	public Iterable<ModelPart> getBodyParts() {
		return this.bodyPartList;
	}

	private void setRotation(ModelPart model, float x, float y, float z) {
		model.pitch = x;
		model.roll = y;
		model.yaw = z;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		for (int i = 0; i < legParts1.length; i++) {
			float flag = i == legParts1.length - 2 ? -0.3490659F : 0;

			this.legParts1[i].pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance + flag;
		}

		for (int i = 0; i < legParts2.length; i++) {
			float flag = i == legParts2.length - 2 ? -0.3490659F : 0;

			this.legParts2[i].pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance + flag;
		}
	}

	@Override
	public Iterable<ModelPart> getTailParts() {
		return null;
	}
}
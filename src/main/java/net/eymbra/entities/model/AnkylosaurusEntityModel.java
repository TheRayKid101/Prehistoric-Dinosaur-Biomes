/**GENERATE FILE SPECX PROJECTE7 RJXB FORGE 1.4.2 ENTITY MODEL TO 1.15.2 MODEL**/
package net.eymbra.entities.model;

import java.util.Arrays;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class AnkylosaurusEntityModel<T extends Entity> extends DinosaurModel<T> {
	private ModelPart[] bodyParts;
	private ImmutableList<ModelPart> bodyPartList;
	private ModelPart[] legParts1;
	private ModelPart[] legParts2;
	
	public AnkylosaurusEntityModel() {
		this.bodyParts = new ModelPart[12];
		this.legParts1 = new ModelPart[4];
		this.legParts2 = new ModelPart[4];

		this.textureWidth = 128;
		this.textureHeight = 64;

		/*
		 * *********************************** BODY PARTS
		 * ***********************************
		 */
		this.bodyParts[0] = new ModelPart(this, 0, 0);
		this.bodyParts[0].addCuboid(0.0F, 0.0F, 0.0F, 8, 7, 8);
		this.bodyParts[0].setPivot(-4.0F, 7.5F, -9.0F);
		this.bodyParts[0].setTextureSize(128, 64);
		this.bodyParts[0].mirror = true;
		setRotation(this.bodyParts[0], 0.0F, 0.0F, 0.0F);
		
		this.bodyParts[1] = new ModelPart(this, 0, 15);
		this.bodyParts[1].addCuboid(0.0F, 0.0F, 0.0F, 4, 6, 4);
		this.bodyParts[1].setPivot(-2.0F, 10.5F, -12.5F);
		this.bodyParts[1].setTextureSize(128, 64);
		this.bodyParts[1].mirror = true;
		setRotation(this.bodyParts[1], 0.4363323F, 0.0F, 0.0F);
		
		this.bodyParts[2] = new ModelPart(this, 24, 0);
		this.bodyParts[2].addCuboid(0.0F, 0.0F, 0.0F, 6, 2, 5);
		this.bodyParts[2].setPivot(-3.0F, 7.5F, -6.5F);
		this.bodyParts[2].setTextureSize(128, 64);
		this.bodyParts[2].mirror = true;
		setRotation(this.bodyParts[2], 0.1745329F, 0.0F, 0.0F);
		
		this.bodyParts[3] = new ModelPart(this, 32, 7);
		this.bodyParts[3].addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 3);
		this.bodyParts[3].setPivot(3.0F, 7.2F, -4.5F);
		this.bodyParts[3].setTextureSize(128, 64);
		this.bodyParts[3].mirror = true;
		setRotation(this.bodyParts[3], 0.1745329F, 0.0F, 0.0F);
		
		this.bodyParts[4] = new ModelPart(this, 44, 7);
		this.bodyParts[4].addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 3);
		this.bodyParts[4].setPivot(-6.0F, 7.2F, -4.5F);
		this.bodyParts[4].setTextureSize(128, 64);
		this.bodyParts[4].mirror = true;
		setRotation(this.bodyParts[4], 0.1745329F, 0.0F, 0.0F);
		
		this.bodyParts[5] = new ModelPart(this, 32, 7);
		this.bodyParts[5].addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 3);
		this.bodyParts[5].setPivot(3.0F, 11.5F, -4.0F);
		this.bodyParts[5].setTextureSize(128, 64);
		this.bodyParts[5].mirror = true;
		setRotation(this.bodyParts[5], 0.0F, 0.0F, 0.1745329F);
		
		this.bodyParts[6] = new ModelPart(this, 44, 7);
		this.bodyParts[6].addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 3);
		this.bodyParts[6].setPivot(-6.0F, 12.0F, -4.0F);
		this.bodyParts[6].setTextureSize(128, 64);
		this.bodyParts[6].mirror = true;
		setRotation(this.bodyParts[6], 0.0F, 0.0F, -0.1745329F);
		
		this.bodyParts[7] = new ModelPart(this, 68, 0);
		this.bodyParts[7].addCuboid(0.0F, 0.0F, 0.0F, 14, 10, 16);
		this.bodyParts[7].setPivot(-7.0F, 6.0F, 0.0F);
		this.bodyParts[7].setTextureSize(128, 64);
		this.bodyParts[7].mirror = true;
		setRotation(this.bodyParts[7], 0.0F, 0.0F, 0.0F);
		
		this.bodyParts[8] = new ModelPart(this, 56, 26);
		this.bodyParts[8].addCuboid(0.0F, 0.0F, 0.0F, 18, 2, 18);
		this.bodyParts[8].setPivot(-9.0F, 10.0F, -1.0F);
		this.bodyParts[8].setTextureSize(128, 64);
		this.bodyParts[8].mirror = true;
		setRotation(this.bodyParts[8], 0.0F, 0.0F, 0.0F);
		
		this.bodyParts[8] = new ModelPart(this, 46, 0);
		this.bodyParts[8].addCuboid(0.0F, 0.0F, 0.0F, 4, 5, 2);
		this.bodyParts[8].setPivot(-2.0F, 8.0F, -1.0F);
		this.bodyParts[8].setTextureSize(128, 64);
		this.bodyParts[8].mirror = true;
		setRotation(this.bodyParts[8], 0.0F, 0.0F, 0.0F);
		
		this.bodyParts[9] = new ModelPart(this, 104, 46);
		this.bodyParts[9].addCuboid(0.0F, 0.0F, 0.0F, 6, 6, 6);
		this.bodyParts[9].setPivot(-3.0F, 7.0F, 16.0F);
		this.bodyParts[9].setTextureSize(128, 64);
		this.bodyParts[9].mirror = true;
		setRotation(this.bodyParts[9], -0.2617994F, 0.0F, 0.0F);
		
		this.bodyParts[10] = new ModelPart(this, 80, 46);
		this.bodyParts[10].addCuboid(0.0F, 0.0F, 0.0F, 6, 6, 6);
		this.bodyParts[10].setPivot(-3.0F, 9.0F, 28.0F);
		this.bodyParts[10].setTextureSize(128, 64);
		this.bodyParts[10].mirror = true;
		setRotation(this.bodyParts[10], 0.0F, 0.0F, 0.0F);
		
		this.bodyParts[11] = new ModelPart(this, 56, 46);
		this.bodyParts[11].addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 8);
		this.bodyParts[11].setPivot(-2.0F, 10.0F, 20.0F);
		this.bodyParts[11].setTextureSize(128, 64);
		this.bodyParts[11].mirror = true;
		setRotation(this.bodyParts[11], 0.0F, 0.0F, 0.0F);
		
		/*
		 * *********************************** LEG PARTS ONE
		 * ***********************************
		 */
		this.legParts1[0] = new ModelPart(this, 18, 24);
		this.legParts1[0].addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 4);
		this.legParts1[0].setPivot(5.0F, 14.0F, 1.0F);
		this.legParts1[0].setTextureSize(128, 64);
		this.legParts1[0].mirror = true;
		setRotation(this.legParts1[0], 0.0F, 0.0F, 0.0F);
		
		this.legParts1[1] = new ModelPart(this, 0, 25);
		this.legParts1[1].addCuboid(0.0F, 0.0F, 0.0F, 4, 5, 5);
		this.legParts1[1].setPivot(5.0F, 13.0F, 10.0F);
		this.legParts1[1].setTextureSize(128, 64);
		this.legParts1[1].mirror = true;
		setRotation(this.legParts1[1], 0.0F, 0.0F, 0.0F);
		
		this.legParts1[2] = new ModelPart(this, 0, 35);
		this.legParts1[2].addCuboid(0.0F, 3.0F, 0.0F, 3, 4, 4);
		this.legParts1[2].setPivot(5.0F, 14.0F, 2.0F);
		this.legParts1[2].setTextureSize(128, 64);
		this.legParts1[2].mirror = true;
		setRotation(this.legParts1[2], 0.0F, 0.0F, 0.0F);
		
		this.legParts1[3] = new ModelPart(this, 0, 35);
		this.legParts1[3].addCuboid(0.0F, 3.0F, 0.0F, 3, 4, 4);
		this.legParts1[3].setPivot(5.0F, 14.0F, 12.0F);
		this.legParts1[3].setTextureSize(128, 64);
		this.legParts1[3].mirror = true;
		setRotation(this.legParts1[3], 0.0F, 0.0F, 0.0F);
		
		/*
		 * *********************************** LEG PARTS TWO
		 * ***********************************
		 */
		this.legParts2[0] = new ModelPart(this, 18, 24);
		this.legParts2[0].addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 4);
		this.legParts2[0].setPivot(-9.0F, 14.0F, 1.0F);
		this.legParts2[0].setTextureSize(128, 64);
		this.legParts2[0].mirror = true;
		setRotation(this.legParts2[0], 0.0F, 0.0F, 0.0F);
		
		this.legParts2[1] = new ModelPart(this, 0, 25);
		this.legParts2[1].addCuboid(0.0F, 0.0F, 0.0F, 4, 5, 5);
		this.legParts2[1].setPivot(-9.0F, 13.0F, 10.0F);
		this.legParts2[1].setTextureSize(128, 64);
		this.legParts2[1].mirror = true;
		setRotation(this.legParts2[1], 0.0F, 0.0F, 0.0F);
		
		this.legParts2[2] = new ModelPart(this, 0, 35);
		this.legParts2[2].addCuboid(0.0F, 3.0F, 0.0F, 3, 4, 4);
		this.legParts2[2].setPivot(-8.0F, 14.0F, 2.0F);
		this.legParts2[2].setTextureSize(128, 64);
		this.legParts2[2].mirror = true;
		setRotation(this.legParts2[2], 0.0F, 0.0F, 0.0F);
		
		this.legParts2[3] = new ModelPart(this, 0, 35);
		this.legParts2[3].addCuboid(0.0F, 3.0F, 0.0F, 3, 4, 4);
		this.legParts2[3].setPivot(-8.0F, 14.0F, 12.0F);
		this.legParts2[3].setTextureSize(128, 64);
		this.legParts2[3].mirror = true;
		setRotation(this.legParts2[3], 0.0F, 0.0F, 0.0F);
		
		Builder<ModelPart> builderBody = ImmutableList.builder();
		builderBody.addAll(Arrays.asList(this.bodyParts));
		builderBody.addAll(Arrays.asList(this.legParts1));
		builderBody.addAll(Arrays.asList(this.legParts2));
		this.bodyPartList = builderBody.build();
	}

	private void setRotation(ModelPart model, float x, float y, float z) {
		model.pitch = x;
		model.roll = y;
		model.yaw = z;
	}

	@Override
	public Iterable<ModelPart> getBodyParts() {
		return this.bodyPartList;
	}

	@Override
	public Iterable<ModelPart> getTailParts() {
		return null;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		for (int i = 0; i < legParts1.length; i++) {
			this.legParts1[i].pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
		}

		for (int i = 0; i < legParts2.length; i++) {
			this.legParts2[i].pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
		}
	}
}
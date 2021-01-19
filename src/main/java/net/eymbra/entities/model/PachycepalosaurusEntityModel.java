package net.eymbra.entities.model;

import java.util.Arrays;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class PachycepalosaurusEntityModel<T extends Entity> extends DinosaurModel<T> {
	private ModelPart[] bodyParts;
	private ModelPart[] legOne;
	private ModelPart[] legTwo;
	private ModelPart[] tailParts;
	private ImmutableList<ModelPart> bodyPartList;
	private ImmutableList<ModelPart> tailPartList;
	private ModelPart tail_peice;
	
	public PachycepalosaurusEntityModel() {
		this.bodyParts = new ModelPart[10];
		this.legOne    = new ModelPart[3];
		this.legTwo    = new ModelPart[3];
		this.tailParts = new ModelPart[1];
		
		this.textureWidth = 64;
		this.textureHeight = 32;
		
		//Body
		this.bodyParts[0] = new ModelPart(this, 0, 0);
		this.bodyParts[0].addCuboid(0.0F, 0.0F, 0.0F, 7, 8, 7);
		this.bodyParts[0].setPivot(-3.5F, 2.2F, -17.0F);
		this.bodyParts[0].setTextureSize(64, 32);
		this.bodyParts[0].mirror = true;

		setRotation(this.bodyParts[0], -0.4886922F, 0.0F, 0.0F);
		this.bodyParts[1] = new ModelPart(this, 0, 15);
		this.bodyParts[1].addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 4);
		this.bodyParts[1].setPivot(-2.0F, 8.5F, -18.5F);
		this.bodyParts[1].setTextureSize(64, 32);
		this.bodyParts[1].mirror = true;

		setRotation(this.bodyParts[1], 0.0F, 0.0F, 0.0F);
		this.bodyParts[2] = new ModelPart(this, 10, 23);
		this.bodyParts[2].addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 4);
		this.bodyParts[2].setPivot(-2.0F, 6.5F, -11.5F);
		this.bodyParts[2].setTextureSize(64, 32);
		this.bodyParts[2].mirror = true;

		setRotation(this.bodyParts[2], -0.4886922F, 0.0F, 0.0F);
		this.bodyParts[3] = new ModelPart(this, 21, 0);
		this.bodyParts[3].addCuboid(0.0F, 0.0F, 0.0F, 8, 2, 4);
		this.bodyParts[3].setPivot(-4.0F, 5.6F, -17.0F);
		this.bodyParts[3].setTextureSize(64, 32);
		this.bodyParts[3].mirror = true;

		setRotation(this.bodyParts[3], 1.082104F, 0.0F, 0.0F);
		this.bodyParts[4] = new ModelPart(this, 37, 8);
		this.bodyParts[4].addCuboid(0.0F, 0.0F, 0.0F, 5, 6, 6);
		this.bodyParts[4].setPivot(-2.5F, 8.0F, -9.0F);
		this.bodyParts[4].setTextureSize(64, 32);
		this.bodyParts[4].mirror = true;
		setRotation(this.bodyParts[4], -0.3141593F, 0.0F, 0.0F);

		this.bodyParts[5] = new ModelPart(this, 32, 6);
		this.bodyParts[5].addCuboid(0.0F, 0.0F, 0.0F, 8, 7, 8);
		this.bodyParts[5].setPivot(-4.0F, 9.5F, -6.5F);
		this.bodyParts[5].setTextureSize(64, 32);
		this.bodyParts[5].mirror = true;
		setRotation(this.bodyParts[5], 0.0F, 0.0F, 0.0F);

		this.bodyParts[6] = new ModelPart(this, 45, 0);
		this.bodyParts[6].addCuboid(0.0F, 0.0F, 0.0F, 2, 4, 2);
		this.bodyParts[6].setPivot(2.5F, 11.0F, -10.0F);
		this.bodyParts[6].setTextureSize(64, 32);
		this.bodyParts[6].mirror = true;
		setRotation(this.bodyParts[6], 0.0F, 0.0F, 0.0F);

		this.bodyParts[7] = new ModelPart(this, 45, 0);
		this.bodyParts[7].addCuboid(0.0F, 0.0F, 0.0F, 2, 4, 2);
		this.bodyParts[7].setPivot(-4.5F, 11.0F, -10.0F);
		this.bodyParts[7].setTextureSize(64, 32);
		this.bodyParts[7].mirror = true;
		setRotation(this.bodyParts[7], 0.0F, 0.0F, 0.0F);

		this.bodyParts[8] = new ModelPart(this, 28, 6);
		this.bodyParts[8].addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 2);
		this.bodyParts[8].setPivot(2.5F, 14.0F, -9.0F);
		this.bodyParts[8].setTextureSize(64, 32);
		this.bodyParts[8].mirror = true;
		setRotation(this.bodyParts[8], -0.3490659F, 0.0F, 0.0F);

		this.bodyParts[9] = new ModelPart(this, 28, 6);
		this.bodyParts[9].addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 2);
		this.bodyParts[9].setPivot(-4.5F, 14.0F, -9.0F);
		this.bodyParts[9].setTextureSize(64, 32);
		this.bodyParts[9].mirror = true;
		setRotation(this.bodyParts[9], -0.3490659F, 0.0F, 0.0F);
		
		//Legs
		this.legOne[0] = new ModelPart(this, 26, 22);
		this.legOne[0].addCuboid(0.0F, 0.0F, 0.0F, 3, 6, 4);
		this.legOne[0].setPivot(4.0F, 12.5F, -2.5F);
		this.legOne[0].setTextureSize(64, 32);
		this.legOne[0].mirror = true;
		setRotation(this.legOne[0], 0.0F, 0.0F, 0.0F);
		
		this.legOne[1] = new ModelPart(this, 0, 23);
		this.legOne[1].addCuboid(0.0F, 4.5F, 0.0F, 2, 6, 3);
		this.legOne[1].setPivot(4.0F, 12.5F, 0.0F);
		this.legOne[1].setTextureSize(64, 32);
		this.legOne[1].mirror = true;
		setRotation(this.legOne[1], -0.2617994F, 0.0F, 0.0F);
		
		this.legOne[2] = new ModelPart(this, 16, 15);
		this.legOne[2].addCuboid(0.0F, 9.5F, 0.0F, 3, 2, 4);
		this.legOne[2].setPivot(4.0F, 12.5F, -3.0F);
		this.legOne[2].setTextureSize(64, 32);
		this.legOne[2].mirror = true;
		setRotation(this.legOne[2], 0.0F, 0.0F, 0.0F);
		
		this.legTwo[0] = new ModelPart(this, 26, 22);
		this.legTwo[0].addCuboid(0.0F, 0.0F, 0.0F, 3, 6, 4);
		this.legTwo[0].setPivot(-7.0F, 12.5F, -2.5F);
		this.legTwo[0].setTextureSize(64, 32);
		this.legTwo[0].mirror = true;
		setRotation(this.legTwo[0], 0.0F, 0.0F, 0.0F);
		
		this.legTwo[1] = new ModelPart(this, 0, 23);
		this.legTwo[1].addCuboid(0.0F, 4.5F, 0.0F, 2, 6, 3);
		this.legTwo[1].setPivot(-6.0F, 12.5F, 0.0F);
		this.legTwo[1].setTextureSize(64, 32);
		this.legTwo[1].mirror = true;
		setRotation(this.legTwo[1], -0.2617994F, 0.0F, 0.0F);
		
		this.legTwo[2] = new ModelPart(this, 16, 15);
		this.legTwo[2].addCuboid(0.0F, 9.5F, 0.0F, 3, 2, 4);
		this.legTwo[2].setPivot(-7.0F, 12.5F, -3.0F);
		this.legTwo[2].setTextureSize(64, 32);
		this.legTwo[2].mirror = true;
		setRotation(this.legTwo[2], 0.0F, 0.0F, 0.0F);
		
		//Tail
		this.tail_peice = new ModelPart(this, 32, 6);
		this.tail_peice.addCuboid(-2.0F, 0.0F, 0.0F, 4, 3, 8);
		this.tail_peice.setPivot(3.0F, 1.0F, 5.0F);
		this.tail_peice.setTextureSize(64, 32);
		this.tail_peice.mirror = true;
		setRotation(this.tail_peice, -0.1F, -0.3F, 0.0F);
		
		this.tailParts[0] = new ModelPart(this, 40, 21);
		this.tailParts[0].addCuboid(0.0F, 0.0F, 0.0F, 6, 5, 6);
		this.tailParts[0].setPivot(-3.0F, 10.5F, 1.5F);
		this.tailParts[0].setTextureSize(64, 32);
		this.tailParts[0].mirror = true;
		setRotation(this.tailParts[0], -0.05F, 0.0F, 0.0F);
		this.tailParts[0].addChild(tail_peice);
		
		Builder<ModelPart> builderBody = ImmutableList.builder();
		builderBody.addAll(Arrays.asList(this.bodyParts));
		builderBody.addAll(Arrays.asList(this.legOne));
		builderBody.addAll(Arrays.asList(this.legTwo));
		this.bodyPartList = builderBody.build();
		
		Builder<ModelPart> builderTail = ImmutableList.builder();
		builderTail.addAll(Arrays.asList(this.tailParts));
		this.tailPartList = builderTail.build();
	}
	
	private void setRotation(ModelPart model, float x, float y, float z) {
		model.pitch = x;
		model.roll = y;
		model.yaw = z;
	}
	
	public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		this.tailParts[0].yaw = MathHelper.cos((float) (limbSwing * Math.PI / 6)) * 1.0F * limbSwingAmount / 2;
		this.tail_peice.yaw   = MathHelper.cos((float) (limbSwing * Math.PI / 6)) * 2.0F * limbSwingAmount / 2;
	}
	
	@Override
	public Iterable<ModelPart> getBodyParts() {
		return this.bodyPartList;
	}
	
	@Override
	public Iterable<ModelPart> getTailParts() {
		return this.tailPartList;
	}
	
	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.setLivingAnimations(entity, limbAngle, limbDistance, 0);
		
		for (int i = 0; i < legOne.length; i++) {
			this.legOne[i].pitch = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance;
		}
		
		for (int i = 0; i < legTwo.length; i++) {
			this.legTwo[i].pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
		}
	}
}
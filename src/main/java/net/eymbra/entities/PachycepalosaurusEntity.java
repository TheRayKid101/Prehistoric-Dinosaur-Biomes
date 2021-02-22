package net.eymbra.entities;

import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.sounds.EymbraSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class PachycepalosaurusEntity extends AnimalEntity {
	private static final Ingredient BREEDING_INGREDIENT;
	private int drinkWaterTimer;
	private EatGrassGoal drinkWaterGoal;

	protected PachycepalosaurusEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initGoals() {
		this.drinkWaterGoal = new EatGrassGoal(this);
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(2, new PachycepalosaurusEntity.HeadButtGoal());
		this.goalSelector.add(3, new TemptGoal(this, 1.0D, false, BREEDING_INGREDIENT));
		this.goalSelector.add(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.add(5, this.drinkWaterGoal);
		this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
	}

	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return world.getBlockState(pos.down()).isOf(EymbraBlocks.PREHISTORIC_GRASS_BLOCK) ? 10.0F : world.getBrightness(pos) - 0.5F;
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return this.isBaby() ? dimensions.height * 0.85F : dimensions.height * 0.92F;
	}

	@Override
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		return world.getBlockState(this.getBlockPos().down()).isOf(EymbraBlocks.PREHISTORIC_RED_ROCK) || world.getBlockState(this.getBlockPos().down()).isOf(EymbraBlocks.VOLCANIC_ROCK);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return BREEDING_INGREDIENT.test(stack);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return EymbraSoundEvents.PACHYCEPALOSAURUS_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return EymbraSoundEvents.PACHYCEPALOSAURUS_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EymbraSoundEvents.PACHYCEPALOSAURUS_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.15F, 1.0F);
	}

	@Override
	protected void mobTick() {
		this.drinkWaterTimer = this.drinkWaterGoal.getTimer();
		super.mobTick();
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 10) {
			this.drinkWaterTimer = 60;
		} else {
			super.handleStatus(status);
		}

	}

	@Environment(EnvType.CLIENT)
	public float getNeckAngle(float delta) {
		if (this.drinkWaterTimer <= 0) {
			return 0.0F;
		} else if (this.drinkWaterTimer >= 4 && this.drinkWaterTimer <= 36) {
			float f = (this.drinkWaterTimer - 4 - delta) / 32.0F;
			return 1.0F + MathHelper.sin(f * 2.7F);
		} else {
			return 0;
		}
	}

	@Override
	public void tickMovement() {
		if (this.world.isClient) {
			this.drinkWaterTimer = Math.max(0, this.drinkWaterTimer - 1);
		}

		super.tickMovement();
	}

	@Environment(EnvType.CLIENT)
	public float getHeadAngle(float delta) {
		if (this.drinkWaterTimer > 4 && this.drinkWaterTimer <= 36) {
			float f = (this.drinkWaterTimer - 4 - delta) / 32.0F;
			return 0.21991149F * MathHelper.sin(f * 8.7F);
		} else {
			return 0;
		}
	}

	public boolean isDrinking() {
		return this.drinkWaterTimer > 0;
	}

	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return EymbraEntities.PACHYCEPALOSAURUS.create(this.world);
	}

	public static Builder createPachycepalosaurusAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 300.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return false;
	}

	static class HeadButtGoal extends Goal {
		@Override
		public boolean canStart() {
			return false;
		}
	}

	static {
		BREEDING_INGREDIENT = Ingredient.ofItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
	}
}
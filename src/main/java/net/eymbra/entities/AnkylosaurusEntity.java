package net.eymbra.entities;

import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.sounds.EymbraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
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
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class AnkylosaurusEntity extends AnimalEntity {
	private static final Ingredient BREEDING_INGREDIENT;

	protected AnkylosaurusEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D));
		this.goalSelector.add(3, new TemptGoal(this, 1.0D, false, BREEDING_INGREDIENT));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
	}

	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return world.getBlockState(pos.down()).isOf(EymbraBlocks.PREHISTORIC_GRASS_BLOCK) ? 10.0F : world.getBrightness(pos) - 0.5F;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return this.isBaby() ? dimensions.height * 0.85F : dimensions.height * 0.92F;
	}

	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		return world.getBlockState(this.getBlockPos().down()).isOf(EymbraBlocks.PREHISTORIC_GRASS_BLOCK);
	}

	public boolean isBreedingItem(ItemStack stack) {
		return BREEDING_INGREDIENT.test(stack);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return EymbraSoundEvents.ANKYLOSAURUS_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return EymbraSoundEvents.ANKYLOSAURUS_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EymbraSoundEvents.ANKYLOSAURUS_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.15F, 1.0F);
	}

	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return (AnkylosaurusEntity) EymbraEntities.ANKYLOSAURUS.create(this.world);
	}

	public static Builder createAnkylosaursAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D);
	}

	public boolean canImmediatelyDespawn(double distanceSquared) {
		return false;
	}

	static {
		BREEDING_INGREDIENT = Ingredient.ofItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
	}
}
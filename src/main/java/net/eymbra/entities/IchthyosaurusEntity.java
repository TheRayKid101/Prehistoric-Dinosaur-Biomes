package net.eymbra.entities;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.DolphinLookControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.BreatheAirGoal;
import net.minecraft.entity.ai.goal.ChaseBoatGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.StructureFeature;

public class IchthyosaurusEntity extends WaterCreatureEntity {
	private static final TrackedData<BlockPos> TREASURE_POS;
	private static final TrackedData<Boolean> HAS_FISH;
	private static final TrackedData<Integer> MOISTNESS;
	private static final TargetPredicate CLOSE_PLAYER_PREDICATE;
	public static final Predicate<ItemEntity> CAN_TAKE;

	public IchthyosaurusEntity(EntityType<? extends IchthyosaurusEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new IchthyosaurusEntity.DolphinMoveControl(this);
		this.lookControl = new DolphinLookControl(this, 10);
		this.setCanPickUpLoot(true);
	}

	@Nullable
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
		this.setAir(this.getMaxAir());
		this.pitch = 0.0F;
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}

	public boolean canBreatheInWater() {
		return false;
	}

	protected void tickWaterBreathingAir(int air) {
	}

	public void setTreasurePos(BlockPos treasurePos) {
		this.dataTracker.set(TREASURE_POS, treasurePos);
	}

	public BlockPos getTreasurePos() {
		return (BlockPos) this.dataTracker.get(TREASURE_POS);
	}

	public boolean hasFish() {
		return (Boolean) this.dataTracker.get(HAS_FISH);
	}

	public void setHasFish(boolean hasFish) {
		this.dataTracker.set(HAS_FISH, hasFish);
	}

	public int getMoistness() {
		return (Integer) this.dataTracker.get(MOISTNESS);
	}

	public void setMoistness(int moistness) {
		this.dataTracker.set(MOISTNESS, moistness);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(TREASURE_POS, BlockPos.ORIGIN);
		this.dataTracker.startTracking(HAS_FISH, false);
		this.dataTracker.startTracking(MOISTNESS, 2400);
	}

	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		tag.putInt("TreasurePosX", this.getTreasurePos().getX());
		tag.putInt("TreasurePosY", this.getTreasurePos().getY());
		tag.putInt("TreasurePosZ", this.getTreasurePos().getZ());
		tag.putBoolean("GotFish", this.hasFish());
		tag.putInt("Moistness", this.getMoistness());
	}

	public void readCustomDataFromTag(CompoundTag tag) {
		int i = tag.getInt("TreasurePosX");
		int j = tag.getInt("TreasurePosY");
		int k = tag.getInt("TreasurePosZ");
		this.setTreasurePos(new BlockPos(i, j, k));
		super.readCustomDataFromTag(tag);
		this.setHasFish(tag.getBoolean("GotFish"));
		this.setMoistness(tag.getInt("Moistness"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initGoals() {
		this.goalSelector.add(0, new BreatheAirGoal(this));
		this.goalSelector.add(0, new MoveIntoWaterGoal(this));
		this.goalSelector.add(1, new IchthyosaurusEntity.LeadToNearbyTreasureGoal(this));
		this.goalSelector.add(2, new IchthyosaurusEntity.SwimWithPlayerGoal(this, 4.0D));
		this.goalSelector.add(4, new SwimAroundGoal(this, 1.0D, 10));
		this.goalSelector.add(4, new LookAroundGoal(this));
		this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(5, new IchthyosaurusJumpGoal(this, 10));
		this.goalSelector.add(6, new MeleeAttackGoal(this, 1.2000000476837158D, true));
		this.goalSelector.add(8, new IchthyosaurusEntity.PlayWithItemsGoal());
		this.goalSelector.add(8, new ChaseBoatGoal(this));
		this.goalSelector.add(9, new FleeEntityGoal(this, GuardianEntity.class, 8.0F, 1.0D, 1.0D));
		this.targetSelector.add(1, (new RevengeGoal(this, new Class[] { GuardianEntity.class })).setGroupRevenge());
	}

	public static DefaultAttributeContainer.Builder createIchthyosaurusAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.2000000476837158D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D);
	}

	protected EntityNavigation createNavigation(World world) {
		return new SwimNavigation(this, world);
	}

	public boolean tryAttack(Entity target) {
		boolean bl = target.damage(DamageSource.mob(this), (float) ((int) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
		if (bl) {
			this.dealDamage(this, target);
			this.playSound(SoundEvents.ENTITY_DOLPHIN_ATTACK, 1.0F, 1.0F);
		}

		return bl;
	}

	public int getMaxAir() {
		return 4800;
	}

	protected int getNextAirOnLand(int air) {
		return this.getMaxAir();
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.3F;
	}

	public int getLookPitchSpeed() {
		return 1;
	}

	public int getBodyYawSpeed() {
		return 1;
	}

	protected boolean canStartRiding(Entity entity) {
		return true;
	}

	public boolean canEquip(ItemStack stack) {
		EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(stack);
		if (!this.getEquippedStack(equipmentSlot).isEmpty()) {
			return false;
		} else {
			return equipmentSlot == EquipmentSlot.MAINHAND && super.canEquip(stack);
		}
	}

	protected void loot(ItemEntity item) {
		if (this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
			ItemStack itemStack = item.getStack();
			if (this.canPickupItem(itemStack)) {
				this.method_29499(item);
				this.equipStack(EquipmentSlot.MAINHAND, itemStack);
				this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0F;
				this.sendPickup(item, itemStack.getCount());
				item.remove();
			}
		}

	}

	public void tick() {
		super.tick();
		if (this.isAiDisabled()) {
			this.setAir(this.getMaxAir());
		} else {
			if (this.isWet()) {
				this.setMoistness(2400);
			} else {
				this.setMoistness(this.getMoistness() - 1);
				if (this.getMoistness() <= 0) {
					this.damage(DamageSource.DRYOUT, 1.0F);
				}

				if (this.onGround) {
					this.setVelocity(this.getVelocity().add((double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5D, (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F)));
					this.yaw = this.random.nextFloat() * 360.0F;
					this.onGround = false;
					this.velocityDirty = true;
				}
			}

			if (this.world.isClient && this.isTouchingWater() && this.getVelocity().lengthSquared() > 0.03D) {
				Vec3d vec3d = this.getRotationVec(0.0F);
				float f = MathHelper.cos(this.yaw * 0.017453292F) * 0.3F;
				float g = MathHelper.sin(this.yaw * 0.017453292F) * 0.3F;
				float h = 1.2F - this.random.nextFloat() * 0.7F;

				for (int i = 0; i < 2; ++i) {
					this.world.addParticle(ParticleTypes.DOLPHIN, this.getX() - vec3d.x * (double) h + (double) f, this.getY() - vec3d.y, this.getZ() - vec3d.z * (double) h + (double) g, 0.0D, 0.0D, 0.0D);
					this.world.addParticle(ParticleTypes.DOLPHIN, this.getX() - vec3d.x * (double) h - (double) f, this.getY() - vec3d.y, this.getZ() - vec3d.z * (double) h - (double) g, 0.0D, 0.0D, 0.0D);
				}
			}

		}
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 38) {
			this.spawnParticlesAround(ParticleTypes.HAPPY_VILLAGER);
		} else {
			super.handleStatus(status);
		}

	}

	@Environment(EnvType.CLIENT)
	private void spawnParticlesAround(ParticleEffect parameters) {
		for (int i = 0; i < 7; ++i) {
			double d = this.random.nextGaussian() * 0.01D;
			double e = this.random.nextGaussian() * 0.01D;
			double f = this.random.nextGaussian() * 0.01D;
			this.world.addParticle(parameters, this.getParticleX(1.0D), this.getRandomBodyY() + 0.2D, this.getParticleZ(1.0D), d, e, f);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!itemStack.isEmpty() && itemStack.getItem().isIn((Tag) ItemTags.FISHES)) {
			if (!this.world.isClient) {
				this.playSound(SoundEvents.ENTITY_DOLPHIN_EAT, 1.0F, 1.0F);
			}

			this.setHasFish(true);
			if (!player.abilities.creativeMode) {
				itemStack.decrement(1);
			}

			return ActionResult.success(this.world.isClient);
		} else {
			return super.interactMob(player, hand);
		}
	}

	@Override
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		return world.getFluidState(this.getBlockPos().down()).isIn(FluidTags.WATER);
	}
	
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_DOLPHIN_HURT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_DOLPHIN_DEATH;
	}

	@Nullable
	protected SoundEvent getAmbientSound() {
		return this.isTouchingWater() ? SoundEvents.ENTITY_DOLPHIN_AMBIENT_WATER : SoundEvents.ENTITY_DOLPHIN_AMBIENT;
	}

	protected SoundEvent getSplashSound() {
		return SoundEvents.ENTITY_DOLPHIN_SPLASH;
	}

	protected SoundEvent getSwimSound() {
		return SoundEvents.ENTITY_DOLPHIN_SWIM;
	}

	protected boolean isNearTarget() {
		BlockPos blockPos = this.getNavigation().getTargetPos();
		return blockPos != null ? blockPos.isWithinDistance(this.getPos(), 12.0D) : false;
	}

	public void travel(Vec3d movementInput) {
		if (this.canMoveVoluntarily() && this.isTouchingWater()) {
			this.updateVelocity(this.getMovementSpeed(), movementInput);
			this.move(MovementType.SELF, this.getVelocity());
			this.setVelocity(this.getVelocity().multiply(0.9D));
			if (this.getTarget() == null) {
				this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
			}
		} else {
			super.travel(movementInput);
		}

	}

	public boolean canBeLeashedBy(PlayerEntity player) {
		return true;
	}

	static {
		TREASURE_POS = DataTracker.registerData(IchthyosaurusEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
		HAS_FISH = DataTracker.registerData(IchthyosaurusEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		MOISTNESS = DataTracker.registerData(IchthyosaurusEntity.class, TrackedDataHandlerRegistry.INTEGER);
		CLOSE_PLAYER_PREDICATE = (new TargetPredicate()).setBaseMaxDistance(10.0D).includeTeammates().includeInvulnerable().includeHidden();
		CAN_TAKE = (itemEntity) -> {
			return !itemEntity.cannotPickup() && itemEntity.isAlive() && itemEntity.isTouchingWater();
		};
	}

	static class LeadToNearbyTreasureGoal extends Goal {
		private final IchthyosaurusEntity dolphin;
		private boolean noPathToStructure;

		LeadToNearbyTreasureGoal(IchthyosaurusEntity dolphin) {
			this.dolphin = dolphin;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStop() {
			return false;
		}

		public boolean canStart() {
			return this.dolphin.hasFish() && this.dolphin.getAir() >= 100;
		}

		public boolean shouldContinue() {
			BlockPos blockPos = this.dolphin.getTreasurePos();
			return !(new BlockPos((double) blockPos.getX(), this.dolphin.getY(), (double) blockPos.getZ())).isWithinDistance(this.dolphin.getPos(), 4.0D) && !this.noPathToStructure && this.dolphin.getAir() >= 100;
		}

		public void start() {
			if (this.dolphin.world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) this.dolphin.world;
				this.noPathToStructure = false;
				this.dolphin.getNavigation().stop();
				BlockPos blockPos = this.dolphin.getBlockPos();
				StructureFeature<?> structureFeature = (double) serverWorld.random.nextFloat() >= 0.5D ? StructureFeature.OCEAN_RUIN : StructureFeature.SHIPWRECK;
				BlockPos blockPos2 = serverWorld.locateStructure(structureFeature, blockPos, 50, false);
				if (blockPos2 == null) {
					StructureFeature<?> structureFeature2 = structureFeature.equals(StructureFeature.OCEAN_RUIN) ? StructureFeature.SHIPWRECK : StructureFeature.OCEAN_RUIN;
					BlockPos blockPos3 = serverWorld.locateStructure(structureFeature2, blockPos, 50, false);
					if (blockPos3 == null) {
						this.noPathToStructure = true;
						return;
					}

					this.dolphin.setTreasurePos(blockPos3);
				} else {
					this.dolphin.setTreasurePos(blockPos2);
				}

				serverWorld.sendEntityStatus(this.dolphin, (byte) 38);
			}
		}

		public void stop() {
			BlockPos blockPos = this.dolphin.getTreasurePos();
			if ((new BlockPos((double) blockPos.getX(), this.dolphin.getY(), (double) blockPos.getZ())).isWithinDistance(this.dolphin.getPos(), 4.0D) || this.noPathToStructure) {
				this.dolphin.setHasFish(false);
			}

		}

		public void tick() {
			World world = this.dolphin.world;
			if (this.dolphin.isNearTarget() || this.dolphin.getNavigation().isIdle()) {
				Vec3d vec3d = Vec3d.ofCenter(this.dolphin.getTreasurePos());
				Vec3d vec3d2 = TargetFinder.findTargetTowards(this.dolphin, 16, 1, vec3d, 0.39269909262657166D);
				if (vec3d2 == null) {
					vec3d2 = TargetFinder.findTargetTowards(this.dolphin, 8, 4, vec3d);
				}

				if (vec3d2 != null) {
					BlockPos blockPos = new BlockPos(vec3d2);
					if (!world.getFluidState(blockPos).isIn(FluidTags.WATER) || !world.getBlockState(blockPos).canPathfindThrough(world, blockPos, NavigationType.WATER)) {
						vec3d2 = TargetFinder.findTargetTowards(this.dolphin, 8, 5, vec3d);
					}
				}

				if (vec3d2 == null) {
					this.noPathToStructure = true;
					return;
				}

				this.dolphin.getLookControl().lookAt(vec3d2.x, vec3d2.y, vec3d2.z, (float) (this.dolphin.getBodyYawSpeed() + 20), (float) this.dolphin.getLookPitchSpeed());
				this.dolphin.getNavigation().startMovingTo(vec3d2.x, vec3d2.y, vec3d2.z, 1.3D);
				if (world.random.nextInt(80) == 0) {
					world.sendEntityStatus(this.dolphin, (byte) 38);
				}
			}

		}
	}

	static class SwimWithPlayerGoal extends Goal {
		private final IchthyosaurusEntity dolphin;
		private final double speed;
		private PlayerEntity closestPlayer;

		SwimWithPlayerGoal(IchthyosaurusEntity dolphin, double speed) {
			this.dolphin = dolphin;
			this.speed = speed;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			this.closestPlayer = this.dolphin.world.getClosestPlayer(IchthyosaurusEntity.CLOSE_PLAYER_PREDICATE, this.dolphin);
			if (this.closestPlayer == null) {
				return false;
			} else {
				return this.closestPlayer.isSwimming() && this.dolphin.getTarget() != this.closestPlayer;
			}
		}

		public boolean shouldContinue() {
			return this.closestPlayer != null && this.closestPlayer.isSwimming() && this.dolphin.squaredDistanceTo(this.closestPlayer) < 256.0D;
		}

		public void start() {
			this.closestPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 100));
		}

		public void stop() {
			this.closestPlayer = null;
			this.dolphin.getNavigation().stop();
		}

		public void tick() {
			this.dolphin.getLookControl().lookAt(this.closestPlayer, (float) (this.dolphin.getBodyYawSpeed() + 20), (float) this.dolphin.getLookPitchSpeed());
			if (this.dolphin.squaredDistanceTo(this.closestPlayer) < 6.25D) {
				this.dolphin.getNavigation().stop();
			} else {
				this.dolphin.getNavigation().startMovingTo(this.closestPlayer, this.speed);
			}

			if (this.closestPlayer.isSwimming() && this.closestPlayer.world.random.nextInt(6) == 0) {
				this.closestPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 100));
			}

		}
	}

	class PlayWithItemsGoal extends Goal {
		private int field_6758;

		private PlayWithItemsGoal() {
		}

		public boolean canStart() {
			if (this.field_6758 > IchthyosaurusEntity.this.age) {
				return false;
			} else {
				List<ItemEntity> list = IchthyosaurusEntity.this.world.getEntitiesByClass(ItemEntity.class, IchthyosaurusEntity.this.getBoundingBox().expand(8.0D, 8.0D, 8.0D), IchthyosaurusEntity.CAN_TAKE);
				return !list.isEmpty() || !IchthyosaurusEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
			}
		}

		public void start() {
			List<ItemEntity> list = IchthyosaurusEntity.this.world.getEntitiesByClass(ItemEntity.class, IchthyosaurusEntity.this.getBoundingBox().expand(8.0D, 8.0D, 8.0D), IchthyosaurusEntity.CAN_TAKE);
			if (!list.isEmpty()) {
				IchthyosaurusEntity.this.getNavigation().startMovingTo((Entity) list.get(0), 1.2000000476837158D);
				IchthyosaurusEntity.this.playSound(SoundEvents.ENTITY_DOLPHIN_PLAY, 1.0F, 1.0F);
			}

			this.field_6758 = 0;
		}

		public void stop() {
			ItemStack itemStack = IchthyosaurusEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
			if (!itemStack.isEmpty()) {
				this.spitOutItem(itemStack);
				IchthyosaurusEntity.this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
				this.field_6758 = IchthyosaurusEntity.this.age + IchthyosaurusEntity.this.random.nextInt(100);
			}

		}

		public void tick() {
			List<ItemEntity> list = IchthyosaurusEntity.this.world.getEntitiesByClass(ItemEntity.class, IchthyosaurusEntity.this.getBoundingBox().expand(8.0D, 8.0D, 8.0D), IchthyosaurusEntity.CAN_TAKE);
			ItemStack itemStack = IchthyosaurusEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
			if (!itemStack.isEmpty()) {
				this.spitOutItem(itemStack);
				IchthyosaurusEntity.this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			} else if (!list.isEmpty()) {
				IchthyosaurusEntity.this.getNavigation().startMovingTo((Entity) list.get(0), 1.2000000476837158D);
			}

		}

		private void spitOutItem(ItemStack stack) {
			if (!stack.isEmpty()) {
				double d = IchthyosaurusEntity.this.getEyeY() - 0.30000001192092896D;
				ItemEntity itemEntity = new ItemEntity(IchthyosaurusEntity.this.world, IchthyosaurusEntity.this.getX(), d, IchthyosaurusEntity.this.getZ(), stack);
				itemEntity.setPickupDelay(40);
				itemEntity.setThrower(IchthyosaurusEntity.this.getUuid());
				float g = IchthyosaurusEntity.this.random.nextFloat() * 6.2831855F;
				float h = 0.02F * IchthyosaurusEntity.this.random.nextFloat();
				itemEntity.setVelocity((double) (0.3F * -MathHelper.sin(IchthyosaurusEntity.this.yaw * 0.017453292F) * MathHelper.cos(IchthyosaurusEntity.this.pitch * 0.017453292F) + MathHelper.cos(g) * h), (double) (0.3F * MathHelper.sin(IchthyosaurusEntity.this.pitch * 0.017453292F) * 1.5F), (double) (0.3F * MathHelper.cos(IchthyosaurusEntity.this.yaw * 0.017453292F) * MathHelper.cos(IchthyosaurusEntity.this.pitch * 0.017453292F) + MathHelper.sin(g) * h));
				IchthyosaurusEntity.this.world.spawnEntity(itemEntity);
			}
		}
	}

	static class DolphinMoveControl extends MoveControl {
		private final IchthyosaurusEntity dolphin;

		public DolphinMoveControl(IchthyosaurusEntity dolphin) {
			super(dolphin);
			this.dolphin = dolphin;
		}

		public void tick() {
			if (this.dolphin.isTouchingWater()) {
				this.dolphin.setVelocity(this.dolphin.getVelocity().add(0.0D, 0.005D, 0.0D));
			}

			if (this.state == MoveControl.State.MOVE_TO && !this.dolphin.getNavigation().isIdle()) {
				double d = this.targetX - this.dolphin.getX();
				double e = this.targetY - this.dolphin.getY();
				double f = this.targetZ - this.dolphin.getZ();
				double g = d * d + e * e + f * f;
				if (g < 2.500000277905201E-7D) {
					this.entity.setForwardSpeed(0.0F);
				} else {
					float h = (float) (MathHelper.atan2(f, d) * 57.2957763671875D) - 90.0F;
					this.dolphin.yaw = this.changeAngle(this.dolphin.yaw, h, 10.0F);
					this.dolphin.bodyYaw = this.dolphin.yaw;
					this.dolphin.headYaw = this.dolphin.yaw;
					float i = (float) (this.speed * this.dolphin.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
					if (this.dolphin.isTouchingWater()) {
						this.dolphin.setMovementSpeed(i * 0.02F);
						float j = -((float) (MathHelper.atan2(e, (double) MathHelper.sqrt(d * d + f * f)) * 57.2957763671875D));
						j = MathHelper.clamp(MathHelper.wrapDegrees(j), -85.0F, 85.0F);
						this.dolphin.pitch = this.changeAngle(this.dolphin.pitch, j, 5.0F);
						float k = MathHelper.cos(this.dolphin.pitch * 0.017453292F);
						float l = MathHelper.sin(this.dolphin.pitch * 0.017453292F);
						this.dolphin.forwardSpeed = k * i;
						this.dolphin.upwardSpeed = -l * i;
					} else {
						this.dolphin.setMovementSpeed(i * 0.1F);
					}

				}
			} else {
				this.dolphin.setMovementSpeed(0.0F);
				this.dolphin.setSidewaysSpeed(0.0F);
				this.dolphin.setUpwardSpeed(0.0F);
				this.dolphin.setForwardSpeed(0.0F);
			}
		}
	}
}

package net.eymbra.entities;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.eymbra.biomes.EymbraBiomes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.ChunkRandom;

public class TarSlimeEntity extends MobEntity implements Monster {
	private static final TrackedData<Integer> SLIME_SIZE;
	public float targetStretch;
	public float stretch;
	public float lastStretch;
	private boolean onGroundLastTick;

	public TarSlimeEntity(EntityType<? extends TarSlimeEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new TarSlimeEntity.SlimeMoveControl(this);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initGoals() {
		this.goalSelector.add(1, new TarSlimeEntity.SwimmingGoal(this));
		this.goalSelector.add(2, new TarSlimeEntity.FaceTowardTargetGoal(this));
		this.goalSelector.add(3, new TarSlimeEntity.RandomLookGoal(this));
		this.goalSelector.add(5, new TarSlimeEntity.MoveGoal(this));
		this.targetSelector.add(1, new FollowTargetGoal(this, PlayerEntity.class, 10, true, false, (livingEntity) -> {
			return Math.abs(((Entity) livingEntity).getY() - this.getY()) <= 4.0D;
		}));
		this.targetSelector.add(3, new FollowTargetGoal(this, IronGolemEntity.class, true));
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SLIME_SIZE, 1);
	}

	protected void setSize(int size, boolean heal) {
		this.dataTracker.set(SLIME_SIZE, size);
		this.refreshPosition();
		this.calculateDimensions();
		this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((double) (size * size));
		this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue((double) (0.2F + 0.1F * (float) size));
		this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double) size);
		if (heal) {
			this.setHealth(this.getMaxHealth());
		}

		this.experiencePoints = size;
	}

	public int getSize() {
		return (Integer) this.dataTracker.get(SLIME_SIZE);
	}

	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		tag.putInt("Size", this.getSize() - 1);
		tag.putBoolean("wasOnGround", this.onGroundLastTick);
	}

	public void readCustomDataFromTag(CompoundTag tag) {
		int i = tag.getInt("Size");
		if (i < 0) {
			i = 0;
		}

		this.setSize(i + 1, false);
		super.readCustomDataFromTag(tag);
		this.onGroundLastTick = tag.getBoolean("wasOnGround");
	}

	public boolean isSmall() {
		return this.getSize() <= 1;
	}

	protected ParticleEffect getParticles() {
		return ParticleTypes.ASH;
	}

	protected boolean isDisallowedInPeaceful() {
		return this.getSize() > 0;
	}

	public void tick() {
		this.stretch += (this.targetStretch - this.stretch) * 0.5F;
		this.lastStretch = this.stretch;
		super.tick();
		if (this.onGround && !this.onGroundLastTick) {
			int i = this.getSize();

			for (int j = 0; j < i * 8; ++j) {
				float f = this.random.nextFloat() * 6.2831855F;
				float g = this.random.nextFloat() * 0.5F + 0.5F;
				float h = MathHelper.sin(f) * (float) i * 0.5F * g;
				float k = MathHelper.cos(f) * (float) i * 0.5F * g;
				this.world.addParticle(this.getParticles(), this.getX() + (double) h, this.getY(), this.getZ() + (double) k, 0.0D, 0.0D, 0.0D);
			}

			this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			this.targetStretch = -0.5F;
		} else if (!this.onGround && this.onGroundLastTick) {
			this.targetStretch = 1.0F;
		}

		this.onGroundLastTick = this.onGround;
		this.updateStretch();
	}

	protected void updateStretch() {
		this.targetStretch *= 0.6F;
	}

	protected int getTicksUntilNextJump() {
		return this.random.nextInt(20) + 10;
	}

	public void calculateDimensions() {
		double d = this.getX();
		double e = this.getY();
		double f = this.getZ();
		super.calculateDimensions();
		this.updatePosition(d, e, f);
	}

	public void onTrackedDataSet(TrackedData<?> data) {
		if (SLIME_SIZE.equals(data)) {
			this.calculateDimensions();
			this.yaw = this.headYaw;
			this.bodyYaw = this.headYaw;
			if (this.isTouchingWater() && this.random.nextInt(20) == 0) {
				this.onSwimmingStart();
			}
		}

		super.onTrackedDataSet(data);
	}

	@SuppressWarnings("unchecked")
	public EntityType<? extends TarSlimeEntity> getType() {
		return (EntityType<? extends TarSlimeEntity>) super.getType();
	}

	public void remove() {
		int i = this.getSize();
		if (!this.world.isClient && i > 1 && this.isDead()) {
			Text text = this.getCustomName();
			boolean bl = this.isAiDisabled();
			float f = (float) i / 4.0F;
			int j = i / 2;
			int k = 2 + this.random.nextInt(3);

			for (int l = 0; l < k; ++l) {
				float g = ((float) (l % 2) - 0.5F) * f;
				float h = ((float) (l / 2) - 0.5F) * f;
				TarSlimeEntity slimeEntity = (TarSlimeEntity) this.getType().create(this.world);
				if (this.isPersistent()) {
					slimeEntity.setPersistent();
				}

				slimeEntity.setCustomName(text);
				slimeEntity.setAiDisabled(bl);
				slimeEntity.setInvulnerable(this.isInvulnerable());
				slimeEntity.setSize(j, true);
				slimeEntity.refreshPositionAndAngles(this.getX() + (double) g, this.getY() + 0.5D, this.getZ() + (double) h, this.random.nextFloat() * 360.0F, 0.0F);
				this.world.spawnEntity(slimeEntity);
			}
		}

		super.remove();
	}

	public void pushAwayFrom(Entity entity) {
		super.pushAwayFrom(entity);
		if (entity instanceof IronGolemEntity && this.canAttack()) {
			this.damage((LivingEntity) entity);
		}

	}

	public void onPlayerCollision(PlayerEntity player) {
		if (this.canAttack()) {
			this.damage(player);
		}

	}

	protected void damage(LivingEntity target) {
		if (this.isAlive()) {
			int i = this.getSize();
			if (this.squaredDistanceTo(target) < 0.6D * (double) i * 0.6D * (double) i && this.canSee(target) && target.damage(DamageSource.mob(this), this.getDamageAmount())) {
				this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
				this.dealDamage(this, target);
			}
		}

	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.625F * dimensions.height;
	}

	protected boolean canAttack() {
		return !this.isSmall() && this.canMoveVoluntarily();
	}

	protected float getDamageAmount() {
		return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return this.isSmall() ? SoundEvents.BLOCK_HONEY_BLOCK_STEP : SoundEvents.BLOCK_HONEY_BLOCK_STEP;
	}

	protected SoundEvent getDeathSound() {
		return this.isSmall() ? SoundEvents.BLOCK_HONEY_BLOCK_STEP : SoundEvents.BLOCK_HONEY_BLOCK_STEP;
	}

	protected SoundEvent getSquishSound() {
		return this.isSmall() ? SoundEvents.BLOCK_HONEY_BLOCK_STEP : SoundEvents.BLOCK_HONEY_BLOCK_STEP;
	}

	protected Identifier getLootTableId() {
		return this.getSize() == 1 ? this.getType().getLootTableId() : LootTables.EMPTY;
	}

	public static boolean canSpawn(EntityType<TarSlimeEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		if (world.getDifficulty() != Difficulty.PEACEFUL) {
			if (Objects.equals(world.method_31081(pos), Optional.of(EymbraBiomes.BOG)) && pos.getY() > 50 && pos.getY() < 70 && random.nextFloat() < 0.5F && random.nextFloat() < world.getMoonSize() && world.getLightLevel(pos) <= random.nextInt(8)) {
				return canMobSpawn(type, world, spawnReason, pos, random);
			}

			if (!(world instanceof StructureWorldAccess)) {
				return false;
			}

			ChunkPos chunkPos = new ChunkPos(pos);
			boolean bl = ChunkRandom.getSlimeRandom(chunkPos.x, chunkPos.z, ((StructureWorldAccess) world).getSeed(), 987234911L).nextInt(10) == 0;
			if (random.nextInt(10) == 0 && bl && pos.getY() < 40) {
				return canMobSpawn(type, world, spawnReason, pos, random);
			}
		}

		return false;
	}

	protected float getSoundVolume() {
		return 0.4F * (float) this.getSize();
	}

	public int getLookPitchSpeed() {
		return 0;
	}

	protected boolean makesJumpSound() {
		return this.getSize() > 0;
	}

	protected void jump() {
		Vec3d vec3d = this.getVelocity();
		this.setVelocity(vec3d.x, (double) this.getJumpVelocity(), vec3d.z);
		this.velocityDirty = true;
	}

	@Nullable
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
		int i = this.random.nextInt(3);
		if (i < 2 && this.random.nextFloat() < 0.5F * difficulty.getClampedLocalDifficulty()) {
			++i;
		}

		int j = 1 << i;
		this.setSize(j, true);
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}

	private float getJumpSoundPitch() {
		float f = this.isSmall() ? 1.4F : 0.8F;
		return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
	}

	protected SoundEvent getJumpSound() {
		return this.isSmall() ? SoundEvents.BLOCK_HONEY_BLOCK_HIT : SoundEvents.BLOCK_HONEY_BLOCK_HIT;
	}

	public EntityDimensions getDimensions(EntityPose pose) {
		return super.getDimensions(pose).scaled(0.255F * (float) this.getSize());
	}

	static {
		SLIME_SIZE = DataTracker.registerData(TarSlimeEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	static class MoveGoal extends Goal {
		private final TarSlimeEntity slime;

		public MoveGoal(TarSlimeEntity slime) {
			this.slime = slime;
			this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
		}

		public boolean canStart() {
			return !this.slime.hasVehicle();
		}

		public void tick() {
			((TarSlimeEntity.SlimeMoveControl) this.slime.getMoveControl()).move(1.0D);
		}
	}

	static class SwimmingGoal extends Goal {
		private final TarSlimeEntity slime;

		public SwimmingGoal(TarSlimeEntity slime) {
			this.slime = slime;
			this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
			slime.getNavigation().setCanSwim(true);
		}

		public boolean canStart() {
			return (this.slime.isTouchingWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof TarSlimeEntity.SlimeMoveControl;
		}

		public void tick() {
			if (this.slime.getRandom().nextFloat() < 0.8F) {
				this.slime.getJumpControl().setActive();
			}

			((TarSlimeEntity.SlimeMoveControl) this.slime.getMoveControl()).move(1.2D);
		}
	}

	static class RandomLookGoal extends Goal {
		private final TarSlimeEntity slime;
		private float targetYaw;
		private int timer;

		public RandomLookGoal(TarSlimeEntity slime) {
			this.slime = slime;
			this.setControls(EnumSet.of(Goal.Control.LOOK));
		}

		public boolean canStart() {
			return this.slime.getTarget() == null && (this.slime.onGround || this.slime.isTouchingWater() || this.slime.isInLava() || this.slime.hasStatusEffect(StatusEffects.LEVITATION)) && this.slime.getMoveControl() instanceof TarSlimeEntity.SlimeMoveControl;
		}

		public void tick() {
			if (--this.timer <= 0) {
				this.timer = 40 + this.slime.getRandom().nextInt(60);
				this.targetYaw = (float) this.slime.getRandom().nextInt(360);
			}

			((TarSlimeEntity.SlimeMoveControl) this.slime.getMoveControl()).look(this.targetYaw, false);
		}
	}

	static class FaceTowardTargetGoal extends Goal {
		private final TarSlimeEntity slime;
		private int ticksLeft;

		public FaceTowardTargetGoal(TarSlimeEntity slime) {
			this.slime = slime;
			this.setControls(EnumSet.of(Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.slime.getTarget();
			if (livingEntity == null) {
				return false;
			} else if (!livingEntity.isAlive()) {
				return false;
			} else {
				return livingEntity instanceof PlayerEntity && ((PlayerEntity) livingEntity).abilities.invulnerable ? false : this.slime.getMoveControl() instanceof TarSlimeEntity.SlimeMoveControl;
			}
		}

		public void start() {
			this.ticksLeft = 300;
			super.start();
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = this.slime.getTarget();
			if (livingEntity == null) {
				return false;
			} else if (!livingEntity.isAlive()) {
				return false;
			} else if (livingEntity instanceof PlayerEntity && ((PlayerEntity) livingEntity).abilities.invulnerable) {
				return false;
			} else {
				return --this.ticksLeft > 0;
			}
		}

		public void tick() {
			this.slime.lookAtEntity(this.slime.getTarget(), 10.0F, 10.0F);
			((TarSlimeEntity.SlimeMoveControl) this.slime.getMoveControl()).look(this.slime.yaw, this.slime.canAttack());
		}
	}

	static class SlimeMoveControl extends MoveControl {
		private float targetYaw;
		private int ticksUntilJump;
		private final TarSlimeEntity slime;
		private boolean jumpOften;

		public SlimeMoveControl(TarSlimeEntity slime) {
			super(slime);
			this.slime = slime;
			this.targetYaw = 180.0F * slime.yaw / 3.1415927F;
		}

		public void look(float targetYaw, boolean jumpOften) {
			this.targetYaw = targetYaw;
			this.jumpOften = jumpOften;
		}

		public void move(double speed) {
			this.speed = speed;
			this.state = MoveControl.State.MOVE_TO;
		}

		public void tick() {
			this.entity.yaw = this.changeAngle(this.entity.yaw, this.targetYaw, 90.0F);
			this.entity.headYaw = this.entity.yaw;
			this.entity.bodyYaw = this.entity.yaw;
			if (this.state != MoveControl.State.MOVE_TO) {
				this.entity.setForwardSpeed(0.0F);
			} else {
				this.state = MoveControl.State.WAIT;
				if (this.entity.isOnGround()) {
					this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
					if (this.ticksUntilJump-- <= 0) {
						this.ticksUntilJump = this.slime.getTicksUntilNextJump();
						if (this.jumpOften) {
							this.ticksUntilJump /= 3;
						}

						this.slime.getJumpControl().setActive();
						if (this.slime.makesJumpSound()) {
							this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getJumpSoundPitch());
						}
					} else {
						this.slime.sidewaysSpeed = 0.0F;
						this.slime.forwardSpeed = 0.0F;
						this.entity.setMovementSpeed(0.0F);
					}
				} else {
					this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
				}

			}
		}
	}

	public static Builder createTarSlimeAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 300.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D);
	}
}

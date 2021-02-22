package net.eymbra.entities;

import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;

import net.eymbra.blocks.EymbraBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class DragonflyEntity extends AnimalEntity implements Flutterer {
	public float tailState;
	public float wingState;
	private boolean wingMovement;
	private BlockPos pointOfAttraction;

	protected DragonflyEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new FlightMoveControl(this, 20, true);
		this.pointOfAttraction = DragonflyEntity.this.getBlockPos();
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.fromTag(ItemTags.FLOWERS), false));
		this.goalSelector.add(5, new FollowParentGoal(this, 1.25D));
		this.goalSelector.add(9, new SwimGoal(this));
		this.goalSelector.add(2, new DragonflyEntity.WanderGoal());
	}

	private boolean isWithinDistance(BlockPos pos, int distance) {
		return pos.isWithinDistance(this.getBlockPos(), distance);
	}

	@Override
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		return world.getBlockState(this.getBlockPos().down()).isOf(EymbraBlocks.PREHISTORIC_GRASS_BLOCK);
	}

	class WanderGoal extends Goal {
		WanderGoal() {
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		@Override
		public boolean canStart() {
			return DragonflyEntity.this.navigation.isIdle() && DragonflyEntity.this.random.nextInt(10) == 0;
		}

		@Override
		public boolean shouldContinue() {
			return DragonflyEntity.this.navigation.isFollowingPath();
		}

		@Override
		public void start() {
			Vec3d vec3d = this.getRandomLocation();
			if (vec3d != null) {
				DragonflyEntity.this.navigation.startMovingAlong(DragonflyEntity.this.navigation.findPathTo((new BlockPos(vec3d)), 1), 1.0D);
			}
		}

		@Nullable
		private Vec3d getRandomLocation() {
			Vec3d vec3d3;
			if (!DragonflyEntity.this.isWithinDistance(DragonflyEntity.this.pointOfAttraction, 22)) {
				Vec3d vec3d = Vec3d.ofCenter(DragonflyEntity.this.pointOfAttraction);
				vec3d3 = vec3d.subtract(DragonflyEntity.this.getPos()).normalize();
			} else {
				vec3d3 = DragonflyEntity.this.getRotationVec(0.0F);
			}

			Vec3d vec3d4 = TargetFinder.findAirTarget(DragonflyEntity.this, 4, 3, vec3d3, 1.5707964F, 2, 1);
			return vec3d4 != null ? vec3d4 : TargetFinder.findGroundTarget(DragonflyEntity.this, 8, 4, -2, vec3d3, 1.5707963705062866D);
		}
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world) {
			@Override
			public boolean isValidPosition(BlockPos pos) {
				return !this.world.getBlockState(pos.down()).isAir();
			}
		};

		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(false);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return false;
	}

	@Override
	public void tick() {
		this.wingMovement = this.world.getBlockState(getBlockPos().down()).getBlock() == Blocks.AIR;

		super.tick();
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return dimensions.height / 2.0F;
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	@Override
	protected boolean hasWings() {
		return true;
	}

	public static Builder createDragonflyAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.8000000238418579D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.40000001192092896D);
	}

	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return EymbraEntities.DRAGONFLY.create(this.world);
	}

	public boolean isFlappingWings() {
		return this.wingMovement;
	}
}
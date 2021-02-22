package net.eymbra.prehistoric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.blocks.TimeBoxBlock;
import net.eymbra.dimensions.EymbraDimensionType;
import net.eymbra.dimensions.EymbraDimensions;
import net.eymbra.entities.IEntityEymbraDimension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

@Mixin(Entity.class)
public class EntityMixin implements IEntityEymbraDimension {
	private BlockPos lastEymbraPortalPosition;
	private boolean inEymbraPortal;
	private int eymbraPortalTime;

	@Shadow
	private float yaw;
	@Shadow
	private float pitch;

	@Override
	public void setInEymbraPortal(BlockPos pos) {
		Entity tmp_this = ((Entity) (Object) this);
		tmp_this.setVelocity(0, 0, 0);

		if (tmp_this.hasNetherPortalCooldown()) {
			tmp_this.resetNetherPortalCooldown();
		} else {
			if (!tmp_this.world.isClient && !pos.equals(this.lastEymbraPortalPosition)) {
				this.lastEymbraPortalPosition = pos.toImmutable();
			}

			this.inEymbraPortal = true;
		}
	}

	@Inject(at = @At("HEAD"), method = "tickNetherPortal")
	protected void tickNetherPortal(CallbackInfo ci) {
		RegistryKey<World> dimension = EymbraDimensionType.DIMENSION_WORLD_REGISTRY.get(EymbraDimensions.THE_PREHISTORIC_REGISTRY_KEY);
		Entity tmp_this = ((Entity) (Object) this);

		if (tmp_this.world instanceof ServerWorld) {
			int i = tmp_this.getMaxNetherPortalTime();
			ServerWorld serverWorld = (ServerWorld) tmp_this.world;
			if (this.inEymbraPortal) {
				MinecraftServer minecraftServer = serverWorld.getServer();
				RegistryKey<World> registryKey = tmp_this.world.getRegistryKey() == dimension ? World.OVERWORLD : dimension;
				ServerWorld serverWorld2 = minecraftServer.getWorld(registryKey);

				if (serverWorld2 != null && minecraftServer.isNetherAllowed() && !tmp_this.hasVehicle() && this.eymbraPortalTime++ >= i) {
					tmp_this.world.getProfiler().push("portal");
					this.eymbraPortalTime = i;
					tmp_this.resetNetherPortalCooldown();
					tmp_this.moveToWorld(serverWorld2);
					tmp_this.world.getProfiler().pop();
				}

				this.inEymbraPortal = false;
			} else {
				if (this.eymbraPortalTime > 0) {
					this.eymbraPortalTime -= 4;
				}

				if (this.eymbraPortalTime < 0) {
					this.eymbraPortalTime = 0;
				}
			}

			this.tickNetherPortalCooldown();
		}
	}

	@Inject(at = @At("HEAD"), method = "getTeleportTarget", cancellable = true)
	protected void getTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> ci) {
		RegistryKey<World> prehistoric_dimension = EymbraDimensionType.DIMENSION_WORLD_REGISTRY.get(EymbraDimensions.THE_PREHISTORIC_REGISTRY_KEY);
		boolean to_prehistoric = destination.getRegistryKey() == prehistoric_dimension;
		boolean to_surface_world = destination.getRegistryKey() == World.OVERWORLD;
		Entity entity = ((Entity) (Object) this);
		World world = entity.world;
		BlockPos blockPos = entity.getBlockPos();

//		BlockPos.Mutable pos = new BlockPos.Mutable(entity.chunkX, blockPos.getY() - 8, entity.chunkZ);
//		i: for (int x = 0; x < 16; x++) {
//			for (int y = 0; y < 16; y++) {
//				for (int z = 0; z < 16; z++) {
//					if (destination.getBlockState(pos.add(x, y, z)).getBlock() == EymbraBlocks.PREHISTORIC_TIME_BOX) {
//						System.out.println(destination.getBlockState(pos).getBlock());
//						blockPos = pos.up();
//						break i;
//					}
//				}
//			}
//		}

		if (to_prehistoric || to_surface_world) {
			if (destination.getBlockState(blockPos.down()).getBlock() != EymbraBlocks.PREHISTORIC_TIME_BOX) {
				destination.setBlockState(blockPos.up(), Blocks.AIR.getDefaultState());
				destination.setBlockState(blockPos, Blocks.AIR.getDefaultState());
				destination.setBlockState(blockPos.down(), EymbraBlocks.PREHISTORIC_TIME_BOX_STATE);
			}

			BlockState state = world.getBlockState(blockPos.down());
			Block block = state.getBlock();

			if (block == EymbraBlocks.PREHISTORIC_TIME_BOX) {
				((TimeBoxBlock) block).trigger(state, world, blockPos.down());
				world.setBlockState(blockPos.down(), Blocks.AIR.getDefaultState());
			}

			ci.setReturnValue(new TeleportTarget(new Vec3d(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D), this.getVelocity(), this.yaw, this.pitch));
		}

//		RegistryKey<World> dimension = EymbraDimensionType.DIMENSION_WORLD_REGISTRY.get(EymbraDimensions.THE_PREHISTORIC_REGISTRY_KEY);
//		boolean bl1 = destination.getRegistryKey() == dimension;
//		World world = ((Entity) (Object) this).world;
//
//		BlockPos blockPos2;
//		if (bl1 || world.getRegistryKey() == dimension) {
//			blockPos2 = ((Entity) (Object) this).getBlockPos();
//
//			BlockState north_state = destination.getBlockState(blockPos2.down().north());
//			BlockState south_state = destination.getBlockState(blockPos2.down().south());
//			BlockState east_state = destination.getBlockState(blockPos2.down().west());
//			BlockState west_state = destination.getBlockState(blockPos2.down().east());
//
//			//Search for timebox in cross pattern
//			BlockState blockState = north_state.getBlock() == EymbraBlocks.PREHISTORIC_TIME_BOX ? north_state :
//				(south_state.getBlock() == EymbraBlocks.PREHISTORIC_TIME_BOX ? south_state :
//				(west_state.getBlock() == EymbraBlocks.PREHISTORIC_TIME_BOX ? west_state :
//				(east_state.getBlock() == EymbraBlocks.PREHISTORIC_TIME_BOX ? east_state : null)));
//
//			BlockPos blockPos = north_state.getBlock() == EymbraBlocks.PREHISTORIC_TIME_BOX ? blockPos2.down().north() :
//				(south_state.getBlock() == EymbraBlocks.PREHISTORIC_TIME_BOX ? blockPos2.down().south() :
//				(west_state.getBlock() == EymbraBlocks.PREHISTORIC_TIME_BOX ? blockPos2.down().west() :
//				(east_state.getBlock() == EymbraBlocks.PREHISTORIC_TIME_BOX ? blockPos2.down().east() : null)));
//
//			if (blockState != null && blockPos != null) {
//				TimeBoxBlock timeBlock = (TimeBoxBlock) blockState.getBlock();
//
//				//Turn off portal
//				timeBlock.trigger(blockState, world, blockPos);
//				world.setBlockState(blockPos2, Blocks.AIR.getDefaultState());
//			} else {
//				destination.setBlockState(blockPos2.down(), EymbraBlocks.PREHISTORIC_TIME_BOX_STATE);
//			}
//
//			ci.setReturnValue(new TeleportTarget(new Vec3d((double) blockPos2.getX() + 0.5D, (double) blockPos2.getY(), (double) blockPos2.getZ() + 0.5D), this.getVelocity(), this.yaw, this.pitch));
//		}
	}

	@Shadow
	public Vec3d getVelocity() {
		return null;
	}

	@Shadow
	protected void tickNetherPortalCooldown() {
	}
}
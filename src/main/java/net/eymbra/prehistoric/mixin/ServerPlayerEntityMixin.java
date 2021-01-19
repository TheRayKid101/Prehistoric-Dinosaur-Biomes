package net.eymbra.prehistoric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.authlib.GameProfile;
import net.eymbra.dimensions.EymbraDimensionType;
import net.eymbra.dimensions.EymbraDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin extends PlayerEntity {
	public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}

	@SuppressWarnings("unused")
	private Vec3d enteredEymbraDimensionPos;
	@Shadow
	private Vec3d enteredNetherPos;

	@Inject(at = @At("HEAD"), method = "moveToWorld")
	public void moveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> ci) {
		RegistryKey<World> dimension = EymbraDimensionType.DIMENSION_WORLD_REGISTRY.get(EymbraDimensions.THE_PREHISTORIC_REGISTRY_KEY);
		
		ServerWorld serverWorld = this.getServerWorld();
		RegistryKey<World> registryKey = serverWorld.getRegistryKey();

		if (registryKey == World.END && destination.getRegistryKey() == World.OVERWORLD) {
		} else {
			TeleportTarget teleportTarget = this.getTeleportTarget(destination);
			if (teleportTarget != null) {
				if (registryKey == World.OVERWORLD && destination.getRegistryKey() == dimension) {
					//this.enteredEymbraDimensionPos = this.getPos();
					this.enteredNetherPos = this.getPos();
				}
			}
		}
	}
	
	@Shadow
	protected TeleportTarget getTeleportTarget(ServerWorld destination) {
		return null;
	}

	@Shadow
	public ServerWorld getServerWorld() {
		return null;
	}

	@Override
	public boolean isSpectator() {
		return false;
	}

	@Override
	public boolean isCreative() {
		return false;
	}
}
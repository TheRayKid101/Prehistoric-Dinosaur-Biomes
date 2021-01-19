package net.eymbra.prehistoric.mixin;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;

@Mixin(SpawnRestriction.class)
public class SpawnRestrictionMixin {
	@Shadow
	private static <T extends MobEntity> void register(EntityType<T> type, SpawnRestriction.Location location, Heightmap.Type heightmapType, SpawnRestriction.SpawnPredicate<T> predicate) {
	}

	@Inject(at = @At("HEAD"), method = "canSpawn")
	private static <T extends Entity> void canSpawn(EntityType<T> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> ci) {
	}

//	static {
//		register(EymbraEntities.HADROSAUR, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SpawnRestrictionMixin::canSpawnIgnoreLightLevel);
//		register(EymbraEntities.DRAGONFLY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SpawnRestrictionMixin::canSpawnIgnoreLightLevel);
//		register(EymbraEntities.TAR_SLIME, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TarSlimeEntity::canSpawn);
//		register(EymbraEntities.ICHTHYOSAURUS, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SpawnRestrictionMixin::canSpawn);
//	}
//
//	private static boolean canSpawnIgnoreLightLevel(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
//		return world.getBlockState(pos.down()).isOf(EymbraBlocks.PREHISTORIC_GRASS_BLOCK);
//	}
//	
//	private static boolean canSpawn(EntityType<? extends IchthyosaurusEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
//		return world.getBlockState(pos).isOf(Blocks.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
//	}
}
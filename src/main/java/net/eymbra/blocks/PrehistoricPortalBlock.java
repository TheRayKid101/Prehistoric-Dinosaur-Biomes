package net.eymbra.blocks;

import java.util.Random;
import net.eymbra.entities.IEntityEymbraDimension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PrehistoricPortalBlock extends Block {
	public PrehistoricPortalBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

//	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//		if (world.getDimension().isNatural() && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && random.nextInt(2000) < world.getDifficulty().getId()) {
//			while (world.getBlockState(pos).isOf(this)) {
//				pos = pos.down();
//			}
//
//			if (world.getBlockState(pos).allowsSpawning(world, pos, EntityType.ZOMBIFIED_PIGLIN)) {
//				Entity entity = EntityType.ZOMBIFIED_PIGLIN.spawn(world, (CompoundTag) null, (Text) null, (PlayerEntity) null, pos.up(), SpawnReason.STRUCTURE, false, false);
//				if (entity != null) {
//					entity.resetNetherPortalCooldown();
//				}
//			}
//		}
//
//	}

	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals() && world.getBlockState(entity.getBlockPos().down()).getBlock() == EymbraBlocks.PREHISTORIC_TIME_BOX) {
			((IEntityEymbraDimension)entity).setInEymbraPortal(pos);
		}

	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (random.nextInt(100) == 0) {
			world.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double d = (double) pos.getX() + random.nextDouble();
			double e = (double) pos.getY() + random.nextDouble();
			double f = (double) pos.getZ() + random.nextDouble();
			double g = ((double) random.nextFloat() - 0.5D) * 0.5D;
			double h = ((double) random.nextFloat() - 0.5D) * 0.5D;
			double j = ((double) random.nextFloat() - 0.5D) * 0.5D;
			int k = random.nextInt(2) * 2 - 1;
			if (!world.getBlockState(pos.west()).isOf(this) && !world.getBlockState(pos.east()).isOf(this)) {
				d = (double) pos.getX() + 0.5D + 0.25D * (double) k;
				g = (double) (random.nextFloat() * 2.0F * (float) k);
			} else {
				f = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
				j = (double) (random.nextFloat() * 2.0F * (float) k);
			}

			world.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, j);
		}

	}

	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}
}

package net.eymbra.blocks;

import java.util.Random;
import net.eymbra.dimensions.EymbraDimensions;
import net.eymbra.prehistoric.IEntityMixinAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PrehistoricPortalBlock extends Block {
	public PrehistoricPortalBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (world instanceof ServerWorld && !entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals()) {
			RegistryKey<World> registryKey = world.getRegistryKey() == EymbraDimensions.EYMBRA_WORLD_KEY ? World.OVERWORLD : EymbraDimensions.EYMBRA_WORLD_KEY;
			ServerWorld serverWorld = ((ServerWorld) world).getServer().getWorld(registryKey);
			if (serverWorld == null) {
				return;
			}

			((IEntityMixinAccess) entity).setInEymbraPortal(pos);
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (random.nextInt(100) == 0) {
			world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double d = pos.getX() + random.nextDouble();
			double e = pos.getY() + random.nextDouble();
			double f = pos.getZ() + random.nextDouble();
			double g = (random.nextFloat() - 0.5D) * 0.5D;
			double h = (random.nextFloat() - 0.5D) * 0.5D;
			double j = (random.nextFloat() - 0.5D) * 0.5D;
			int k = random.nextInt(2) * 2 - 1;
			if (!world.getBlockState(pos.west()).isOf(this) && !world.getBlockState(pos.east()).isOf(this)) {
				d = pos.getX() + 0.5D + 0.25D * k;
				g = random.nextFloat() * 2.0F * k;
			} else {
				f = pos.getZ() + 0.5D + 0.25D * k;
				j = random.nextFloat() * 2.0F * k;
			}

			world.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, j);
		}

	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}
}

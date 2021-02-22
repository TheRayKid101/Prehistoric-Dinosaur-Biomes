package net.eymbra.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class DodoEggEntity extends ThrownItemEntity {
	public DodoEggEntity(EntityType<? extends DodoEggEntity> entityType, World world) {
		super(entityType, world);
	}

	public DodoEggEntity(World world, LivingEntity owner) {
		super(EntityType.EGG, owner, world);
	}

	public DodoEggEntity(World world, double x, double y, double z) {
		super(EntityType.EGG, x, y, z, world);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 3) {
			for (int i = 0; i < 8; ++i) {
				this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), (this.random.nextFloat() - 0.5D) * 0.08D, (this.random.nextFloat() - 0.5D) * 0.08D,
						(this.random.nextFloat() - 0.5D) * 0.08D);
			}
		}

	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 0.0F);
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			if (this.random.nextInt(8) == 0) {
				int i = 1;
				if (this.random.nextInt(32) == 0) {
					i = 4;
				}

				for (int j = 0; j < i; ++j) {
					DodoEntity dodoEntity = EymbraEntities.DODO.create(this.world);
					dodoEntity.setBreedingAge(-24000);
					dodoEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, 0.0F);
					this.world.spawnEntity(dodoEntity);
				}
			}

			this.world.sendEntityStatus(this, (byte) 3);
			this.remove();
		}

	}

	@Override
	protected Item getDefaultItem() {
		return Items.EGG;
	}
}

package net.eymbra.particles;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class EymbraCustomSuspendParticle extends SpriteBillboardParticle {
	public EymbraCustomSuspendParticle(ClientWorld world, double x, double y, double z) {
		super(world, x, y - 0.125D, z);
		this.colorRed = 0.4F;
		this.colorGreen = 0.4F;
		this.colorBlue = 0.7F;
		this.setBoundingBoxSpacing(0.01F, 0.01F);
		this.scale *= this.random.nextFloat() * 0.6F + 0.2F;
		this.maxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
		this.collidesWithWorld = false;
	}

	public EymbraCustomSuspendParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		super(world, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
		this.setBoundingBoxSpacing(0.01F, 0.01F);
		this.scale *= this.random.nextFloat() * 0.6F + 0.6F;
		this.maxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
	}

	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}

	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.maxAge-- <= 0) {
			this.markDead();
		} else {
			this.move(this.velocityX, this.velocityY, this.velocityZ);
		}
	}

	@Environment(EnvType.CLIENT)
	public static class RedSandFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public RedSandFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			Random random = clientWorld.random;
			double j = random.nextGaussian() * 9.999999974752427E-7D;
			double k = random.nextGaussian() * 9.999999747378752E-5D;
			double l = random.nextGaussian() * 9.999999974752427E-7D;
			EymbraCustomSuspendParticle waterSuspendParticle = new EymbraCustomSuspendParticle(clientWorld, d, e, f, j, k, l);
			waterSuspendParticle.setSprite(this.spriteProvider);
			waterSuspendParticle.setColor(0.3F, 0.0F, 0.0F);
			waterSuspendParticle.setBoundingBoxSpacing(0.001F, 0.001F);
			return waterSuspendParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class RainforestDustFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public RainforestDustFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			Random random = clientWorld.random;
			double j = (double) random.nextFloat() * -1.9D * (double) random.nextFloat() * 0.1D;
			double k = (double) random.nextFloat() * -0.5D * (double) random.nextFloat() * 0.1D * 5.0D;
			double l = (double) random.nextFloat() * -1.9D * (double) random.nextFloat() * 0.1D;
			EymbraCustomSuspendParticle waterSuspendParticle = new EymbraCustomSuspendParticle(clientWorld, d, e, f, j, k, l);
			waterSuspendParticle.setSprite(this.spriteProvider);
			waterSuspendParticle.setColor(0.3F, 0.3F, 0.0F);
			waterSuspendParticle.setBoundingBoxSpacing(0.001F, 0.001F);
			return waterSuspendParticle;
		}
	}
}
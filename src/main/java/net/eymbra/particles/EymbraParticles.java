package net.eymbra.particles;

import net.eymbra.prehistoric.EymbraPrehistoric;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EymbraParticles {
	public static final DefaultParticleType RED_SAND;
	public static final DefaultParticleType RAINFOREST_DUST;

	public static void init() {
	}

	private static <T extends ParticleEffect> DefaultParticleType register(String name, boolean alwaysShow) {
		Identifier id = (Identifier) new Identifier(EymbraPrehistoric.MODID, name);
		return (DefaultParticleType) Registry.register(Registry.PARTICLE_TYPE, id, new Simple(alwaysShow));
	}

	public static class Simple extends DefaultParticleType {
		public Simple(boolean alwaysSpawn) {
			super(alwaysSpawn);
		}
	}

	static {
		RED_SAND = register("prehistoric_red_sand", false);
		ParticleFactoryRegistry.getInstance().register(RED_SAND, EymbraCustomSuspendParticle.RedSandFactory::new);

		RAINFOREST_DUST = register("prehistoric_rainforest_dust", false);
		ParticleFactoryRegistry.getInstance().register(RAINFOREST_DUST, EymbraCustomSuspendParticle.RainforestDustFactory::new);
	}
}
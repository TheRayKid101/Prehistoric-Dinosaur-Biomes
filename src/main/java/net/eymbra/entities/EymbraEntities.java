package net.eymbra.entities;

import net.eymbra.prehistoric.EymbraPrehistoric;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class EymbraEntities {
	public static final EntityType<HadrosaurEntity> HADROSAUR = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "hadrosaur"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HadrosaurEntity::new).dimensions(EntityDimensions.fixed(2.35F, 2.7F)).build());
	public static final EntityType<DragonflyEntity> DRAGONFLY = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "dragonfly"),
			FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, DragonflyEntity::new).trackRangeBlocks(8).dimensions(EntityDimensions.fixed(0.7F, 0.2F)).build());
	public static final EntityType<TarSlimeEntity> TAR_SLIME = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "tarslime"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TarSlimeEntity::new).dimensions(EntityDimensions.fixed(2.04F, 2.04F)).build());
	public static final EntityType<IchthyosaurusEntity> ICHTHYOSAURUS = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "ichthyosaurus"),
			FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, IchthyosaurusEntity::new).dimensions(EntityDimensions.fixed(1.95F, 2.2F)).build());
	public static final EntityType<PachycepalosaurusEntity> PACHYCEPALOSAURUS = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "pachycepalosaurus"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PachycepalosaurusEntity::new).dimensions(EntityDimensions.fixed(1.4F, 1.4F)).build());
	public static final EntityType<DodoEntity> DODO = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "dodo"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DodoEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.7F)).build());
	public static final EntityType<AnkylosaurusEntity> ANKYLOSAURUS = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "ankylosaurus"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AnkylosaurusEntity::new).dimensions(EntityDimensions.fixed(2.45F, 2.25F)).build());

	public static void init() {
		FabricDefaultAttributeRegistry.register(HADROSAUR, HadrosaurEntity.createHadrosaurAttributes());
		FabricDefaultAttributeRegistry.register(DRAGONFLY, DragonflyEntity.createDragonflyAttributes());
		FabricDefaultAttributeRegistry.register(TAR_SLIME, TarSlimeEntity.createTarSlimeAttributes());
		FabricDefaultAttributeRegistry.register(ICHTHYOSAURUS, IchthyosaurusEntity.createIchthyosaurusAttributes());
		FabricDefaultAttributeRegistry.register(PACHYCEPALOSAURUS, PachycepalosaurusEntity.createPachycepalosaurusAttributes());
		FabricDefaultAttributeRegistry.register(DODO, DodoEntity.createDodoAttributes());
		FabricDefaultAttributeRegistry.register(ANKYLOSAURUS, AnkylosaurusEntity.createAnkylosaursAttributes());

		SpawnRestrictionAccessor.callRegister(HADROSAUR, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
		SpawnRestrictionAccessor.callRegister(DRAGONFLY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
		SpawnRestrictionAccessor.callRegister(TAR_SLIME, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
		SpawnRestrictionAccessor.callRegister(ICHTHYOSAURUS, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
		SpawnRestrictionAccessor.callRegister(PACHYCEPALOSAURUS, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
		SpawnRestrictionAccessor.callRegister(DODO, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
		SpawnRestrictionAccessor.callRegister(ANKYLOSAURUS, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
	}
}
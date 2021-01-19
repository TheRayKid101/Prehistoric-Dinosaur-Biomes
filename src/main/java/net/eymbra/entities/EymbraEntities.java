package net.eymbra.entities;

import net.eymbra.entities.renderer.AnkylosaurusEntityRenderer;
import net.eymbra.entities.renderer.DodoEntityRenderer;
import net.eymbra.entities.renderer.DragonflyEntityRenderer;
import net.eymbra.entities.renderer.HadrosaurEntityRenderer;
import net.eymbra.entities.renderer.IchthyosaurusEntityRenderer;
import net.eymbra.entities.renderer.PachycepalosaurusEntityRenderer;
import net.eymbra.entities.renderer.TarSlimeEntityRenderer;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EymbraEntities {
	public static final EntityType<HadrosaurEntity> HADROSAUR = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "hadrosaur"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HadrosaurEntity::new).dimensions(EntityDimensions.fixed(1.95F, 2.2F)).build());
	public static final EntityType<DragonflyEntity> DRAGONFLY = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "dragonfly"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, DragonflyEntity::new).trackRangeBlocks(8).dimensions(EntityDimensions.fixed(0.7F, 0.6F)).build());
	public static final EntityType<TarSlimeEntity> TAR_SLIME = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "tarslime"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TarSlimeEntity::new).dimensions(EntityDimensions.fixed(2.04F, 2.04F)).build());
	public static final EntityType<IchthyosaurusEntity> ICHTHYOSAURUS = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "ichthyosaurus"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, IchthyosaurusEntity::new).dimensions(EntityDimensions.fixed(1.95F, 2.2F)).build());
	public static final EntityType<PachycepalosaurusEntity> PACHYCEPALOSAURUS = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "pachycepalosaurus"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PachycepalosaurusEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.7F)).build());
	public static final EntityType<DodoEntity> DODO = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "dodo"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DodoEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.7F)).build());
	public static final EntityType<AnkylosaurusEntity> ANKYLOSAURUS = Registry.register(Registry.ENTITY_TYPE, new Identifier(EymbraPrehistoric.MODID, "ankylosaurus"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AnkylosaurusEntity::new).dimensions(EntityDimensions.fixed(2.45F, 2.25F)).build());

	static {
		FabricDefaultAttributeRegistry.register(HADROSAUR, HadrosaurEntity.createHadrosaurAttributes());
		EntityRendererRegistry.INSTANCE.register(HADROSAUR, (entityRenderDispatcher, context) -> new HadrosaurEntityRenderer(entityRenderDispatcher));

		FabricDefaultAttributeRegistry.register(DRAGONFLY, DragonflyEntity.createDragonflyAttributes());
		EntityRendererRegistry.INSTANCE.register(DRAGONFLY, (entityRenderDispatcher, context) -> new DragonflyEntityRenderer(entityRenderDispatcher));

		FabricDefaultAttributeRegistry.register(TAR_SLIME, TarSlimeEntity.createTarSlimeAttributes());
		EntityRendererRegistry.INSTANCE.register(TAR_SLIME, (entityRenderDispatcher, context) -> new TarSlimeEntityRenderer(entityRenderDispatcher));

		FabricDefaultAttributeRegistry.register(ICHTHYOSAURUS, IchthyosaurusEntity.createIchthyosaurusAttributes());
		EntityRendererRegistry.INSTANCE.register(ICHTHYOSAURUS, (entityRenderDispatcher, context) -> new IchthyosaurusEntityRenderer(entityRenderDispatcher));

		FabricDefaultAttributeRegistry.register(PACHYCEPALOSAURUS, PachycepalosaurusEntity.createPachycepalosaurusAttributes());
		EntityRendererRegistry.INSTANCE.register(PACHYCEPALOSAURUS, (entityRenderDispatcher, context) -> new PachycepalosaurusEntityRenderer(entityRenderDispatcher));

		FabricDefaultAttributeRegistry.register(DODO, DodoEntity.createDodoAttributes());
		EntityRendererRegistry.INSTANCE.register(DODO, (entityRenderDispatcher, context) -> new DodoEntityRenderer(entityRenderDispatcher));
		
		FabricDefaultAttributeRegistry.register(ANKYLOSAURUS, AnkylosaurusEntity.createAnkylosaursAttributes());
		EntityRendererRegistry.INSTANCE.register(ANKYLOSAURUS, (entityRenderDispatcher, context) -> new AnkylosaurusEntityRenderer(entityRenderDispatcher));
	}

	public static void init() {
	}
}
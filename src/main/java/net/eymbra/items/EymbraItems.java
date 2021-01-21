package net.eymbra.items;

import net.eymbra.blocks.EymbraBlocks;
import net.eymbra.food.EymbraFoodComponents;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.EggItem;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class EymbraItems {
	public static final Item PREHISTORIC_TIME_BOX;

	public static final Item PREHISTORIC_GRASS_BLOCK;

	public static final Item PREHISTORIC_DIRT_BLOCK;

	public static final Item PREHISTORIC_LEPIDODENDRALES_LOG;

	public static final Item PREHISTORIC_LEPIDODENDRALES_LEAVES;

	public static final Item PREHISTORIC_LEPIDODENDRALES_SAPLING;

	public static final Item PREHISTORIC_MANGROVE_SAPLING;

	public static final Item PREHISTORIC_MANGROVE_LOG;

	public static final Item PREHISTORIC_MANGROVE_LEAVES;

	public static final Item PREHISTORIC_RED_ROCK;

	public static final Item VOLCANIC_ROCK;

	public static final Item PREHISTORIC_DARKWOOD_LOG;

	public static final Item PREHISTORIC_DARKWOOD_LEAVES;

	public static final Item PREHISTORIC_DARKWOOD_SAPLING;

	public static final Item PREHISTORIC_LEPIDODENDRALES_PLANKS;

	public static final Item PREHISTORIC_MANGROVE_PLANKS;

	public static final Item PREHISTORIC_DARKWOOD_PLANKS;

	public static final Item PREHISTORIC_REFERENCE_BOOK;

	public static final Item PREHISTORIC_LEPIDODENDRALES_STAIRS;

	public static final Item PREHISTORIC_MANGROVE_STAIRS;

	public static final Item PREHISTORIC_DARKWOOD_STAIRS;

	public static final Item PREHISTORIC_LEPIDODENDRALES_SLAB;

	public static final Item PREHISTORIC_MANGROVE_SLAB;

	public static final Item PREHISTORIC_DARKWOOD_SLAB;

	public static final Item PREHISTORIC_CALAMITES_LOG;

	public static final Item PREHISTORIC_CALAMITES_PLANKS;

	public static final Item PREHISTORIC_CALAMITES_STAIRS;

	public static final Item PREHISTORIC_CALAMITES_SLAB;

	public static final Item PREHISTORIC_CALAMITES_LEAVES;

	public static final Item PREHISTORIC_CALAMITES_SAPLING;

	public static final Item PREHISTORIC_APPLE;

	public static final Item PREHISTORIC_GROUND_APPLE;

	public static final Item PREHISTORIC_RAW_HADROSAUR;

	public static final Item PREHISTORIC_COOKED_HADROSAUR;

	public static final Item PREHISTORIC_AMMONITE_SHELL;

	public static final Item PREHISTORIC_CLAW_DAGGER;

	public static final Item PREHISTORIC_RAW_DODO;

	public static final Item PREHISTORIC_COOKED_DODO;

	public static final Item PREHISTORIC_DODO_EGG;

	public static final Item PREHISTORIC_RAW_ANKYLOSAURUS;

	public static final Item PREHISTORIC_COOKED_ANKYLOSAURUS;

	public static final Item PREHISTORIC_RAPTOR_CLAW;

	public static final Item PREHISTORIC_HUGE_DRAGONFLY;

	public static final Item NORMAL_GROUND_APPLE;

	public static final Item PREHISTORIC_SHORT_BUSH;

	public static final Item PREHISTORIC_DEAD_BUSH;

	public static final Item PREHISTORIC_FARMLAND;

	public static void init() {
	}

	@SuppressWarnings("unused")
	private static Item register(Block block) {
		return register(new BlockItem(block, new Item.Settings()));
	}

	private static Item register(Block block, ItemGroup group) {
		return register(new BlockItem(block, (new Item.Settings()).group(group)));
	}

	private static Item register(BlockItem item) {
		return register(item.getBlock(), item);
	}

	protected static Item register(Block block, Item item) {
		return register(Registry.BLOCK.getId(block), item);
	}

	private static Item register(String id, Item item) {
		return register(new Identifier(EymbraPrehistoric.MODID, id), item);
	}

	private static Item register(Identifier id, Item item) {
		if (item instanceof BlockItem) {
			((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
		}

		return Registry.register(Registry.ITEM, id, item);
	}

	static {
		PREHISTORIC_TIME_BOX = register(EymbraBlocks.PREHISTORIC_TIME_BOX, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_GRASS_BLOCK = register(EymbraBlocks.PREHISTORIC_GRASS_BLOCK, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_DIRT_BLOCK = register(EymbraBlocks.PREHISTORIC_DIRT_BLOCK, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_FARMLAND = register(EymbraBlocks.PREHISTORIC_FARMLAND, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_RED_ROCK = register(EymbraBlocks.PREHISTORIC_RED_ROCK, EymbraPrehistoric.PREHISTORIC_GROUP);
		VOLCANIC_ROCK = register(EymbraBlocks.VOLCANIC_ROCK, EymbraPrehistoric.PREHISTORIC_GROUP);

		PREHISTORIC_CALAMITES_PLANKS = register(EymbraBlocks.PREHISTORIC_CALAMITES_PLANKS, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_LEPIDODENDRALES_PLANKS = register(EymbraBlocks.PREHISTORIC_LEPIDODENDRALES_PLANKS, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_DARKWOOD_PLANKS = register(EymbraBlocks.PREHISTORIC_DARKWOOD_PLANKS, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_MANGROVE_PLANKS = register(EymbraBlocks.PREHISTORIC_MANGROVE_PLANKS, EymbraPrehistoric.PREHISTORIC_GROUP);

		PREHISTORIC_CALAMITES_LOG = register(EymbraBlocks.PREHISTORIC_CALAMITES_LOG, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_LEPIDODENDRALES_LOG = register(EymbraBlocks.PREHISTORIC_LEPIDODENDRALES_LOG, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_DARKWOOD_LOG = register(EymbraBlocks.PREHISTORIC_DARKWOOD_LOG, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_MANGROVE_LOG = register(EymbraBlocks.PREHISTORIC_MANGROVE_LOG, EymbraPrehistoric.PREHISTORIC_GROUP);

		PREHISTORIC_CALAMITES_SLAB = register(EymbraBlocks.PREHISTORIC_CALAMITES_SLAB, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_LEPIDODENDRALES_SLAB = register(EymbraBlocks.PREHISTORIC_LEPIDODENDRALES_SLAB, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_DARKWOOD_SLAB = register(EymbraBlocks.PREHISTORIC_DARKWOOD_SLAB, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_MANGROVE_SLAB = register(EymbraBlocks.PREHISTORIC_MANGROVE_SLAB, EymbraPrehistoric.PREHISTORIC_GROUP);

		PREHISTORIC_CALAMITES_STAIRS = register(EymbraBlocks.PREHISTORIC_CALAMITES_STAIRS, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_LEPIDODENDRALES_STAIRS = register(EymbraBlocks.PREHISTORIC_LEPIDODENDRALES_STAIRS, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_DARKWOOD_STAIRS = register(EymbraBlocks.PREHISTORIC_DARKWOOD_STAIRS, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_MANGROVE_STAIRS = register(EymbraBlocks.PREHISTORIC_MANGROVE_STAIRS, EymbraPrehistoric.PREHISTORIC_GROUP);

		PREHISTORIC_CALAMITES_LEAVES = register(EymbraBlocks.PREHISTORIC_CALAMITES_LEAVES, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_LEPIDODENDRALES_LEAVES = register(EymbraBlocks.PREHISTORIC_LEPIDODENDRALES_LEAVES, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_DARKWOOD_LEAVES = register(EymbraBlocks.PREHISTORIC_DARKWOOD_LEAVES, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_MANGROVE_LEAVES = register(EymbraBlocks.PREHISTORIC_MANGROVE_LEAVES, EymbraPrehistoric.PREHISTORIC_GROUP);

		PREHISTORIC_APPLE = register("prehistoric_apple", new Item((new Item.Settings()).group(ItemGroup.FOOD).food(FoodComponents.APPLE)));
		PREHISTORIC_GROUND_APPLE = register("prehistoric_ground_apple", new Item((new Item.Settings()).group(ItemGroup.FOOD).food(FoodComponents.APPLE)));
		NORMAL_GROUND_APPLE = register("normal_ground_apple", new Item((new Item.Settings()).group(ItemGroup.FOOD).food(FoodComponents.APPLE)));
		PREHISTORIC_RAW_HADROSAUR = register("prehistoric_raw_hadrosaur", new Item((new Item.Settings()).group(ItemGroup.FOOD).food(EymbraFoodComponents.HADROSAUR)));
		PREHISTORIC_COOKED_HADROSAUR = register("prehistoric_cooked_hadrosaur", new Item((new Item.Settings()).group(ItemGroup.FOOD).food(EymbraFoodComponents.COOKED_HADROSAUR)));
		PREHISTORIC_AMMONITE_SHELL = register("prehistoric_ammonite_shell", new Item((new Item.Settings()).group(ItemGroup.MATERIALS)));
		PREHISTORIC_DODO_EGG = register("prehistoric_dodo_egg", (new EggItem((new Item.Settings()).maxCount(16).group(ItemGroup.MATERIALS))));
		PREHISTORIC_RAPTOR_CLAW = register("prehistoric_raptor_claw", new Item((new Item.Settings()).group(ItemGroup.MISC)));
		PREHISTORIC_CLAW_DAGGER = register("prehistoric_claw_dagger", new Item((new Item.Settings()).group(ItemGroup.MISC)));
		PREHISTORIC_RAW_DODO = register("prehistoric_raw_dodo", new Item((new Item.Settings()).group(ItemGroup.FOOD).food(FoodComponents.CHICKEN)));
		PREHISTORIC_COOKED_DODO = register("prehistoric_cooked_dodo", new Item((new Item.Settings()).group(ItemGroup.FOOD).food(FoodComponents.COOKED_CHICKEN)));
		PREHISTORIC_RAW_ANKYLOSAURUS = register("prehistoric_raw_ankylosaurus", new Item((new Item.Settings()).group(ItemGroup.FOOD).food(EymbraFoodComponents.AKYLOSAURUS)));
		PREHISTORIC_COOKED_ANKYLOSAURUS = register("prehistoric_cooked_ankylosaurus", new Item((new Item.Settings()).group(ItemGroup.FOOD).food(EymbraFoodComponents.COOKED_AKYLOSAURUS)));
		PREHISTORIC_HUGE_DRAGONFLY = register("prehistoric_huge_dragonfly", new Item((new Item.Settings()).group(ItemGroup.MISC)));
		PREHISTORIC_REFERENCE_BOOK = register("ph_book", (new PrehistoricReferenceBookItem((new Item.Settings()).group(ItemGroup.MISC).rarity(Rarity.UNCOMMON))));

		PREHISTORIC_CALAMITES_SAPLING = register(EymbraBlocks.PREHISTORIC_CALAMITES_SAPLING, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_LEPIDODENDRALES_SAPLING = register(EymbraBlocks.PREHISTORIC_LEPIDODENDRALES_SAPLING, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_DARKWOOD_SAPLING = register(EymbraBlocks.PREHISTORIC_DARKWOOD_SAPLING, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_MANGROVE_SAPLING = register(EymbraBlocks.PREHISTORIC_MANGROVE_SAPLING, EymbraPrehistoric.PREHISTORIC_GROUP);

		PREHISTORIC_SHORT_BUSH = register(EymbraBlocks.PREHISTORIC_SHORT_BUSH, EymbraPrehistoric.PREHISTORIC_GROUP);
		PREHISTORIC_DEAD_BUSH = register(EymbraBlocks.PREHISTORIC_DEAD_BUSH, EymbraPrehistoric.PREHISTORIC_GROUP);

		// Any hoe item would do for this modifier
		((IEymbraTiltedBlocks) Items.WOODEN_HOE).addTiltedBlock(EymbraBlocks.PREHISTORIC_GRASS_BLOCK, EymbraBlocks.PREHISTORIC_FARMLAND_STATE);
		((IEymbraTiltedBlocks) Items.WOODEN_HOE).addTiltedBlock(EymbraBlocks.PREHISTORIC_DIRT_BLOCK, EymbraBlocks.PREHISTORIC_FARMLAND_STATE);
	}
}
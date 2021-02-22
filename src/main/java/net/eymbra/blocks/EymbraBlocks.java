package net.eymbra.blocks;

import net.eymbra.generator.CalamitesSaplingGenerator;
import net.eymbra.generator.DarkwoodSaplingGenerator;
import net.eymbra.generator.LepidodendralesSaplingGenerator;
import net.eymbra.generator.MangroveSaplingGenerator;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

public class EymbraBlocks {
	public static final Block PREHISTORIC_TIME_BOX;
	public static final BlockState PREHISTORIC_TIME_BOX_STATE;

	public static final Block PREHISTORIC_GRASS_BLOCK;
	public static final BlockState PREHISTORIC_GRASS_BLOCK_STATE;

	public static final Block PREHISTORIC_DIRT_BLOCK;
	public static final BlockState PREHISTORIC_DIRT_BLOCK_STATE;

	public static final Block PREHISTORIC_LEPIDODENDRALES_LOG;
	public static final BlockState PREHISTORIC_LEPIDODENDRALES_LOG_STATE;

	public static final Block PREHISTORIC_LEPIDODENDRALES_LEAVES;
	public static final BlockState PREHISTORIC_LEPIDODENDRALES_LEAVES_STATE;

	public static final Block PREHISTORIC_LEPIDODENDRALES_SAPLING;

	public static final Block PREHISTORIC_MANGROVE_SAPLING;

	public static final Block PREHISTORIC_SHORT_BUSH;
	public static final BlockState PREHISTORIC_SHORT_BUSH_STATE;

	public static final Block PREHISTORIC_DEAD_BUSH;
	public static final BlockState PREHISTORIC_DEAD_BUSH_STATE;

	public static final Block PREHISTORIC_MANGROVE_LOG;
	public static final BlockState PREHISTORIC_MANGROVE_LOG_STATE;

	public static final Block PREHISTORIC_MANGROVE_LEAVES;
	public static final BlockState PREHISTORIC_MANGROVE_LEAVES_STATE;

	public static final Block PREHISTORIC_PORTAL_BLOCK;
	public static final BlockState PREHISTORIC_PORTAL_BLOCK_STATE;

	public static final Block PREHISTORIC_RED_ROCK;
	public static final BlockState PREHISTORIC_RED_ROCK_STATE;

	public static final Block VOLCANIC_ROCK;
	public static final BlockState VOLCANIC_ROCK_STATE;

	public static final Block PREHISTORIC_DARKWOOD_LOG;
	public static final BlockState PREHISTORIC_DARKWOOD_LOG_STATE;

	public static final Block PREHISTORIC_DARKWOOD_LEAVES;
	public static final BlockState PREHISTORIC_DARKWOOD_LEAVES_STATE;

	public static final Block PREHISTORIC_DARKWOOD_SAPLING;

	public static final Block PREHISTORIC_LEPIDODENDRALES_PLANKS;
	public static final BlockState PREHISTORIC_LEPIDODENDRALES_PLANKS_STATE;

	public static final Block PREHISTORIC_MANGROVE_PLANKS;
	public static final BlockState PREHISTORIC_MANGROVE_PLANKS_STATE;

	public static final Block PREHISTORIC_DARKWOOD_PLANKS;
	public static final BlockState PREHISTORIC_DARKWOOD_PLANKS_STATE;

	public static final Block PREHISTORIC_LEPIDODENDRALES_STAIRS;
	public static final BlockState PREHISTORIC_LEPIDODENDRALES_STAIRS_STATE;

	public static final Block PREHISTORIC_MANGROVE_STAIRS;
	public static final BlockState PREHISTORIC_MANGROVE_STAIRS_STATE;

	public static final Block PREHISTORIC_DARKWOOD_STAIRS;
	public static final BlockState PREHISTORIC_DARKWOOD_STAIRS_STATE;

	public static final Block PREHISTORIC_LEPIDODENDRALES_SLAB;
	public static final BlockState PREHISTORIC_LEPIDODENDRALES_SLAB_STATE;

	public static final Block PREHISTORIC_MANGROVE_SLAB;
	public static final BlockState PREHISTORIC_MANGROVE_SLAB_STATE;

	public static final Block PREHISTORIC_DARKWOOD_SLAB;
	public static final BlockState PREHISTORIC_DARKWOOD_SLAB_STATE;

	public static final Block PREHISTORIC_CALAMITES_LOG;
	public static final BlockState PREHISTORIC_CALAMITES_LOG_STATE;

	public static final Block PREHISTORIC_CALAMITES_PLANKS;
	public static final BlockState PREHISTORIC_CALAMITES_PLANKS_STATE;

	public static final Block PREHISTORIC_CALAMITES_STAIRS;
	public static final BlockState PREHISTORIC_CALAMITES_STAIRS_STATE;

	public static final Block PREHISTORIC_CALAMITES_SLAB;
	public static final BlockState PREHISTORIC_CALAMITES_SLAB_STATE;

	public static final Block PREHISTORIC_CALAMITES_LEAVES;
	public static final BlockState PREHISTORIC_CALAMITES_LEAVES_STATE;

	public static final Block PREHISTORIC_CALAMITES_SAPLING;

	public static final Block PREHISTORIC_FARMLAND;
	public static final BlockState PREHISTORIC_FARMLAND_STATE;

	public static final Block PREHISTORIC_SPIKED_BARK;

	public static void init() {
	}

	private static Block register(String id, Block block) {
		return Registry.register(Registry.BLOCK, new Identifier(EymbraPrehistoric.MODID, id), block);
	}

	private static PillarBlock createLogBlock(MaterialColor topMaterialColor, MaterialColor sideMaterialColor) {
		return new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (blockState) -> {
			return blockState.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMaterialColor : sideMaterialColor;
		}).strength(2.0F).sounds(BlockSoundGroup.WOOD));
	}

	private static LeavesBlock createLeavesBlock() {
		return new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(EymbraBlocks::canSpawnOnLeaves).suffocates(EymbraBlocks::never).blockVision(EymbraBlocks::never));
	}

	/**
	 * A shortcut to always return {@code true} a context predicate, used as
	 * {@code settings.solidBlock(Blocks::always)}.
	 */
	private static boolean always(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}

	/**
	 * A shortcut to always return {@code false} a context predicate, used as
	 * {@code settings.solidBlock(Blocks::never)}.
	 */
	private static boolean never(BlockState state, BlockView world, BlockPos pos) {
		return false;
	}

	private static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return type == EntityType.OCELOT || type == EntityType.PARROT;
	}

	static {
		PREHISTORIC_TIME_BOX = register("prehistoric_time_box", new TimeBoxBlock(AbstractBlock.Settings.of(Material.METAL, MaterialColor.IRON).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL)));
		PREHISTORIC_TIME_BOX_STATE = PREHISTORIC_TIME_BOX.getDefaultState();

		PREHISTORIC_GRASS_BLOCK = register("prehistoric_grass_block", new ModifiableGrassBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS)));
		PREHISTORIC_GRASS_BLOCK_STATE = PREHISTORIC_GRASS_BLOCK.getDefaultState();

		PREHISTORIC_DIRT_BLOCK = register("prehistoric_dirt", new Block(AbstractBlock.Settings.of(Material.SOIL, MaterialColor.DIRT).strength(0.5F).sounds(BlockSoundGroup.GRAVEL)));
		PREHISTORIC_DIRT_BLOCK_STATE = PREHISTORIC_DIRT_BLOCK.getDefaultState();

		PREHISTORIC_LEPIDODENDRALES_LOG = register("prehistoric_lepidodendrales_log", createLogBlock(MaterialColor.WOOD, MaterialColor.SPRUCE));
		PREHISTORIC_LEPIDODENDRALES_LOG_STATE = PREHISTORIC_LEPIDODENDRALES_LOG.getDefaultState();

		PREHISTORIC_LEPIDODENDRALES_LEAVES = register("prehistoric_lepidodendrales_leaves", createLeavesBlock());
		PREHISTORIC_LEPIDODENDRALES_LEAVES_STATE = PREHISTORIC_LEPIDODENDRALES_LEAVES.getDefaultState();

		PREHISTORIC_LEPIDODENDRALES_SAPLING = register("prehistoric_lepidodendrales_sapling", new ModifiedSaplingBlock(new LepidodendralesSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));

		PREHISTORIC_MANGROVE_SAPLING = register("prehistoric_mangrove_sapling", new ModifiedSaplingBlock(new MangroveSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));

		PREHISTORIC_SHORT_BUSH = register("prehistoric_short_bush", new ModifiablePlantBlock(AbstractBlock.Settings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)));
		PREHISTORIC_SHORT_BUSH_STATE = PREHISTORIC_SHORT_BUSH.getDefaultState();

		PREHISTORIC_DEAD_BUSH = register("prehistoric_dead_bush", new ModifiablePlantBlock(AbstractBlock.Settings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)));
		PREHISTORIC_DEAD_BUSH_STATE = PREHISTORIC_DEAD_BUSH.getDefaultState();

		PREHISTORIC_MANGROVE_LOG = register("prehistoric_mangrove_log", createLogBlock(MaterialColor.WOOD, MaterialColor.SPRUCE));
		PREHISTORIC_MANGROVE_LOG_STATE = PREHISTORIC_MANGROVE_LOG.getDefaultState();

		PREHISTORIC_MANGROVE_LEAVES = register("prehistoric_mangrove_leaves", createLeavesBlock());
		PREHISTORIC_MANGROVE_LEAVES_STATE = PREHISTORIC_MANGROVE_LEAVES.getDefaultState();

		PREHISTORIC_PORTAL_BLOCK = register("prehistoric_portal", new PrehistoricPortalBlock(AbstractBlock.Settings.of(Material.PORTAL).noCollision().ticksRandomly().strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance((state) -> {
			return 11;
		})));
		PREHISTORIC_PORTAL_BLOCK_STATE = PREHISTORIC_PORTAL_BLOCK.getDefaultState();

		PREHISTORIC_RED_ROCK = register("prehistoric_red_rock", new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.RED).requiresTool().strength(1.5F, 6.0F)));
		PREHISTORIC_RED_ROCK_STATE = PREHISTORIC_RED_ROCK.getDefaultState();

		VOLCANIC_ROCK = register("prehistoric_volcanic_rock", new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.BLACK).requiresTool().strength(1.5F, 6.0F)));
		VOLCANIC_ROCK_STATE = VOLCANIC_ROCK.getDefaultState();

		PREHISTORIC_DARKWOOD_LOG = register("prehistoric_darkwood_log", createLogBlock(MaterialColor.WOOD, MaterialColor.SPRUCE));
		PREHISTORIC_DARKWOOD_LOG_STATE = PREHISTORIC_DARKWOOD_LOG.getDefaultState();

		PREHISTORIC_DARKWOOD_LEAVES = register("prehistoric_darkwood_leaves", createLeavesBlock());
		PREHISTORIC_DARKWOOD_LEAVES_STATE = PREHISTORIC_DARKWOOD_LEAVES.getDefaultState();

		PREHISTORIC_DARKWOOD_SAPLING = register("prehistoric_darkwood_sapling", new ModifiedSaplingBlock(new DarkwoodSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));

		PREHISTORIC_LEPIDODENDRALES_PLANKS = register("prehistoric_lepidodendrales_planks", new Block(AbstractBlock.Settings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
		PREHISTORIC_LEPIDODENDRALES_PLANKS_STATE = PREHISTORIC_LEPIDODENDRALES_PLANKS.getDefaultState();

		PREHISTORIC_MANGROVE_PLANKS = register("prehistoric_mangrove_planks", new Block(AbstractBlock.Settings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
		PREHISTORIC_MANGROVE_PLANKS_STATE = PREHISTORIC_MANGROVE_PLANKS.getDefaultState();

		PREHISTORIC_DARKWOOD_PLANKS = register("prehistoric_darkwood_planks", new Block(AbstractBlock.Settings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
		PREHISTORIC_DARKWOOD_PLANKS_STATE = PREHISTORIC_DARKWOOD_PLANKS.getDefaultState();

		PREHISTORIC_LEPIDODENDRALES_STAIRS = register("prehistoric_lepidodendrales_stairs", new ModifiableStairsBlock(PREHISTORIC_LEPIDODENDRALES_PLANKS_STATE, AbstractBlock.Settings.copy(PREHISTORIC_LEPIDODENDRALES_PLANKS)));
		PREHISTORIC_LEPIDODENDRALES_STAIRS_STATE = PREHISTORIC_LEPIDODENDRALES_STAIRS.getDefaultState();

		PREHISTORIC_MANGROVE_STAIRS = register("prehistoric_mangrove_stairs", new ModifiableStairsBlock(PREHISTORIC_MANGROVE_PLANKS_STATE, AbstractBlock.Settings.copy(PREHISTORIC_MANGROVE_PLANKS)));
		PREHISTORIC_MANGROVE_STAIRS_STATE = PREHISTORIC_MANGROVE_STAIRS.getDefaultState();

		PREHISTORIC_DARKWOOD_STAIRS = register("prehistoric_darkwood_stairs", new ModifiableStairsBlock(PREHISTORIC_DARKWOOD_PLANKS_STATE, AbstractBlock.Settings.copy(PREHISTORIC_DARKWOOD_PLANKS)));
		PREHISTORIC_DARKWOOD_STAIRS_STATE = PREHISTORIC_DARKWOOD_STAIRS.getDefaultState();

		PREHISTORIC_LEPIDODENDRALES_SLAB = register("prehistoric_lepidodendrales_slab", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
		PREHISTORIC_LEPIDODENDRALES_SLAB_STATE = PREHISTORIC_LEPIDODENDRALES_SLAB.getDefaultState();

		PREHISTORIC_MANGROVE_SLAB = register("prehistoric_mangrove_slab", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
		PREHISTORIC_MANGROVE_SLAB_STATE = PREHISTORIC_MANGROVE_SLAB.getDefaultState();

		PREHISTORIC_DARKWOOD_SLAB = register("prehistoric_darkwood_slab", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
		PREHISTORIC_DARKWOOD_SLAB_STATE = PREHISTORIC_DARKWOOD_SLAB.getDefaultState();

		PREHISTORIC_CALAMITES_LOG = register("prehistoric_calamites_log", createLogBlock(MaterialColor.WOOD, MaterialColor.SPRUCE));
		PREHISTORIC_CALAMITES_LOG_STATE = PREHISTORIC_CALAMITES_LOG.getDefaultState();

		PREHISTORIC_CALAMITES_PLANKS = register("prehistoric_calamites_planks", new Block(AbstractBlock.Settings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
		PREHISTORIC_CALAMITES_PLANKS_STATE = PREHISTORIC_CALAMITES_PLANKS.getDefaultState();

		PREHISTORIC_CALAMITES_STAIRS = register("prehistoric_calamites_stairs", new ModifiableStairsBlock(PREHISTORIC_CALAMITES_PLANKS_STATE, AbstractBlock.Settings.copy(PREHISTORIC_CALAMITES_PLANKS)));
		PREHISTORIC_CALAMITES_STAIRS_STATE = PREHISTORIC_CALAMITES_STAIRS.getDefaultState();

		PREHISTORIC_CALAMITES_SLAB = register("prehistoric_calamites_slab", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
		PREHISTORIC_CALAMITES_SLAB_STATE = PREHISTORIC_CALAMITES_SLAB.getDefaultState();

		PREHISTORIC_CALAMITES_LEAVES = register("prehistoric_calamites_leaves", createLeavesBlock());
		PREHISTORIC_CALAMITES_LEAVES_STATE = PREHISTORIC_CALAMITES_LEAVES.getDefaultState();

		PREHISTORIC_CALAMITES_SAPLING = register("prehistoric_calamites_sapling", new ModifiedSaplingBlock(new CalamitesSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));

		PREHISTORIC_FARMLAND = register("prehistoric_farmland", new ModifiableFarmlandBlock(AbstractBlock.Settings.of(Material.SOIL).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRAVEL).blockVision(EymbraBlocks::always).suffocates(EymbraBlocks::always)));
		PREHISTORIC_FARMLAND_STATE = PREHISTORIC_FARMLAND.getDefaultState();

		PREHISTORIC_SPIKED_BARK = register("prehistoric_spiked_bark", new SpikedBarkBlock(AbstractBlock.Settings.of(Material.PLANT, MaterialColor.BROWN).strength(0.4F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
	}
}
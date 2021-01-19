package net.eymbra.blocks;

import org.jetbrains.annotations.Nullable;
import net.eymbra.sounds.EymbraSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TimeBoxBlock extends Block {
	public static final BooleanProperty LIT;
	private boolean cycle = false;

	public TimeBoxBlock(Settings settings) {
		super(settings);
		this.setDefaultState((BlockState) this.getDefaultState().with(LIT, false));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		return trigger(state, world, pos);
	}

	public ActionResult trigger(BlockState state, World world, BlockPos pos) {
		boolean del = false;
		SoundManager snd = MinecraftClient.getInstance().getSoundManager();
		
		del = !world.getBlockState(pos).get(LIT);
		
		if (!del && !snd.isPlaying((SoundInstance) EymbraSoundEvents.TM_OPEN) && !cycle) {
			snd.play(EymbraSoundEvents.TM_CLOSE);
		} else if (!snd.isPlaying((SoundInstance) EymbraSoundEvents.TM_CLOSE) && !cycle) {
			snd.play(EymbraSoundEvents.TM_OPEN);
		}
		
		if (cycle && (!snd.isPlaying((SoundInstance) EymbraSoundEvents.TM_OPEN) && !snd.isPlaying((SoundInstance) EymbraSoundEvents.TM_CLOSE))) {
			cycle = false;
		}
		
		if (world.isClient || cycle) {
			return ActionResult.SUCCESS;
		} else {
			world.setBlockState(pos, (BlockState)state.cycle(LIT), 2);
			del = !world.getBlockState(pos).get(LIT);
			
			world.setBlockState(pos.north(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_SLAB.getDefaultState());
			world.setBlockState(pos.south(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_SLAB.getDefaultState());
			world.setBlockState(pos.east(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_SLAB.getDefaultState());
			world.setBlockState(pos.west(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_SLAB.getDefaultState());
			
			world.setBlockState(pos.north().east(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.north().west(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.south().east(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.south().west(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			
			world.setBlockState(pos.east().east(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.north().east().east(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.south().east().east(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			
			world.setBlockState(pos.west().west(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.north().west().west(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.south().west().west(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			
			world.setBlockState(pos.north().north(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.east().north().north(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.west().north().north(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			
			world.setBlockState(pos.south().south(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.east().south().south(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.west().south().south(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			
			world.setBlockState(pos.east().east().north().north(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.west().west().north().north(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			
			world.setBlockState(pos.east().east().south().south(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			world.setBlockState(pos.west().west().south().south(), del ? Blocks.AIR.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState());
			
			world.setBlockState(pos.east().east().north().north().up(), del ? Blocks.AIR.getDefaultState() : Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
			world.setBlockState(pos.west().west().north().north().up(), del ? Blocks.AIR.getDefaultState() : Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
			
			world.setBlockState(pos.east().east().south().south().up(), del ? Blocks.AIR.getDefaultState() : Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
			world.setBlockState(pos.west().west().south().south().up(), del ? Blocks.AIR.getDefaultState() : Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
			
			world.setBlockState(pos.east().east().north().north().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
			world.setBlockState(pos.west().west().north().north().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
			
			world.setBlockState(pos.east().east().south().south().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
			world.setBlockState(pos.west().west().south().south().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
			
			world.setBlockState(pos.up(), del ? Blocks.AIR.getDefaultState() : EymbraBlocks.PREHISTORIC_PORTAL_BLOCK_STATE);
			world.setBlockState(pos.up().up(), del ? Blocks.AIR.getDefaultState() : EymbraBlocks.PREHISTORIC_PORTAL_BLOCK_STATE);
			world.setBlockState(pos.up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_PLANKS.getDefaultState());
			
			world.setBlockState(pos.north().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.south().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.east().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.west().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			
			world.setBlockState(pos.north().east().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.north().west().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.south().east().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.south().west().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			
			world.setBlockState(pos.east().east().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.north().east().east().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.south().east().east().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			
			world.setBlockState(pos.west().west().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.north().west().west().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.south().west().west().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			
			world.setBlockState(pos.north().north().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.east().north().north().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.west().north().north().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			
			world.setBlockState(pos.south().south().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.east().south().south().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.west().south().south().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			
			world.setBlockState(pos.east().east().north().north().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.west().west().north().north().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			
			world.setBlockState(pos.east().east().south().south().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			world.setBlockState(pos.west().west().south().south().up().up().up(), del ? Blocks.AIR.getDefaultState() : Blocks.SPRUCE_SLAB.getDefaultState());
			
			cycle = true;
			
			return ActionResult.CONSUME;
		}
	}

	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return (BlockState) this.getDefaultState().with(LIT, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}

	static {
		LIT = Properties.LIT;
	}
}
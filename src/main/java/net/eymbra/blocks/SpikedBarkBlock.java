package net.eymbra.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class SpikedBarkBlock extends ConnectingBlock {
	protected SpikedBarkBlock(AbstractBlock.Settings settings) {
		super(0.3125F, settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.withConnectionProperties(ctx.getWorld(), ctx.getBlockPos());
	}

	public BlockState withConnectionProperties(BlockView world, BlockPos pos) {
		Block block = world.getBlockState(pos.down()).getBlock();
		Block block2 = world.getBlockState(pos.up()).getBlock();
		Block block3 = world.getBlockState(pos.north()).getBlock();
		Block block4 = world.getBlockState(pos.east()).getBlock();
		Block block5 = world.getBlockState(pos.south()).getBlock();
		Block block6 = world.getBlockState(pos.west()).getBlock();
		return this.getDefaultState().with(DOWN, block == this || block != Blocks.AIR).with(UP, block2 == this || block2 != Blocks.AIR).with(NORTH, block3 == this || block3 != Blocks.AIR).with(EAST, block4 == this || block4 != Blocks.AIR).with(SOUTH, block5 == this || block5 != Blocks.AIR).with(WEST, block6 == this || block6 != Blocks.AIR);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
		super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
		boolean bl = !newState.isOf(Blocks.AIR);
		return state.with((Property) FACING_PROPERTIES.get(direction), bl);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}
}

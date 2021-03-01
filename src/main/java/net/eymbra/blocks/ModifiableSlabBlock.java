package net.eymbra.blocks;

import org.jetbrains.annotations.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class ModifiableSlabBlock extends Block implements Waterloggable {
	public static final EnumProperty<SlabType> TYPE;
	public static final BooleanProperty WATERLOGGED;
	protected static final VoxelShape BOTTOM_SHAPE;
	protected static final VoxelShape TOP_SHAPE;

	public ModifiableSlabBlock(AbstractBlock.Settings settings) {
		super(settings.strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
		this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, false));
	}

	@Override
	public boolean hasSidedTransparency(BlockState state) {
		return state.get(TYPE) != SlabType.DOUBLE;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(TYPE, WATERLOGGED);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		SlabType slabType = state.get(TYPE);
		switch (slabType) {
		case DOUBLE:
			return VoxelShapes.fullCube();
		case TOP:
			return TOP_SHAPE;
		default:
			return BOTTOM_SHAPE;
		}
	}

	@Override
	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockPos blockPos = ctx.getBlockPos();
		BlockState blockState = ctx.getWorld().getBlockState(blockPos);
		if (blockState.isOf(this)) {
			return blockState.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, false);
		} else {
			FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
			BlockState blockState2 = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
			Direction direction = ctx.getSide();
			return direction != Direction.DOWN && (direction == Direction.UP || ctx.getHitPos().y - blockPos.getY() <= 0.5D) ? blockState2 : (BlockState) blockState2.with(TYPE, SlabType.TOP);
		}
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		ItemStack itemStack = context.getStack();
		SlabType slabType = state.get(TYPE);
		if (slabType != SlabType.DOUBLE && itemStack.getItem() == this.asItem()) {
			if (context.canReplaceExisting()) {
				boolean bl = context.getHitPos().y - context.getBlockPos().getY() > 0.5D;
				Direction direction = context.getSide();
				if (slabType == SlabType.BOTTOM) {
					return direction == Direction.UP || bl && direction.getAxis().isHorizontal();
				} else {
					return direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
		return state.get(TYPE) != SlabType.DOUBLE ? Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState) : false;
	}

	@Override
	public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
		return state.get(TYPE) != SlabType.DOUBLE ? Waterloggable.super.canFillWithFluid(world, pos, state, fluid) : false;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
		if (state.get(WATERLOGGED)) {
			world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		switch (type) {
		case LAND:
			return false;
		case WATER:
			return world.getFluidState(pos).isIn(FluidTags.WATER);
		case AIR:
			return false;
		default:
			return false;
		}
	}

	static {
		TYPE = Properties.SLAB_TYPE;
		WATERLOGGED = Properties.WATERLOGGED;
		BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
		TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	}
}

package net.eymbra.prehistoric.mixin;

import java.util.Map;
import net.eymbra.items.IEymbraTiltedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HoeItem.class)
public class HoeItemMixin implements IEymbraTiltedBlocks {
	@Shadow
	@Final
	private static Map<Block, BlockState> TILLED_BLOCKS;

	@Override
	public void addTiltedBlock(Block block, BlockState blockState) {
		TILLED_BLOCKS.put(block, blockState);
	}
}
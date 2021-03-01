package net.eymbra.blocks;

import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

public class ModifiablePlankBlock extends Block {
	public ModifiablePlankBlock(Settings settings) {
		super(settings.strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
	}
}
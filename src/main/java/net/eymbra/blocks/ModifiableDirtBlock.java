package net.eymbra.blocks;

import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

public class ModifiableDirtBlock extends Block {
	public ModifiableDirtBlock(Settings settings) {
		super(settings.strength(0.5F).sounds(BlockSoundGroup.GRAVEL));
	}
}
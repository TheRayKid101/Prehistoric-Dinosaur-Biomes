package net.eymbra.gui.screen;

import net.eymbra.gui.inventory.PrehistoricReferenceBookInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class PrehistoricReferenceBookScreenHandler extends ScreenHandler {
	private final PrehistoricReferenceBookInventory input;

	public PrehistoricReferenceBookScreenHandler(int syncId, PlayerInventory playerInventory) {
		super(EymbraScreenHanderType.PREHISTORIC_REFERENCE, syncId);
		
		this.input = new PrehistoricReferenceBookInventory(this, 9, 9);
		
		int xOffset = 92;
		int yOffset = 31;
		
		int var2 = 0;
		for (int var3 = 0; var3 < 3; var3++) {
			for (int var4 = 0; var4 < 3; var4++) {
				this.addSlot(new Slot(this.input, var2++, 99 + var4 * 117 - xOffset, 24 + (var3 + 1) * 55 - yOffset));
				for (int var5 = 0; var5 < 3; var5++) {
					for (int var6 = 0; var6 < 3; var6++)
						this.addSlot(new Slot(this.input, var2++, 5 + var6 * 18 + var4 * 117 - xOffset, 6 + var5 * 18 + (var3 + 1) * 55 - yOffset));
				}
			}
		}
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
package net.eymbra.gui.inventory;

import java.util.Iterator;

import net.eymbra.items.EymbraItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;

public class PrehistoricReferenceBookInventory implements Inventory {
	private final DefaultedList<ItemStack> stacks;
	@SuppressWarnings("unused")
	private final ScreenHandler handler;

	public PrehistoricReferenceBookInventory(ScreenHandler handler, int width, int height) {
		this.handler = handler;

		this.stacks = DefaultedList.ofSize(width * height, ItemStack.EMPTY);

		this.stacks.set(0, new ItemStack(EymbraItems.PREHISTORIC_TIME_BOX));
		
		this.stacks.set(1, new ItemStack(Items.IRON_INGOT));
		this.stacks.set(2, new ItemStack(Items.DIAMOND));
		this.stacks.set(3, new ItemStack(Items.IRON_INGOT));
		this.stacks.set(4, new ItemStack(Items.REDSTONE));
		this.stacks.set(5, new ItemStack(Items.LEVER));
		this.stacks.set(6, new ItemStack(Items.REDSTONE));
		this.stacks.set(7, new ItemStack(Items.IRON_INGOT));
		this.stacks.set(8, new ItemStack(Items.REDSTONE));
		this.stacks.set(9, new ItemStack(Items.IRON_INGOT));
		
//		this.stacks.set(0, new ItemStack(Items.STICK, 4));
//		this.stacks.set(1, new ItemStack(EymbraItems.PREHISTORIC_LEPIDODENDRALES_PLANKS));
//		this.stacks.set(4, new ItemStack(EymbraItems.PREHISTORIC_LEPIDODENDRALES_PLANKS));
//
//		this.stacks.set(11, new ItemStack(EymbraItems.PREHISTORIC_LEPIDODENDRALES_PLANKS));
//		this.stacks.set(12, new ItemStack(EymbraItems.PREHISTORIC_LEPIDODENDRALES_PLANKS));
//		this.stacks.set(10, new ItemStack(Items.OAK_PRESSURE_PLATE));
	}

	@Override
	public void clear() {

	}

	@Override
	public int size() {
		return this.stacks.size();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isEmpty() {
		Iterator var1 = this.stacks.iterator();

		ItemStack itemStack;
		do {
			if (!var1.hasNext()) {
				return true;
			}

			itemStack = (ItemStack) var1.next();
		} while (itemStack.isEmpty());

		return false;
	}

	@Override
	public ItemStack getStack(int slot) {
		return slot >= this.size() ? ItemStack.EMPTY : (ItemStack) this.stacks.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStack(int slot) {
		return ItemStack.EMPTY;
	}

	public void setStack(int slot, ItemStack stack) {
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return false;
	}
}
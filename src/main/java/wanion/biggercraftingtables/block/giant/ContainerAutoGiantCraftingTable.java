package wanion.biggercraftingtables.block.giant;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.ContainerAutoBiggerCraftingTable;
import wanion.biggercraftingtables.inventory.DeadSlot;
import wanion.biggercraftingtables.inventory.ShapeSlot;
import wanion.biggercraftingtables.inventory.SpecialSlot;

import javax.annotation.Nonnull;

public final class ContainerAutoGiantCraftingTable extends ContainerAutoBiggerCraftingTable
{
	public ContainerAutoGiantCraftingTable(@Nonnull TileEntityAutoGiantCraftingTable tileEntityAutoGiantCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityAutoGiantCraftingTable);
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(tileEntityAutoGiantCraftingTable, y * 9 + x, 8 + (18 * x), 18 + (18 * y)));
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new ShapeSlot(tileEntityAutoGiantCraftingTable, 81 + (y * 9 + x), 175 + (18 * x), 18 + (18 * y)));
		addSlotToContainer(new DeadSlot(tileEntityAutoGiantCraftingTable, 163, 346, 90));
		addSlotToContainer(new SpecialSlot(tileEntityAutoGiantCraftingTable, 162, 346, 118));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 107 + (18 * x), 194 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 107 + (18 * i), 252));
	}

	@Override
	public final ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int slot)
	{
		ItemStack itemstack = null;
		final Slot actualSlot = (Slot) this.inventorySlots.get(slot);
		if (actualSlot != null && actualSlot.getHasStack()) {
			ItemStack itemstack1 = actualSlot.getStack();
			itemstack = itemstack1.copy();
			if (slot > 163) {
				if (!mergeItemStack(itemstack1, 0, 81, false))
					return null;
			} else if (slot < 81 || slot == 163) {
				if (!mergeItemStack(itemstack1, 164, 200, true))
					return null;
			}
			if (itemstack1.stackSize == 0)
				actualSlot.putStack(null);
			actualSlot.onSlotChanged();
		}
		return itemstack;
	}

	@Override
	public ItemStack slotClick(final int slot, final int mouseButton, final int modifier, final EntityPlayer entityPlayer)
	{
		if (slot > 80 && slot < 162) {
			if (modifier == 2)
				return null;
			final ItemStack playerStack = entityPlayer.inventory.getItemStack();
			final Slot actualSlot = (Slot) inventorySlots.get(slot);
			final boolean slotHasStack = actualSlot.getHasStack();
			if (slotHasStack && playerStack == null) {
				actualSlot.putStack(null);
				return null;
			} else if (playerStack != null) {
				if (modifier == 1) {
					actualSlot.putStack(null);
					return null;
				} else if (slotHasStack && playerStack.isItemEqual(actualSlot.getStack())) {
					actualSlot.putStack(null);
					return null;
				}
				final ItemStack slotStack = playerStack.copy();
				slotStack.stackSize = 0;
				actualSlot.putStack(slotStack);
				return slotStack;
			}
			return null;
		} else return super.slotClick(slot, mouseButton, modifier, entityPlayer);
	}
}
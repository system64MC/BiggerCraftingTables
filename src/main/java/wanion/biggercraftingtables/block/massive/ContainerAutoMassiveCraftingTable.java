package wanion.biggercraftingtables.block.massive;

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

public final class ContainerAutoMassiveCraftingTable extends ContainerAutoBiggerCraftingTable
{
	public ContainerAutoMassiveCraftingTable(@Nonnull TileEntityAutoMassiveCraftingTable tileEntityAutoMassiveCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityAutoMassiveCraftingTable);
		for (int y = 0; y < 11; y++)
			for (int x = 0; x < 11; x++)
				addSlotToContainer(new Slot(tileEntityAutoMassiveCraftingTable, y * 11 + x, 8 + (18 * x), 18 + (18 * y)));
		for (int y = 0; y < 11; y++)
			for (int x = 0; x < 11; x++)
				addSlotToContainer(new ShapeSlot(tileEntityAutoMassiveCraftingTable, 121 + (y * 11 + x), 211 + (18 * x), 18 + (18 * y)));
		addSlotToContainer(new DeadSlot(tileEntityAutoMassiveCraftingTable, 243, 418, 108));
		addSlotToContainer(new SpecialSlot(tileEntityAutoMassiveCraftingTable, 242, 418, 136));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 143 + (18 * x), 230 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 143 + (18 * i), 288));
	}

	@Override
	public final ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int slot)
	{
		ItemStack itemstack = null;
		final Slot actualSlot = (Slot) this.inventorySlots.get(slot);
		if (actualSlot != null && actualSlot.getHasStack()) {
			ItemStack itemstack1 = actualSlot.getStack();
			itemstack = itemstack1.copy();
			if (slot > 243) {
				if (!mergeItemStack(itemstack1, 0, 121, false))
					return null;
			} else if (slot < 121 || slot == 242) {
				if (!mergeItemStack(itemstack1, 244, 280, true))
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
		if (slot > 120 && slot < 242) {
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
package wanion.biggercraftingtables.block.giant;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public final class CraftingGiantCraftingTable extends InventoryCrafting
{
	private final TileEntityGiantCraftingTable tileEntityGiantCraftingTable;
	private final Container container;

	public CraftingGiantCraftingTable(@Nonnull final Container container, @Nonnull final TileEntityGiantCraftingTable tileEntityBigCraftingTable)
	{
		super(container, 9, 9);
		this.tileEntityGiantCraftingTable = tileEntityBigCraftingTable;
		this.container = container;
	}

	@Override
	public ItemStack getStackInSlot(final int slot)
	{
		return slot >= getSizeInventory() ? null : tileEntityGiantCraftingTable.getStackInSlot(slot);
	}

	@Override
	public ItemStack getStackInRowAndColumn(final int row, final int column)
	{
		final int slot = row * 9 + column;
		return slot < 81 ? getStackInSlot(slot) : null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int par1)
	{
		return null;
	}

	@Override
	public ItemStack decrStackSize(final int slot, final int decrement)
	{
		final ItemStack stack = tileEntityGiantCraftingTable.getStackInSlot(slot);
		if (stack != null) {
			ItemStack itemstack;
			if (stack.stackSize <= decrement) {
				itemstack = stack.copy();
				tileEntityGiantCraftingTable.setInventorySlotContents(slot, null);
				container.onCraftMatrixChanged(this);
				return itemstack;
			} else {
				itemstack = stack.splitStack(decrement);
				container.onCraftMatrixChanged(this);
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack itemstack)
	{
		tileEntityGiantCraftingTable.setInventorySlotContents(slot, itemstack);
		container.onCraftMatrixChanged(this);
	}
}
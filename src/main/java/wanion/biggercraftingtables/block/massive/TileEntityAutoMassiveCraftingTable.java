package wanion.biggercraftingtables.block.massive;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.biggercraftingtables.block.TileEntityAutoBiggerCraftingTable;
import wanion.biggercraftingtables.recipe.massive.MassiveRecipeRegistry;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.recipe.massive.MassiveRecipeRegistry.IMassiveRecipe;

public final class TileEntityAutoMassiveCraftingTable extends TileEntityAutoBiggerCraftingTable<IMassiveRecipe>
{
	@Override
	public int getSizeInventory()
	{
		return 244;
	}

	@Override
	public String getInventoryName()
	{
		return "container.AutoMassiveCraftingTable";
	}

	@Nonnull
	@Override
	public AbstractRecipeRegistry<IMassiveRecipe> getRecipeRegistry()
	{
		return MassiveRecipeRegistry.instance;
	}
}
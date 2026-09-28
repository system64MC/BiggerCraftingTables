package wanion.biggercraftingtables.minetweaker;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wanion.biggercraftingtables.recipe.massive.MassiveRecipeRegistry;
import wanion.biggercraftingtables.recipe.massive.ShapedMassiveRecipe;
import wanion.biggercraftingtables.recipe.massive.ShapelessMassiveRecipe;
import wanion.lib.common.MineTweakerHelper;
import wanion.lib.recipe.RecipeHelper;

import javax.annotation.Nonnull;
import java.util.List;

import static wanion.biggercraftingtables.recipe.massive.MassiveRecipeRegistry.IMassiveRecipe;

@ZenClass("mods.biggercraftingtables.Massive")
public final class MassiveCrafting
{
	private MassiveCrafting() {}

	@ZenMethod
	public static void addShaped(@Nonnull final IItemStack output, @Nonnull final IIngredient[][] inputs)
	{
		int height = inputs.length;
		int width = 0;
		for (final IIngredient[] row : inputs)
			if (width < row.length)
				width = row.length;
		final Object[][] input = new Object[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				input[y][x] = MineTweakerHelper.toActualObject(inputs[y][x]);
		MineTweakerAPI.apply(new Add(new ShapedMassiveRecipe(MineTweakerHelper.toStack(output), RecipeHelper.rawShapeToShape(input, 11))));
	}

	@ZenMethod
	public static void addShapeless(@Nonnull final IItemStack output, @Nonnull final IIngredient[] inputs)
	{
		MineTweakerAPI.apply(new Add(new ShapelessMassiveRecipe(MineTweakerHelper.toStack(output), MineTweakerHelper.toObjects(inputs))));
	}

	@ZenMethod
	public static void remove(final IItemStack target)
	{
		MineTweakerAPI.apply(new Remove(MineTweakerHelper.toStack(target)));
	}

	private static class Add implements IUndoableAction
	{
		private final IMassiveRecipe recipe;

		public Add(@Nonnull final IMassiveRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			MassiveRecipeRegistry.instance.addRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			recipe.setRemoved(true);
			MassiveRecipeRegistry.instance.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding MassiveRecipe for " + recipe.getOutput().getDisplayName();
		}

		@Override
		public String describeUndo()
		{
			return "Un-adding MassiveRecipe Recipe for " + recipe.getOutput().getDisplayName();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	private static class Remove implements IUndoableAction
	{
		private final ItemStack itemStackToRemove;
		private final IMassiveRecipe recipe;

		private Remove(@Nonnull final ItemStack itemStackToRemove)
		{
			this.itemStackToRemove = itemStackToRemove;
			IMassiveRecipe recipe = null;
			for (final List<IMassiveRecipe> massiveRecipeList : MassiveRecipeRegistry.instance.shapedRecipes.valueCollection()) {
				if (massiveRecipeList == null)
					continue;
				for (final IMassiveRecipe massiveRecipe : massiveRecipeList) {
					if (massiveRecipe.getOutput().isItemEqual(itemStackToRemove)) {
						MassiveRecipeRegistry.instance.removeRecipe(recipe = massiveRecipe);
						break;
					}
				}
			}
			if (recipe == null) {
				for (final List<IMassiveRecipe> massiveRecipeList : MassiveRecipeRegistry.instance.shapelessRecipes.valueCollection()) {
					if (massiveRecipeList == null)
						continue;
					for (final IMassiveRecipe massiveRecipe : massiveRecipeList) {
						if (massiveRecipe.getOutput().isItemEqual(itemStackToRemove)) {
							MassiveRecipeRegistry.instance.removeRecipe(recipe = massiveRecipe);
							break;
						}
					}
				}
			}
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			if (recipe != null)
				recipe.setRemoved(true);
			MassiveRecipeRegistry.instance.removeRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return recipe != null;
		}

		@Override
		public void undo()
		{
			recipe.setRemoved(false);
			MassiveRecipeRegistry.instance.addRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Removing MassiveRecipe for " + itemStackToRemove.getDisplayName();
		}

		@Override
		public String describeUndo()
		{
			return "Un-removing MassiveRecipe for " + itemStackToRemove.getDisplayName();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}
}
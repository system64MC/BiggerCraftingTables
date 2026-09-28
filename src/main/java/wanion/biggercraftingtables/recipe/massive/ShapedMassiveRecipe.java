package wanion.biggercraftingtables.recipe.massive;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.recipe.AbstractShapedAdvancedRecipe;
import wanion.biggercraftingtables.recipe.massive.MassiveRecipeRegistry;

import javax.annotation.Nonnull;

public final class ShapedMassiveRecipe extends AbstractShapedAdvancedRecipe implements MassiveRecipeRegistry.IMassiveRecipe
{
	public ShapedMassiveRecipe(@Nonnull final ItemStack output, @Nonnull final Object... inputs)
	{
		super(11, output, inputs);
	}
}
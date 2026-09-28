package wanion.biggercraftingtables.nei;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import wanion.biggercraftingtables.block.massive.GuiMassiveCraftingTable;
import wanion.biggercraftingtables.recipe.massive.MassiveRecipeRegistry;
import wanion.biggercraftingtables.recipe.massive.ShapelessMassiveRecipe;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static wanion.biggercraftingtables.recipe.massive.MassiveRecipeRegistry.IMassiveRecipe;

public final class MassiveShapelessRecipeHandler extends ShapelessRecipeHandler
{
	@Override
	public int recipiesPerPage()
	{
		return 1;
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiMassiveCraftingTable.class;
	}

	@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("crafting.massive.shapeless");
	}

	@Override
	public void loadCraftingRecipes(@Nonnull final String outputId, Object... results)
	{
		if (outputId.equals("massive") && getClass() == MassiveShapelessRecipeHandler.class)
			MassiveRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iMassiveRecipes -> iMassiveRecipes.forEach(iMassiveRecipe -> {
				final CachedShapelessMassiveRecipe cachedShapelessMassiveRecipe = new CachedShapelessMassiveRecipe((ShapelessMassiveRecipe) iMassiveRecipe);
				cachedShapelessMassiveRecipe.computeVisuals();
				arecipes.add(cachedShapelessMassiveRecipe);
			}));
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(final ItemStack result)
	{
		final List<IMassiveRecipe> matchingRecipes = new ArrayList<>();
		MassiveRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iMassiveRecipes -> iMassiveRecipes.stream().filter(iMassiveRecipe -> NEIServerUtils.areStacksSameTypeCrafting(iMassiveRecipe.getOutput(), result)).forEach(matchingRecipes::add));
		matchingRecipes.forEach(iMassiveRecipe -> {
			final CachedShapelessMassiveRecipe cachedShapelessMassiveRecipe = new CachedShapelessMassiveRecipe((ShapelessMassiveRecipe) iMassiveRecipe);
			cachedShapelessMassiveRecipe.computeVisuals();
			arecipes.add(cachedShapelessMassiveRecipe);
		});
	}

	@Override
	public void loadUsageRecipes(final ItemStack ingredient)
	{
		MassiveRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iMassiveRecipes -> iMassiveRecipes.forEach(iMassiveRecipe -> {
			final CachedShapelessMassiveRecipe cachedShapelessMassiveRecipe = new CachedShapelessMassiveRecipe((ShapelessMassiveRecipe) iMassiveRecipe);
			cachedShapelessMassiveRecipe.computeVisuals();
			if (cachedShapelessMassiveRecipe.contains(cachedShapelessMassiveRecipe.inputs, ingredient)) {
				cachedShapelessMassiveRecipe.setIngredientPermutation(cachedShapelessMassiveRecipe.inputs, ingredient);
				arecipes.add(cachedShapelessMassiveRecipe);
			}
		}));
	}

	@Override
	public void loadTransferRects()
	{
		transferRects.add(new RecipeTransferRect(new Rectangle(219, 108, 3, 6), "massive"));
	}

	@Override
	public String getOverlayIdentifier()
	{
		return "massive";
	}

	@Override
	public boolean hasOverlay(final GuiContainer guiContainer, final net.minecraft.inventory.Container container, final int recipe)
	{
		return RecipeInfo.hasDefaultOverlay(guiContainer, "massive");
	}

	@Override
	public String getGuiTexture()
	{
		return "biggercraftingtables:textures/gui/massiveRecipe.png";
	}

	@Override
	public void drawBackground(final int recipe)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(2, 0, 0, 0, 198, 162);
	}

	private class CachedShapelessMassiveRecipe extends CachedRecipe
	{
		private final List<PositionedStack> inputs = new ArrayList<>();
		private final PositionedStack output;

		private CachedShapelessMassiveRecipe(@Nonnull final ShapelessMassiveRecipe shapelessMassiveRecipe)
		{
			this.output = new PositionedStack(shapelessMassiveRecipe.getOutput(), 177 + 2, 73);
			for (int i = 0; i < shapelessMassiveRecipe.inputs.size(); i++) {
				final PositionedStack stack = new PositionedStack(shapelessMassiveRecipe.inputs.get(i), 3 + (i % 9) * 18, 1 + (i / 9) * 18);
				stack.setMaxSize(1);
				inputs.add(stack);
			}
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			return getCycledIngredients(cycleticks / 20, inputs);
		}

		@Override
		public PositionedStack getResult()
		{
			return output;
		}

		private void computeVisuals()
		{
			inputs.forEach(PositionedStack::generatePermutations);
		}
	}
}
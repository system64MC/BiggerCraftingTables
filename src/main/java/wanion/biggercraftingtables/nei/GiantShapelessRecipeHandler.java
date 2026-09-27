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
import wanion.biggercraftingtables.block.giant.GuiGiantCraftingTable;
import wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry;
import wanion.biggercraftingtables.recipe.giant.ShapelessGiantRecipe;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry.IGiantRecipe;

public final class GiantShapelessRecipeHandler extends ShapelessRecipeHandler
{
	@Override
	public int recipiesPerPage()
	{
		return 1;
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiGiantCraftingTable.class;
	}

	@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("crafting.giant.shapeless");
	}

	@Override
	public void loadCraftingRecipes(@Nonnull final String outputId, Object... results)
	{
		if (outputId.equals("giant") && getClass() == GiantShapelessRecipeHandler.class)
			GiantRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iGiantRecipes -> iGiantRecipes.forEach(iGiantRecipe -> {
				final CachedShapelessGiantRecipe cachedShapelessGiantRecipe = new CachedShapelessGiantRecipe((ShapelessGiantRecipe) iGiantRecipe);
				cachedShapelessGiantRecipe.computeVisuals();
				arecipes.add(cachedShapelessGiantRecipe);
			}));
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(final ItemStack result)
	{
		final List<IGiantRecipe> matchingRecipes = new ArrayList<>();
		GiantRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iGiantRecipes -> iGiantRecipes.stream().filter(iGiantRecipe -> NEIServerUtils.areStacksSameTypeCrafting(iGiantRecipe.getOutput(), result)).forEach(matchingRecipes::add));
		matchingRecipes.forEach(iGiantRecipe -> {
			final CachedShapelessGiantRecipe cachedShapelessGiantRecipe = new CachedShapelessGiantRecipe((ShapelessGiantRecipe) iGiantRecipe);
			cachedShapelessGiantRecipe.computeVisuals();
			arecipes.add(cachedShapelessGiantRecipe);
		});
	}

	@Override
	public void loadUsageRecipes(final ItemStack ingredient)
	{
		GiantRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iGiantRecipes -> iGiantRecipes.forEach(iGiantRecipe -> {
			final CachedShapelessGiantRecipe cachedShapelessGiantRecipe = new CachedShapelessGiantRecipe((ShapelessGiantRecipe) iGiantRecipe);
			cachedShapelessGiantRecipe.computeVisuals();
			if (cachedShapelessGiantRecipe.contains(cachedShapelessGiantRecipe.inputs, ingredient)) {
				cachedShapelessGiantRecipe.setIngredientPermutation(cachedShapelessGiantRecipe.inputs, ingredient);
				arecipes.add(cachedShapelessGiantRecipe);
			}
		}));
	}

	@Override
	public void loadTransferRects()
	{
		transferRects.add(new RecipeTransferRect(new Rectangle(132, 64, 3, 6), "giant"));
	}

	@Override
	public String getOverlayIdentifier()
	{
		return "giant";
	}

	@Override
	public boolean hasOverlay(final GuiContainer guiContainer, final net.minecraft.inventory.Container container, final int recipe)
	{
		return RecipeInfo.hasDefaultOverlay(guiContainer, "giant");
	}

	@Override
	public String getGuiTexture()
	{
		return "biggercraftingtables:textures/gui/giantRecipe.png";
	}

	@Override
	public void drawBackground(final int recipe)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(2, 0, 0, 0, 198, 162);
	}

	private class CachedShapelessGiantRecipe extends CachedRecipe
	{
		private final List<PositionedStack> inputs = new ArrayList<>();
		private final PositionedStack output;

		private CachedShapelessGiantRecipe(@Nonnull final ShapelessGiantRecipe shapelessGiantRecipe)
		{
			this.output = new PositionedStack(shapelessGiantRecipe.getOutput(), 177 + 2, 73);
			for (int i = 0; i < shapelessGiantRecipe.inputs.size(); i++) {
				final PositionedStack stack = new PositionedStack(shapelessGiantRecipe.inputs.get(i), 3 + (i % 9) * 18, 1 + (i / 9) * 18);
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
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
import codechicken.nei.recipe.ShapedRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import wanion.biggercraftingtables.block.giant.GuiGiantCraftingTable;
import wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry;
import wanion.biggercraftingtables.recipe.giant.ShapedGiantRecipe;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry.IGiantRecipe;

public final class GiantShapedRecipeHandler extends ShapedRecipeHandler
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
		return StatCollector.translateToLocal("crafting.giant.shaped");
	}

	@Override
	public void loadCraftingRecipes(@Nonnull final String outputId, Object... results)
	{
		if (outputId.equals("giant") && getClass() == GiantShapedRecipeHandler.class)
			GiantRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iGiantRecipes -> iGiantRecipes.forEach(iGiantRecipe -> {
				final CachedShapedGiantRecipe cachedShapedGiantRecipe = new CachedShapedGiantRecipe((ShapedGiantRecipe) iGiantRecipe);
				cachedShapedGiantRecipe.computeVisuals();
				arecipes.add(cachedShapedGiantRecipe);
			}));
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(final ItemStack result)
	{
		final List<IGiantRecipe> matchingRecipes = new ArrayList<>();
		GiantRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iGiantRecipes -> iGiantRecipes.stream().filter(iGiantRecipe -> NEIServerUtils.areStacksSameTypeCrafting(iGiantRecipe.getOutput(), result)).forEach(matchingRecipes::add));
		matchingRecipes.forEach(iGiantRecipe -> {
			final CachedShapedGiantRecipe cachedShapedGiantRecipe = new CachedShapedGiantRecipe((ShapedGiantRecipe) iGiantRecipe);
			cachedShapedGiantRecipe.computeVisuals();
			arecipes.add(cachedShapedGiantRecipe);
		});
	}

	@Override
	public void loadUsageRecipes(final ItemStack ingredient)
	{
		GiantRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iGiantRecipes -> iGiantRecipes.forEach(iGiantRecipe -> {
			final CachedShapedGiantRecipe cachedShapedGiantRecipe = new CachedShapedGiantRecipe((ShapedGiantRecipe) iGiantRecipe);
			cachedShapedGiantRecipe.computeVisuals();
			if (cachedShapedGiantRecipe.contains(cachedShapedGiantRecipe.inputs, ingredient)) {
				cachedShapedGiantRecipe.setIngredientPermutation(cachedShapedGiantRecipe.inputs, ingredient);
				arecipes.add(cachedShapedGiantRecipe);
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
	public boolean hasOverlay(final GuiContainer guiContainer, final Container container, final int recipe)
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

	private class CachedShapedGiantRecipe extends CachedRecipe
	{
		private final List<PositionedStack> inputs = new ArrayList<>();
		private final PositionedStack output;

		private CachedShapedGiantRecipe(@Nonnull final ShapedGiantRecipe shapedGiantRecipe)
		{
			this.output = new PositionedStack(shapedGiantRecipe.getOutput(), 177 + 2, 73);
			for (int x = 0; x < shapedGiantRecipe.width; x++) {
				for (int y = 0; y < shapedGiantRecipe.height; y++) {
					final Object input = shapedGiantRecipe.inputs[y * shapedGiantRecipe.width + x];
					if (input == null)
						continue;
					final PositionedStack positionedStack = new PositionedStack(input, 3 + x * 18, 1 + y * 18);
					positionedStack.setMaxSize(1);
					this.inputs.add(positionedStack);
				}
			}
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			return getCycledIngredients(GiantShapedRecipeHandler.this.cycleticks / 20, inputs);
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
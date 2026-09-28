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
import wanion.biggercraftingtables.block.massive.GuiMassiveCraftingTable;
import wanion.biggercraftingtables.recipe.massive.MassiveRecipeRegistry;
import wanion.biggercraftingtables.recipe.massive.ShapedMassiveRecipe;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static wanion.biggercraftingtables.recipe.massive.MassiveRecipeRegistry.IMassiveRecipe;

public final class MassiveShapedRecipeHandler extends ShapedRecipeHandler
{

	// int getRecipeHeight();  // NEI may use this to space the sidebar
	// int getRecipeWidth();   // NEI may use this to reserve right-side space
	// Rectangle getIngredientArea();

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
		return StatCollector.translateToLocal("crafting.massive.shaped");
	}

	@Override
	public void loadCraftingRecipes(@Nonnull final String outputId, Object... results)
	{
		if (outputId.equals("massive") && getClass() == MassiveShapedRecipeHandler.class)
			MassiveRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iMassiveRecipes -> iMassiveRecipes.forEach(iMassiveRecipe -> {
				final CachedShapedMassiveRecipe cachedShapedMassiveRecipe = new CachedShapedMassiveRecipe((ShapedMassiveRecipe) iMassiveRecipe);
				cachedShapedMassiveRecipe.computeVisuals();
				arecipes.add(cachedShapedMassiveRecipe);
			}));
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(final ItemStack result)
	{
		final List<IMassiveRecipe> matchingRecipes = new ArrayList<>();
		MassiveRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iMassiveRecipes -> iMassiveRecipes.stream().filter(iMassiveRecipe -> NEIServerUtils.areStacksSameTypeCrafting(iMassiveRecipe.getOutput(), result)).forEach(matchingRecipes::add));
		matchingRecipes.forEach(iMassiveRecipe -> {
			final CachedShapedMassiveRecipe cachedShapedMassiveRecipe = new CachedShapedMassiveRecipe((ShapedMassiveRecipe) iMassiveRecipe);
			cachedShapedMassiveRecipe.computeVisuals();
			arecipes.add(cachedShapedMassiveRecipe);
		});
	}

	@Override
	public void loadUsageRecipes(final ItemStack ingredient)
	{
		MassiveRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iMassiveRecipes -> iMassiveRecipes.forEach(iMassiveRecipe -> {
			final CachedShapedMassiveRecipe cachedShapedMassiveRecipe = new CachedShapedMassiveRecipe((ShapedMassiveRecipe) iMassiveRecipe);
			cachedShapedMassiveRecipe.computeVisuals();
			if (cachedShapedMassiveRecipe.contains(cachedShapedMassiveRecipe.inputs, ingredient)) {
				cachedShapedMassiveRecipe.setIngredientPermutation(cachedShapedMassiveRecipe.inputs, ingredient);
				arecipes.add(cachedShapedMassiveRecipe);
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
	public boolean hasOverlay(final GuiContainer guiContainer, final Container container, final int recipe)
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
		GuiDraw.drawTexturedModalRect(2, 0, 0, 0, 234, 198);
	}

	private class CachedShapedMassiveRecipe extends CachedRecipe
	{
		private final List<PositionedStack> inputs = new ArrayList<>();
		private final PositionedStack output;

		private CachedShapedMassiveRecipe(@Nonnull final ShapedMassiveRecipe shapedMassiveRecipe)
		{
			this.output = new PositionedStack(shapedMassiveRecipe.getOutput(), 213 + 2, 91);
			for (int x = 0; x < shapedMassiveRecipe.width; x++) {
				for (int y = 0; y < shapedMassiveRecipe.height; y++) {
					final Object input = shapedMassiveRecipe.inputs[y * shapedMassiveRecipe.width + x];
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
			return getCycledIngredients(MassiveShapedRecipeHandler.this.cycleticks / 20, inputs);
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
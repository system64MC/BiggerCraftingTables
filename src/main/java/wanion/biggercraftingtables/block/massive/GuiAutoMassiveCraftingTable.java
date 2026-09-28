package wanion.biggercraftingtables.block.massive;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.Reference.MOD_ID;

@SideOnly(Side.CLIENT)
public final class GuiAutoMassiveCraftingTable extends GuiContainer
{
	private static final ResourceLocation autoMassiveCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/autoMassiveCraftingTable.png");
	private final TileEntityAutoMassiveCraftingTable tileEntityAutoMassiveCraftingTable;

	public GuiAutoMassiveCraftingTable(@Nonnull final TileEntityAutoMassiveCraftingTable tileEntityAutoMassiveCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerAutoMassiveCraftingTable(tileEntityAutoMassiveCraftingTable, inventoryPlayer));
		this.tileEntityAutoMassiveCraftingTable = tileEntityAutoMassiveCraftingTable;
		xSize = 446;
		ySize = 312;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(autoMassiveCraftingTexture);
		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(guiLeft, guiTop, 0, 0.0, 0.0);
		tessellator.addVertexWithUV(guiLeft, guiTop + xSize, 0, 0.0, 1.0);
		tessellator.addVertexWithUV(guiLeft + xSize, guiTop + xSize, 0, 1.0, 1.0);
		tessellator.addVertexWithUV(guiLeft + xSize, guiTop, 0, 1.0, 0.0);
		tessellator.draw();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int p_146979_1_, final int p_146979_2_)
	{
		fontRendererObj.drawString(I18n.format(tileEntityAutoMassiveCraftingTable.getInventoryName()), 7, 7, 0x404040);
		fontRendererObj.drawString(I18n.format("container.inventory"), 7, 219, 0x404040);
	}
}
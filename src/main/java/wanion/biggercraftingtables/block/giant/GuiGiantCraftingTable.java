package wanion.biggercraftingtables.block.giant;

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
public final class GuiGiantCraftingTable extends GuiContainer
{
	private static final ResourceLocation giantCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/giantCraftingTable.png");
	private final TileEntityGiantCraftingTable tileEntityGiantCraftingTable;

	public GuiGiantCraftingTable(@Nonnull final TileEntityGiantCraftingTable tileEntityGiantCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerGiantCraftingTable(tileEntityGiantCraftingTable, inventoryPlayer));
		this.tileEntityGiantCraftingTable = tileEntityGiantCraftingTable;
		xSize = 211;
		ySize = 276;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(
			float partialTicks,
			int mouseX,
			int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.getTextureManager().bindTexture(giantCraftingTexture);

		final Tessellator tessellator = Tessellator.instance;

		tessellator.startDrawingQuads();

		tessellator.addVertexWithUV(
				guiLeft,
				guiTop,
				0,
				0.0,
				0.0
		);

		tessellator.addVertexWithUV(
				guiLeft,
				guiTop + ySize,
				0,
				0.0,
				1.0
		);

		tessellator.addVertexWithUV(
				guiLeft + ySize,
				guiTop + ySize,
				0,
				1.0,
				1.0
		);

		tessellator.addVertexWithUV(
				guiLeft + ySize,
				guiTop,
				0,
				1.0,
				0.0
		);

		tessellator.draw();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int p_146979_1_, final int p_146979_2_)
	{
		fontRendererObj.drawString(I18n.format(tileEntityGiantCraftingTable.getInventoryName()), 7, 7, 0x404040);
		fontRendererObj.drawString(I18n.format("container.inventory"), 7, 183, 0x404040);
	}
}
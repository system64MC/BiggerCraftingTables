package wanion.biggercraftingtables.nei;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import codechicken.nei.event.NEIRegisterHandlerInfosEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import wanion.biggercraftingtables.block.BlockBiggerCraftingTable;

public final class NEIEventHandler
{
	public static void register()
	{
		MinecraftForge.EVENT_BUS.register(new NEIEventHandler());
	}

	@SubscribeEvent()
	public void registerHandlerInfos(NEIRegisterHandlerInfosEvent event) {
		System.out.println("NEIRegisterHandlerInfosEvent fired!");

		event.registerHandlerInfo(
				"wanion.biggercraftingtables.nei.BigShapedRecipeHandler",
				"Bigger Crafting Tables",
				"biggercraftingtables",
				builder -> builder
						.setWidth(125)
						.setHeight(92+18)
						// .setShiftY(-18)
						.setDisplayStack(new ItemStack(BlockBiggerCraftingTable.instance, 1, 0))
		);

		event.registerHandlerInfo(
				"wanion.biggercraftingtables.nei.BigShapelessRecipeHandler",
				"Bigger Crafting Tables",
				"biggercraftingtables",
				builder -> builder
						.setWidth(125)
						.setHeight(92+18)
						// .setShiftY(-18)
						.setDisplayStack(new ItemStack(BlockBiggerCraftingTable.instance, 1, 0))
		);


		event.registerHandlerInfo(
				"wanion.biggercraftingtables.nei.HugeShapedRecipeHandler",
				"Bigger Crafting Tables",
				"biggercraftingtables",
				builder -> builder
						.setDisplayStack(new ItemStack(BlockBiggerCraftingTable.instance, 1, 1))
		);

		event.registerHandlerInfo(
				"wanion.biggercraftingtables.nei.HugeShapelessRecipeHandler",
				"Bigger Crafting Tables",
				"biggercraftingtables",
				builder -> builder
						.setDisplayStack(new ItemStack(BlockBiggerCraftingTable.instance, 1, 1))
		);


		event.registerHandlerInfo(
				"wanion.biggercraftingtables.nei.GiantShapedRecipeHandler",
				"Bigger Crafting Tables",
				"biggercraftingtables",
				builder -> builder
						.setDisplayStack(new ItemStack(BlockBiggerCraftingTable.instance, 1, 2))
		);

		event.registerHandlerInfo(
				"wanion.biggercraftingtables.nei.GiantShapelessRecipeHandler",
				"Bigger Crafting Tables",
				"biggercraftingtables",
				builder -> builder
						.setDisplayStack(new ItemStack(BlockBiggerCraftingTable.instance, 1, 2))
		);


		event.registerHandlerInfo(
				"wanion.biggercraftingtables.nei.MassiveShapedRecipeHandler",
				"Bigger Crafting Tables",
				"biggercraftingtables",
				builder -> builder
						.setWidth(256)
						.setHeight(230)
						// .setAllowOverflowX(true)
						// .setAllowOverflowY(true)
						.setDisplayStack(new ItemStack(BlockBiggerCraftingTable.instance, 1, 3))
						.setShowOverlayButton(true)
		);

		event.registerHandlerInfo(
				"wanion.biggercraftingtables.nei.MassiveShapelessRecipeHandler",
				"Bigger Crafting Tables",
				"biggercraftingtables",
				builder -> builder
						.setWidth(256)
						.setHeight(230)
						// .setAllowOverflowX(true)
						// .setAllowOverflowY(true)
						.setDisplayStack(new ItemStack(BlockBiggerCraftingTable.instance, 1, 3))
						.setShowOverlayButton(true)
		);
	}
}

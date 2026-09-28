package wanion.biggercraftingtables.client;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import wanion.biggercraftingtables.CommonProxy;
import wanion.biggercraftingtables.nei.NEI;
import wanion.biggercraftingtables.nei.NEIEventHandler;

public final class ClientProxy extends CommonProxy
{
	@Override
	public void postInit()
	{
		super.postInit();
		if (Loader.isModLoaded("NotEnoughItems")) {
			ModContainer neiMod = Loader.instance().getIndexedModList().get("NotEnoughItems");
			System.out.println("NEI detected, initializing and registering event handler");
			System.out.println(neiMod.getVersion());
			NEI.init();

			// Is it the GTNH Version? If so, we use GTNH features.
			if(neiMod.getVersion().toLowerCase().contains("gtnh"))
				NEIEventHandler.register();
		}
	}
}
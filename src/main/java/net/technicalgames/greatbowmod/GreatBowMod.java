package net.technicalgames.greatbowmod;

import net.fabricmc.api.ModInitializer;

import net.technicalgames.greatbowmod.item.ModItemGroups;
import net.technicalgames.greatbowmod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreatBowMod implements ModInitializer {
	public static final String MOD_ID = "greatbowmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
	}
}
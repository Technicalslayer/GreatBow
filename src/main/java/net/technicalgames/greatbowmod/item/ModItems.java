package net.technicalgames.greatbowmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.technicalgames.greatbowmod.GreatBowMod;
import net.technicalgames.greatbowmod.item.custom.GreatArrowItem;
import net.technicalgames.greatbowmod.item.custom.GreatBowItem;

public class ModItems {
    public static final Item GREAT_BOW = registerItem("great_bow",
            new GreatBowItem());

    public static final Item GREAT_ARROW = registerItem("great_arrow", new GreatArrowItem());

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries){


    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(GreatBowMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        GreatBowMod.LOGGER.info("Registering Mod Items for " + GreatBowMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}

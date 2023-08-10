package net.technicalgames.greatbowmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.technicalgames.greatbowmod.GreatBowMod;

public class ModItemGroups {
    public static final ItemGroup RUBY_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(GreatBowMod.MOD_ID, "ruby"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.great_bow"))
                    .icon(() -> new ItemStack(ModItems.GREAT_BOW)).entries((displayContext, entries) -> {

                        entries.add(ModItems.GREAT_BOW);
                        entries.add(ModItems.GREAT_ARROW);
                    }).build());
    public static void registerItemGroups(){
        GreatBowMod.LOGGER.info("Registering Item Groups for " + GreatBowMod.MOD_ID);
    }
}

package net.technicalgames.greatbowmod.item.client;

import net.minecraft.util.Identifier;
import net.technicalgames.greatbowmod.GreatBowMod;
import net.technicalgames.greatbowmod.item.custom.GreatBowItem;
import software.bernie.geckolib.model.GeoModel;

public class GreatBowModel extends GeoModel<GreatBowItem> {
    @Override
    public Identifier getModelResource(GreatBowItem animatable) {
        return new Identifier(GreatBowMod.MOD_ID, "geo/great_bow.geo.json");
    }

    @Override
    public Identifier getTextureResource(GreatBowItem animatable) {
        return new Identifier(GreatBowMod.MOD_ID, "textures/item/great_bow.png");

    }

    @Override
    public Identifier getAnimationResource(GreatBowItem animatable) {
        return new Identifier(GreatBowMod.MOD_ID, "animations/great_bow.animation.json");

    }
}

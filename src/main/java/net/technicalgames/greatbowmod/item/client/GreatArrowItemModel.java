package net.technicalgames.greatbowmod.item.client;

import net.minecraft.util.Identifier;
import net.technicalgames.greatbowmod.GreatBowMod;
import net.technicalgames.greatbowmod.item.custom.GreatArrowItem;
import software.bernie.geckolib.model.GeoModel;

public class GreatArrowItemModel extends GeoModel<GreatArrowItem> {
    @Override
    public Identifier getModelResource(GreatArrowItem animatable) {
        return new Identifier(GreatBowMod.MOD_ID, "geo/great_arrow.geo.json");
    }

    @Override
    public Identifier getTextureResource(GreatArrowItem animatable) {
        return new Identifier(GreatBowMod.MOD_ID, "textures/item/great_arrow.png");

    }

    @Override
    public Identifier getAnimationResource(GreatArrowItem animatable) {
        return new Identifier(GreatBowMod.MOD_ID, "animations/great_arrow.animation.json");
        //return null;

    }
}

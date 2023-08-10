package net.technicalgames.greatbowmod.item.client;

import net.minecraft.util.Identifier;
import net.technicalgames.greatbowmod.GreatBowMod;
import net.technicalgames.greatbowmod.entity.custom.GreatArrowEntity;
import software.bernie.geckolib.model.GeoModel;

public class GreatArrowEntityModel extends GeoModel<GreatArrowEntity> {
    @Override
    public Identifier getModelResource(GreatArrowEntity animatable) {
        return new Identifier(GreatBowMod.MOD_ID, "geo/great_arrow.geo.json");
    }

    @Override
    public Identifier getTextureResource(GreatArrowEntity animatable) {
        return new Identifier(GreatBowMod.MOD_ID, "textures/entity/great_arrow.png");

    }

    @Override
    public Identifier getAnimationResource(GreatArrowEntity animatable) {
        return new Identifier(GreatBowMod.MOD_ID, "animations/great_arrow.animation.json");
        //return null;

    }
}

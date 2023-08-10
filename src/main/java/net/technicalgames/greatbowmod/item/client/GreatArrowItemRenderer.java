package net.technicalgames.greatbowmod.item.client;

import net.technicalgames.greatbowmod.item.custom.GreatArrowItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GreatArrowItemRenderer extends GeoItemRenderer<GreatArrowItem> {
    public GreatArrowItemRenderer() {
        super(new GreatArrowItemModel());
    }
}

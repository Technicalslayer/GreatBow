package net.technicalgames.greatbowmod.item.client;

import net.technicalgames.greatbowmod.item.custom.GreatBowItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GreatBowItemRenderer extends GeoItemRenderer<GreatBowItem> {
    public GreatBowItemRenderer() {
        super(new GreatBowModel());
    }
}

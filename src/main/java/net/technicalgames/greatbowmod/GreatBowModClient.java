package net.technicalgames.greatbowmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.technicalgames.greatbowmod.entity.ModEntity;
import net.technicalgames.greatbowmod.item.client.GreatArrowEntityRenderer;
import software.bernie.example.client.renderer.entity.BatRenderer;
import software.bernie.example.registry.EntityRegistry;

public class GreatBowModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //ModModelPredicateProvider.registerModModels();
        EntityRendererRegistry.register(ModEntity.GREAT_ARROW, GreatArrowEntityRenderer::new);

    }
}

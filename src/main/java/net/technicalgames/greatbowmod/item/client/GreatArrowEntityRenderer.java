package net.technicalgames.greatbowmod.item.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.technicalgames.greatbowmod.entity.custom.GreatArrowEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GreatArrowEntityRenderer extends GeoEntityRenderer<GreatArrowEntity>{
    public GreatArrowEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GreatArrowEntityModel());
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, GreatArrowEntity animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(animatable.getYaw()));
        poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-animatable.getPitch()));
        poseStack.translate(0,-0.35,-0.3); //offset model to match hitbox
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

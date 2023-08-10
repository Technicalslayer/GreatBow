package net.technicalgames.greatbowmod.mixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.technicalgames.greatbowmod.item.custom.GreatBowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/*
    Injects method same as vanilla bow
    that provides zoom effect when
    pulling the modded bow
 */
@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin
{
    @Inject(method = "getFovMultiplier", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void greatBowsZoom(CallbackInfoReturnable<Float> cir, float f)
    {
        AbstractClientPlayerEntity abstractPlayer = MinecraftClient.getInstance().player;

        if (abstractPlayer == null)
            return;
        if (abstractPlayer.getActiveItem() == null)
            return;

        ItemStack itemStack = abstractPlayer.getActiveItem();

        if (abstractPlayer.isUsingItem())
        {
            if (itemStack.getItem() instanceof GreatBowItem)
            {
                int i = abstractPlayer.getItemUseTime();
                float g = (float)i / 50.0F;
                if (g > 1.0F)
                {
                    g = 1.0F;
                }
                else
                {
                    g *= g;
                }

                f *= 1.0F - g * 0.15F;

                cir.setReturnValue(MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(), 1.0F, f));
            }
        }
    }
}

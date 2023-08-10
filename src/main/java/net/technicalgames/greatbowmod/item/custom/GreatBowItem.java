package net.technicalgames.greatbowmod.item.custom;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.technicalgames.greatbowmod.item.ModItems;
import net.technicalgames.greatbowmod.item.client.GreatBowItemRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.network.SerializableDataTicket;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class GreatBowItem extends RangedWeaponItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    public static final Predicate<ItemStack> GREAT_BOW_PROJECTILES = stack -> stack.isOf(ModItems.GREAT_ARROW);
    public static final RawAnimation TEST = RawAnimation.begin().thenPlay("test");

    public GreatBowItem() {
        super(new Settings().maxCount(1).maxDamage(201));

        // Register our item as server-side handled.
        // This enables both animation data syncing and server-side animation triggering
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GreatBowItemRenderer renderer = null;
            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new GreatBowItemRenderer();

                return this.renderer;
            }

        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

    @Override
    public double getTick(Object itemStack){
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "bow_controller", state -> PlayState.CONTINUE)
                .triggerableAnim("draw", RawAnimation.begin().thenPlayAndHold("animation.great_bow.draw"))
                .triggerableAnim("release", RawAnimation.begin().thenPlay("animation.great_bow.release")));

    }

    //start using the item once clicked
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand){
        player.setCurrentHand(hand);

        //play animation
        if(world instanceof ServerWorld serverLevel){
            triggerAnim(player, GeoItem.getOrAssignId(player.getStackInHand(hand), serverLevel), "bow_controller", "draw");
        }
        return TypedActionResult.consume(player.getStackInHand(hand));
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        boolean bl2;
        int i;
        float f;
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)user;
        boolean bl = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
        ItemStack itemStack = playerEntity.getProjectileType(stack);
        if (itemStack.isEmpty() && !bl) {
            return;
        }
        if (itemStack.isEmpty()) {
            itemStack = new ItemStack(ModItems.GREAT_ARROW);
        }
        if ((double)(f = GreatBowItem.getPullProgress(i = this.getMaxUseTime(stack) - remainingUseTicks)) < 0.1) {
            return;
        }
        boolean bl3 = bl2 = bl && itemStack.isOf(ModItems.GREAT_ARROW);
        if (!world.isClient) {
            int k;
            int j;
            GreatArrowItem greatArrowItem = (GreatArrowItem)(itemStack.getItem() instanceof GreatArrowItem ? itemStack.getItem() : ModItems.GREAT_ARROW);
            PersistentProjectileEntity persistentProjectileEntity = greatArrowItem.createArrow(world, itemStack, playerEntity);
            persistentProjectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, f * 6.0f, 1.0f);
            persistentProjectileEntity.setDamage(3.0); //higher speed means higher damage
            persistentProjectileEntity.setPierceLevel((byte) (persistentProjectileEntity.getPierceLevel() +1));
            if (f == 1.0f) {
                persistentProjectileEntity.setCritical(true);
            }
            if ((j = EnchantmentHelper.getLevel(Enchantments.POWER, stack)) > 0) {
                persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double)j * 0.5 + 0.5);
            }
            if ((k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack)) > 0) {
                persistentProjectileEntity.setPunch(k);
            }
            if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                persistentProjectileEntity.setOnFireFor(100);
            }
            stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(playerEntity.getActiveHand()));
            world.spawnEntity(persistentProjectileEntity);

            //play animation
            triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerWorld)world), "bow_controller", "release");

        }
        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.2f, 0.75f / (world.getRandom().nextFloat() * 0.4f + 1.2f) + f * 0.5f);
        if (!bl2 && !playerEntity.getAbilities().creativeMode) {
            itemStack.decrement(1);
            if (itemStack.isEmpty()) {
                playerEntity.getInventory().removeOne(itemStack);
            }
        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }



    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 50.0f;
        if ((f = (f * f + f * 2.0f) / 3.0f) > 1.0f) {
            f = 1.0f;
        }
        return f;
    }


    // Use vanilla animation to 'pull back' the pistol while charging it
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    // Let's add some ammo text to the tooltip
    /*@Override
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        tooltip.add(Text.translatable("item." + GeckoLib.MOD_ID + ".pistol.ammo",
                        stack.getMaxDamage() - stack.getDamage() - 1,
                        stack.getMaxDamage() - 1)
                .formatted(Formatting.ITALIC));
    }*/

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return GREAT_BOW_PROJECTILES;
    }

    @Override
    public int getRange() {
        return 30;
    }
}

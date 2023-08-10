package net.technicalgames.greatbowmod.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.technicalgames.greatbowmod.entity.ModEntity;
import net.technicalgames.greatbowmod.item.ModItems;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GreatArrowEntity extends PersistentProjectileEntity implements GeoEntity {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    public GreatArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public GreatArrowEntity(World world, LivingEntity owner) {
        super(ModEntity.GREAT_ARROW, owner, world);
    }

    public GreatArrowEntity(World world, double x, double y, double z) {
        super(ModEntity.GREAT_ARROW, x, y, z, world);
    }


    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.GREAT_ARROW);
    }

    @Override
    public void setDamage(double damage) {
        super.setDamage(damage);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "Flying", 5, this::flyAnimController));
    }

    protected <E extends GreatArrowEntity> PlayState flyAnimController(final AnimationState<E> event) {
        if (event.isMoving())
            return event.setAndContinue(RawAnimation.begin().thenLoop("fly"));

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
    }


/*@Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.spawnParticles(1);
                }
            } else {
                this.spawnParticles(2);
            }
        } *//*else if (this.inGround && this.inGroundTime != 0 && !this.effects.isEmpty() && this.inGroundTime >= 600) {
            this.getWorld().sendEntityStatus(this, (byte)0);
            this.potion = Potions.EMPTY;
            this.effects.clear();
            this.dataTracker.set(COLOR, -1);
        }*//*
    }

    private void spawnParticles(int amount) {
        *//*int i = this.getColor();
        if (i == -1 || amount <= 0) {
            return;
        }*//*
        double d = (double)(i >> 16 & 0xFF) / 255.0;
        double e = (double)(i >> 8 & 0xFF) / 255.0;
        double f = (double)(i >> 0 & 0xFF) / 255.0;
        for (int j = 0; j < amount; ++j) {
            this.getWorld().addParticle(ParticleTypes.ENTITY_EFFECT, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), d, e, f);
        }
    }*/
}

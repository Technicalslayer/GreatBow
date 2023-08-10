package net.technicalgames.greatbowmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.technicalgames.greatbowmod.GreatBowMod;
import net.technicalgames.greatbowmod.entity.custom.GreatArrowEntity;

public class ModEntity {
    public static final EntityType<GreatArrowEntity> GREAT_ARROW = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(GreatBowMod.MOD_ID, "great_arrow"),
            FabricEntityTypeBuilder.<GreatArrowEntity>create(SpawnGroup.MISC, GreatArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f,0.25f)).trackRangeBlocks(50).trackedUpdateRate(20).build()
    );
}

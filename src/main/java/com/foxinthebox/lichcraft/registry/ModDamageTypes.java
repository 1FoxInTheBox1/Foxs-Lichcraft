package com.foxinthebox.lichcraft.registry;

import com.foxinthebox.lichcraft.Lichcraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> LOW_SOUL_REND = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Lichcraft.getID("low_soul_rend"));
    public static final RegistryKey<DamageType> HIGH_SOUL_REND = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Lichcraft.getID("high_soul_rend"));

    public static DamageSource create(World world, RegistryKey<DamageType> damageType) {
        return create(world, damageType, null, null);
    }

    public static DamageSource create(World world, RegistryKey<DamageType> damageType, @Nullable Entity source) {
        return create(world, damageType, null, source);
    }

    public static DamageSource create(World world, RegistryKey<DamageType> damageType, @Nullable Entity projectile, @Nullable Entity source) {
        return new DamageSource(world.getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(damageType), projectile, source);
    }

    public static void initialize() {}
}

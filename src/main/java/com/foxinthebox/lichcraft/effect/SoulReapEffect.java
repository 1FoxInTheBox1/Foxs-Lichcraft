package com.foxinthebox.lichcraft.effect;

import com.foxinthebox.lichcraft.registry.ModTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

public class SoulReapEffect extends InstantStatusEffect {
    public SoulReapEffect() {
        super(StatusEffectCategory.HARMFUL, 0x2f3c76);
    }
    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        entity.damage(world, ModTags.createDamageSource(world, ModTags.HIGH_SOUL_REAP, null), (float)(3 << amplifier));

        return true;
    }


    @Override
    public void applyInstantEffect(ServerWorld world, @Nullable Entity effectEntity, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        int i = (int)(proximity * (double)(3 << amplifier) + 0.5);
        if (effectEntity != null) {
            target.damage(world, ModTags.createDamageSource(target.getWorld(), ModTags.HIGH_SOUL_REAP, attacker), i);
        } else {
            target.damage(world, ModTags.createDamageSource(target.getWorld(), ModTags.HIGH_SOUL_REAP), i);
        }
    }
}

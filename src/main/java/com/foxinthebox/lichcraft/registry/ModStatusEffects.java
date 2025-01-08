package com.foxinthebox.lichcraft.registry;

import com.foxinthebox.lichcraft.Lichcraft;
import com.foxinthebox.lichcraft.effect.SoulReapEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModStatusEffects {

    public static final RegistryEntry.Reference<StatusEffect> SOUL_REAP = create("soul_reap", new SoulReapEffect());

    public static RegistryEntry.Reference<StatusEffect> create(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Lichcraft.getID(name), statusEffect);
    }

    public static void initialize() {

    }
}

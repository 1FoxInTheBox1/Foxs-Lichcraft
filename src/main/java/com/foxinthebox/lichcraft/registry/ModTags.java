package com.foxinthebox.lichcraft.registry;

import com.foxinthebox.lichcraft.Lichcraft;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    // Item Tags
    public static final TagKey<Item> HIGH_POWER_SOUL = createItemTag("high_power_soul");
    public static final TagKey<Item> MID_POWER_SOUL = createItemTag("mid_power_soul");
    public static final TagKey<Item> LOW_POWER_SOUL = createItemTag("low_power_soul");
    public static final TagKey<Item> PHYLACTERY_FUEL = createItemTag("phylactery_fuel");

    // Entity Type Tags
    public static final TagKey<EntityType<?>> LOW_SOUL_YIELD = createEntityTypeTag("low_soul_yield");
    public static final TagKey<EntityType<?>> MID_SOUL_YIELD = createEntityTypeTag("mid_soul_yield");
    public static final TagKey<EntityType<?>> HIGH_SOUL_YIELD = createEntityTypeTag("high_soul_yield");
    public static final TagKey<EntityType<?>> EXTREME_SOUL_YIELD = createEntityTypeTag("extreme_soul_yield");

    public static TagKey<Item> createItemTag(String name) {
        return TagKey.of(RegistryKeys.ITEM, Lichcraft.getID(name));
    }

    public static TagKey<EntityType<?>> createEntityTypeTag(String name) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Lichcraft.getID(name));
    }

}

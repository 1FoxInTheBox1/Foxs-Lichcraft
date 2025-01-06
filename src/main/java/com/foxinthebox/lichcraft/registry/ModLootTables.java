package com.foxinthebox.lichcraft.registry;

import com.foxinthebox.lichcraft.Lichcraft;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModLootTables {

    public static RegistryKey<LootTable> LOW_YIELD_LOW_REAP = RegistryKey.of(RegistryKeys.LOOT_TABLE, create("souls/low_yield_low_reap"));
    public static RegistryKey<LootTable> MID_YIELD_LOW_REAP = RegistryKey.of(RegistryKeys.LOOT_TABLE, create("souls/mid_yield_low_reap"));
    public static RegistryKey<LootTable> HIGH_YIELD_LOW_REAP = RegistryKey.of(RegistryKeys.LOOT_TABLE, create("souls/high_yield_low_reap"));
    public static RegistryKey<LootTable> EXTREME_YIELD_LOW_REAP = RegistryKey.of(RegistryKeys.LOOT_TABLE, create("souls/extreme_yield_low_reap"));
    public static RegistryKey<LootTable> LOW_YIELD_HIGH_REAP = RegistryKey.of(RegistryKeys.LOOT_TABLE, create("souls/low_yield_high_reap"));
    public static RegistryKey<LootTable> MID_YIELD_HIGH_REAP = RegistryKey.of(RegistryKeys.LOOT_TABLE, create("souls/mid_yield_high_reap"));
    public static RegistryKey<LootTable> HIGH_YIELD_HIGH_REAP = RegistryKey.of(RegistryKeys.LOOT_TABLE, create("souls/high_yield_high_reap"));
    public static RegistryKey<LootTable> EXTREME_YIELD_HIGH_REAP = RegistryKey.of(RegistryKeys.LOOT_TABLE, create("souls/extreme_yield_high_reap"));


    private static Identifier create(String name) {
        return Identifier.of(Lichcraft.MOD_ID, name);
    }
}

package com.foxinthebox.lichcraft.item;

import com.foxinthebox.lichcraft.FoxsLichcraft;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item SOUL_GOO = register(new Item.Settings(), "soul_goo");

    public static Item register(Item.Settings itemSettings, String id) {
        Identifier itemID = Identifier.of(FoxsLichcraft.MOD_ID, id);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, itemID);
        Item.Settings settings = itemSettings.registryKey(key);

        return Registry.register(Registries.ITEM, itemID, new Item(settings));
    }

    public static void initialize() {
    }
}

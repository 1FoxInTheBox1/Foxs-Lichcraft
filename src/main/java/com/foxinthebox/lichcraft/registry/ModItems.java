package com.foxinthebox.lichcraft.registry;

import com.foxinthebox.lichcraft.FoxsLichcraft;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {
    // Items
    public static final Item SOUL_GOO = register(new Item.Settings(), "soul_goo");

    // Item Group
    public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(FoxsLichcraft.MOD_ID, "item_group"));
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SOUL_GOO))
            .displayName(Text.translatable("itemGroup.lichcraft"))
            .build();

    public static Item register(Item.Settings itemSettings, String id) {
        Identifier itemID = Identifier.of(FoxsLichcraft.MOD_ID, id);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, itemID);
        Item.Settings settings = itemSettings.registryKey(key);

        return Registry.register(Registries.ITEM, itemID, new Item(settings));
    }

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY, ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(SOUL_GOO);
        });
    }
}

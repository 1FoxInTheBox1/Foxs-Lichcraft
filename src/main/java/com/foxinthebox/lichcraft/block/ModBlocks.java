package com.foxinthebox.lichcraft.block;

import com.foxinthebox.lichcraft.FoxsLichcraft;
import com.foxinthebox.lichcraft.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBlocks {

//    public static final Block SOUL_MASHER = register(AbstractBlock.Settings.create(), "soul_masher", true);
    public static final Block SOUL_MASHER = register(new SoulMasher(AbstractBlock.Settings.create()), SoulMasher.ID, true);

    public static Block register(Block.Settings blockSettings, String id, boolean shouldRegisterItem) {
        Identifier blockID = getID(id);
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, blockID);
        Block.Settings settings = blockSettings.registryKey(blockKey);
        Block newBlock = new Block(settings);

        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockID);
            Registry.register(Registries.ITEM,
                                blockID,
                                new BlockItem(newBlock, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey()));
        }

        return Registry.register(Registries.BLOCK, blockID, newBlock);
    }

    public static Block register(Block block, Identifier blockID, boolean shouldRegisterItem) {
        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockID);
            Registry.register(Registries.ITEM,
                    blockID,
                    new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey()));
        }

        return Registry.register(Registries.BLOCK, blockID, block);
    }

    public static Identifier getID(String id) {
        return Identifier.of(FoxsLichcraft.MOD_ID, id);
    }


    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ModItems.ITEM_GROUP_KEY).register((itemGroup) -> {
            itemGroup.add(ModBlocks.SOUL_MASHER.asItem());
        });
    }
}

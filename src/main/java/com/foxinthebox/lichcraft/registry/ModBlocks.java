package com.foxinthebox.lichcraft.registry;

import com.foxinthebox.lichcraft.Lichcraft;
import com.foxinthebox.lichcraft.block.PhylacteryBlock;
import com.foxinthebox.lichcraft.block.PhylacteryBlockEntity;
import com.foxinthebox.lichcraft.block.SoulMasherBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // Blocks
    public static final Block SOUL_MASHER_BLOCK = registerBlock(new SoulMasherBlock(AbstractBlock.Settings.create()), SoulMasherBlock.ID, true);
    public static final Block PHYLACTERY_BLOCK = registerBlock(new PhylacteryBlock(AbstractBlock.Settings.create()), PhylacteryBlock.ID, true);

    // Block Entities
    public static final BlockEntityType<PhylacteryBlockEntity> PHYLACTERY_BLOCK_ENTITY = registerBlockEntity("phylactery", FabricBlockEntityTypeBuilder.create(PhylacteryBlockEntity::new, PHYLACTERY_BLOCK).build());

    public static Block registerBlock(Block.Settings blockSettings, String id, boolean shouldRegisterItem) {
        Identifier blockID = Lichcraft.getID(id);
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

    public static Block registerBlock(Block block, Identifier blockID, boolean shouldRegisterItem) {
        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockID);
            Registry.register(Registries.ITEM,
                    blockID,
                    new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey()));
        }

        return Registry.register(Registries.BLOCK, blockID, block);
    }

    public static <T extends BlockEntityType<?>> T registerBlockEntity(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Lichcraft.getID(path), blockEntityType);
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ModItems.ITEM_GROUP_KEY).register((itemGroup) -> {
            itemGroup.add(ModBlocks.SOUL_MASHER_BLOCK.asItem());
            itemGroup.add(ModBlocks.PHYLACTERY_BLOCK.asItem());
        });
    }
}

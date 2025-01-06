package com.foxinthebox.lichcraft.datagen;

import com.foxinthebox.lichcraft.registry.ModItems;
import com.foxinthebox.lichcraft.registry.ModLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.context.ContextType;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModEntityLootTableProvider extends SimpleFabricLootTableProvider {
    public ModEntityLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.ENTITY);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(ModLootTables.LOW_YIELD_LOW_REAP, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(1,3))
                        .with(ItemEntry.builder(ModItems.SOUL_GOO))
                        .with(EmptyEntry.builder())));
        lootTableBiConsumer.accept(ModLootTables.LOW_YIELD_HIGH_REAP, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(1,3))
                        .with(ItemEntry.builder(ModItems.SOUL_GOO)
                                .weight(7))
                        .with(ItemEntry.builder(ModItems.UNCHARGED_SOUL_CRYSTAL)
                                .weight(1))
                        .with(EmptyEntry.builder()
                                .weight(2))));

        lootTableBiConsumer.accept(ModLootTables.MID_YIELD_LOW_REAP, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(2,4))
                        .with(ItemEntry.builder(ModItems.SOUL_GOO)
                                .weight(7))
                        .with(ItemEntry.builder(ModItems.UNCHARGED_SOUL_CRYSTAL)
                                .weight(4))
                        .with(EmptyEntry.builder()
                                .weight(4))));
        lootTableBiConsumer.accept(ModLootTables.MID_YIELD_HIGH_REAP, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(4,6))
                        .with(ItemEntry.builder(ModItems.SOUL_GOO)
                                .weight(7))
                        .with(ItemEntry.builder(ModItems.UNCHARGED_SOUL_CRYSTAL)
                                .weight(4))
                        .with(EmptyEntry.builder()
                                .weight(3))));

        lootTableBiConsumer.accept(ModLootTables.HIGH_YIELD_LOW_REAP, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(1, 2))
                        .with(ItemEntry.builder(ModItems.UNCHARGED_SOUL_CRYSTAL)))
                .pool(LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(3, 4))
                        .with(ItemEntry.builder(ModItems.SOUL_GOO)
                                .weight(6))
                        .with(ItemEntry.builder(ModItems.UNCHARGED_SOUL_CRYSTAL)
                                .weight(3))
                        .with(EmptyEntry.builder()
                                .weight(6))));
        lootTableBiConsumer.accept(ModLootTables.HIGH_YIELD_HIGH_REAP, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.UNCHARGED_SOUL_CRYSTAL)))
                .pool(LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(3, 4))
                        .with(ItemEntry.builder(ModItems.SOUL_GOO)
                                .weight(2))
                        .with(ItemEntry.builder(ModItems.UNCHARGED_SOUL_CRYSTAL)
                                .weight(4))
                        .with(EmptyEntry.builder()
                                .weight(2))));

        lootTableBiConsumer.accept(ModLootTables.EXTREME_YIELD_LOW_REAP, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.SOUL_STAR))));
        lootTableBiConsumer.accept(ModLootTables.EXTREME_YIELD_HIGH_REAP, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.SOUL_STAR)))
                .pool(LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(10,25))
                        .with(ItemEntry.builder(ModItems.UNCHARGED_SOUL_CRYSTAL))));
    }
}

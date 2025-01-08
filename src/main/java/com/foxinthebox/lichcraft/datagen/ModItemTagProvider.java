package com.foxinthebox.lichcraft.datagen;

import com.foxinthebox.lichcraft.registry.ModItems;
import com.foxinthebox.lichcraft.registry.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.PHYLACTERY_FUEL)
                .add(ModItems.SOUL_GOO)
                .add(ModItems.CHARGED_SOUL_CRYSTAL)
                .add(ModItems.UNCHARGED_SOUL_CRYSTAL)
                .add(ModItems.ECHOING_GOO)
                .add(ModItems.SOUL_STAR)
                .add(Items.NETHER_STAR);

        getOrCreateTagBuilder(ModTags.HIGH_POWER_SOUL)
                .add(ModItems.SOUL_STAR)
                .add(Items.NETHER_STAR);

        getOrCreateTagBuilder(ModTags.MID_POWER_SOUL)
                .add(ModItems.CHARGED_SOUL_CRYSTAL)
                .add(ModItems.ECHOING_GOO);

        getOrCreateTagBuilder(ModTags.LOW_POWER_SOUL)
                .add(ModItems.SOUL_GOO)
                .add(ModItems.UNCHARGED_SOUL_CRYSTAL);
    }
}

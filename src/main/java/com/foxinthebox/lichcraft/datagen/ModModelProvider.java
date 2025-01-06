package com.foxinthebox.lichcraft.datagen;

import com.foxinthebox.lichcraft.registry.ModBlocks;
import com.foxinthebox.lichcraft.registry.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PHYLACTERY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOUL_MASHER_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SOUL_GOO, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHARGED_SOUL_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.UNCHARGED_SOUL_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL_STAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.ECHOING_GOO, Models.GENERATED);
        itemModelGenerator.register(ModItems.DREAD_STEEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.RESONANT_SLAG, Models.GENERATED);
    }
}

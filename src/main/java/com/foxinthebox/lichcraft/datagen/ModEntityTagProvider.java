package com.foxinthebox.lichcraft.datagen;

import com.foxinthebox.lichcraft.registry.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public ModEntityTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.LOW_SOUL_YIELD)
                .add(EntityType.ARMADILLO)
                .add(EntityType.AXOLOTL)
                .add(EntityType.BAT)
                .add(EntityType.CAMEL)
                .add(EntityType.CAT)
                .add(EntityType.CHICKEN)
                .add(EntityType.COW)
                .add(EntityType.COD)
                .add(EntityType.DONKEY)
                .add(EntityType.FROG)
                .add(EntityType.GLOW_SQUID)
                .add(EntityType.HORSE)
                .add(EntityType.MOOSHROOM)
                .add(EntityType.MULE)
                .add(EntityType.OCELOT)
                .add(EntityType.PARROT)
                .add(EntityType.PIG)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.RABBIT)
                .add(EntityType.SALMON)
                .add(EntityType.SHEEP)
                .add(EntityType.SNIFFER)
                .add(EntityType.SQUID)
                .add(EntityType.STRIDER)
                .add(EntityType.TADPOLE)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.TURTLE)
                .add(EntityType.BEE)
                .add(EntityType.CAVE_SPIDER)
                .add(EntityType.DOLPHIN)
                .add(EntityType.FOX)
                .add(EntityType.GOAT)
                .add(EntityType.LLAMA)
                .add(EntityType.PANDA)
                .add(EntityType.POLAR_BEAR)
                .add(EntityType.SPIDER)
                .add(EntityType.TRADER_LLAMA)
                .add(EntityType.WOLF)
                .add(EntityType.HOGLIN)
                .add(EntityType.SILVERFISH)
                .add(EntityType.ENDERMITE);

        getOrCreateTagBuilder(ModTags.MID_SOUL_YIELD)
                .add(EntityType.ALLAY)
                .add(EntityType.VILLAGER)
                .add(EntityType.PIGLIN)
                .add(EntityType.PIGLIN_BRUTE)
                .add(EntityType.PILLAGER)
                .add(EntityType.VINDICATOR)
                .add(EntityType.ENDERMAN)
                .add(EntityType.GHAST)
                .add(EntityType.RAVAGER);

        getOrCreateTagBuilder(ModTags.HIGH_SOUL_YIELD)
                .add(EntityType.EVOKER)
                .add(EntityType.ILLUSIONER)
                .add(EntityType.WITCH);

        getOrCreateTagBuilder(ModTags.EXTREME_SOUL_YIELD)
                .add(EntityType.PLAYER)
                .add(EntityType.WITHER)
                .add(EntityType.ENDER_DRAGON)
                .add(EntityType.WARDEN);
    }
}

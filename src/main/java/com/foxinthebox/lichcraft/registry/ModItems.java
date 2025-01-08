package com.foxinthebox.lichcraft.registry;

import com.foxinthebox.lichcraft.Lichcraft;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    // Items
    public static final Item SOUL_GOO = register(new Item.Settings(), "soul_goo");
    public static final Item CHARGED_SOUL_CRYSTAL = register(new Item.Settings().rarity(Rarity.UNCOMMON), "charged_soul_crystal");
    public static final Item UNCHARGED_SOUL_CRYSTAL = register(new Item.Settings().rarity(Rarity.UNCOMMON), "uncharged_soul_crystal");
    public static final Item ECHOING_GOO = register(new Item.Settings().rarity(Rarity.UNCOMMON), "echoing_goo");
    public static final Item SOUL_STAR = register(new Item.Settings().rarity(Rarity.RARE), "soul_star");
    public static final Item RESONANT_SLAG = register(new Item.Settings().rarity(Rarity.EPIC), "resonant_slag");
    public static final Item DREAD_STEEL = register(new Item.Settings(), "dread_steel");
    public static final Potion SOUL_REAP_POTION_WEAK = Registry.register(Registries.POTION, Lichcraft.getID("soul_reap_weak"),
                                                    new Potion("soul_reap", new StatusEffectInstance(ModStatusEffects.SOUL_REAP, 1, 0)));
    public static final Potion SOUL_REAP_POTION_STRONG = Registry.register(Registries.POTION, Lichcraft.getID("soul_reap_strong"),
                                                    new Potion("soul_reap", new StatusEffectInstance(ModStatusEffects.SOUL_REAP, 1, 1)));

    // Item Group
    public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(Lichcraft.MOD_ID, "item_group"));
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SOUL_GOO))
            .displayName(Text.translatable("itemGroup.lichcraft"))
            .build();

    public static Item register(Item.Settings itemSettings, String id) {
        Identifier itemID = Identifier.of(Lichcraft.MOD_ID, id);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, itemID);
        Item.Settings settings = itemSettings.registryKey(key);

        return Registry.register(Registries.ITEM, itemID, new Item(settings));
    }

    public static void registerModPotionRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(
                    Potions.AWKWARD,
                    CHARGED_SOUL_CRYSTAL,
                    Registries.POTION.getEntry(SOUL_REAP_POTION_WEAK)
            );
        });

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(
                    Registries.POTION.getEntry(SOUL_REAP_POTION_WEAK),
                    Items.SCULK_CATALYST,
                    Registries.POTION.getEntry(SOUL_REAP_POTION_STRONG)
            );
        });
    }

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY, ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(SOUL_GOO);
            itemGroup.add(CHARGED_SOUL_CRYSTAL);
            itemGroup.add(UNCHARGED_SOUL_CRYSTAL);
            itemGroup.add(ECHOING_GOO);
            itemGroup.add(SOUL_STAR);
            itemGroup.add(RESONANT_SLAG);
            itemGroup.add(DREAD_STEEL);
        });

        registerModPotionRecipes();
    }
}

package com.foxinthebox.lichcraft.registry;

import com.foxinthebox.lichcraft.Lichcraft;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

public class ModItems {
    // Items
    public static final Item SOUL_GOO = register(new Item.Settings(), "soul_goo");
    public static final Item CHARGED_SOUL_CRYSTAL = register(new Item.Settings().rarity(Rarity.UNCOMMON), "charged_soul_crystal");
    public static final Item UNCHARGED_SOUL_CRYSTAL = register(new Item.Settings().rarity(Rarity.UNCOMMON), "uncharged_soul_crystal");
    public static final Item ECHOING_GOO = register(new Item.Settings().rarity(Rarity.UNCOMMON), "echoing_goo");
    public static final Item SOUL_STAR = register(new Item.Settings().rarity(Rarity.RARE), "soul_star");
    public static final Item RESONANT_SLAG = register(new Item.Settings().rarity(Rarity.EPIC), "resonant_slag");
    public static final Item DREAD_STEEL = register(new Item.Settings(), "dread_steel");

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

        DispenserBlock.registerBehavior(ModBlocks.SOUL_MASHER_BLOCK.asItem(),
                new FallibleItemDispenserBehavior() {
                    @Override
                    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                        ServerWorld serverWorld = pointer.world();
                        BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
                        if (serverWorld.isAir(blockPos)) {
                            serverWorld.setBlockState(blockPos, ModBlocks.SOUL_MASHER_BLOCK.getDefaultState());
                            serverWorld.emitGameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
                        }

                        if (this.isSuccess()) {
                            stack.decrement(1);
                        }

                        return stack;
                    }
                }
        );
    }
}

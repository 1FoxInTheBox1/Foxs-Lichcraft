package com.foxinthebox.lichcraft.block;

import com.foxinthebox.lichcraft.registry.ModBlocks;
import com.foxinthebox.lichcraft.registry.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PhylacteryBlockEntity extends BlockEntity implements Inventory {
    public static final int MAX_SOULS = 5000;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int souls = 0;

    public PhylacteryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.PHYLACTERY_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.writeNbt(nbt, items, registries);
        nbt.putInt("souls", souls);
        super.writeNbt(nbt, registries);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        Inventories.readNbt(nbt, items, registries);
        souls = nbt.getInt("souls");
    }

    public static void tick(World world, BlockPos pos, BlockState state, PhylacteryBlockEntity blockEntity) {
        if (!blockEntity.isEmpty()) {
            ItemStack stack = blockEntity.getStack(0);
            if (stack.isIn(ModTags.PHYLACTERY_FUEL)) {
                int soulValue = stack.isIn(ModTags.LOW_POWER_SOUL) ? 1 : 0;
                soulValue = stack.isIn(ModTags.MID_POWER_SOUL) ? 2 : soulValue;
                soulValue = stack.isIn(ModTags.HIGH_POWER_SOUL) ? 100 : soulValue;
                blockEntity.setSouls(blockEntity.getSouls() + soulValue);

                stack.setCount(stack.getCount() - 1);

                blockEntity.markDirty();
            }
        }
    }

    public int getSouls() {
        return souls;
    }

    public void setSouls(int amount) {
        souls = Math.clamp(amount, 0, MAX_SOULS);
        markDirty();
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return items.getFirst().isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return items.getFirst();
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(items, slot, amount);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(items, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > stack.getMaxCount()) {
            stack.setCount(stack.getMaxCount());
        }
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return stack.isIn(ModTags.PHYLACTERY_FUEL);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        items.clear();
    }
}

package com.foxinthebox.lichcraft.block;

import com.foxinthebox.lichcraft.Lichcraft;
import com.foxinthebox.lichcraft.registry.ModBlocks;
import com.foxinthebox.lichcraft.registry.ModTags;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class PhylacteryBlock extends BlockWithEntity {
    public static final Identifier ID = Lichcraft.getID("phylactery");
    public static final MapCodec<PhylacteryBlock> CODEC = createCodec(PhylacteryBlock::new);
    private static final ImmutableList<Vec3i> VALID_HORIZONTAL_SPAWN_OFFSETS = ImmutableList.of(
            new Vec3i(0, 0, -1),
            new Vec3i(-1, 0, 0),
            new Vec3i(0, 0, 1),
            new Vec3i(1, 0, 0),
            new Vec3i(-1, 0, -1),
            new Vec3i(1, 0, -1),
            new Vec3i(-1, 0, 1),
            new Vec3i(1, 0, 1)
    );
    private static final ImmutableList<Vec3i> VALID_SPAWN_OFFSETS = new ImmutableList.Builder<Vec3i>()
            .addAll(VALID_HORIZONTAL_SPAWN_OFFSETS)
            .addAll(VALID_HORIZONTAL_SPAWN_OFFSETS.stream().map(Vec3i::down).iterator())
            .addAll(VALID_HORIZONTAL_SPAWN_OFFSETS.stream().map(Vec3i::up).iterator())
            .add(new Vec3i(0, 1, 0))
            .build();

    private static final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(5, 0, 5, 11, 6, 11),
            Block.createCuboidShape(6, 6, 6, 10, 7, 10),
            Block.createCuboidShape(5, 7, 5, 11, 8, 11),
            Block.createCuboidShape(6, 8, 6, 10, 9, 10)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public PhylacteryBlock(Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, ID))
                .sounds(BlockSoundGroup.SCULK_SHRIEKER));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;

        if (!(world.getBlockEntity(pos) instanceof PhylacteryBlockEntity blockEntity)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }

        setPlayerSpawn((ServerPlayerEntity)player, world, pos);

        if (!stack.isEmpty()) {
            if (stack.isIn(ModTags.PHYLACTERY_FUEL)) {
                if (blockEntity.isEmpty()) {
                    blockEntity.setStack(0, player.getStackInHand(hand).copy());
                    stack.setCount(0);
                    world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SENSOR_BREAK, SoundCategory.BLOCKS);
                } else {
                    int inBlock = blockEntity.getStack(0).getCount();
                    ItemStack newStack = stack.copy();
                    stack.setCount(stack.getCount() - (stack.getMaxCount() - inBlock));
                    newStack.setCount(Math.min(inBlock + stack.getCount(), 64));
                    blockEntity.setStack(0, newStack);
                    world.playSound(null, pos, SoundEvents.BLOCK_SCULK_CATALYST_PLACE, SoundCategory.BLOCKS);
                }
            } else {
                world.playSound(null, pos, SoundEvents.BLOCK_SCULK_CATALYST_PLACE, SoundCategory.BLOCKS);
            }
        }

        return ActionResult.SUCCESS;
    }

    private void setPlayerSpawn(ServerPlayerEntity serverPlayerEntity, World world, BlockPos pos) {
        if (serverPlayerEntity.getSpawnPointDimension() != world.getRegistryKey() || !pos.equals(serverPlayerEntity.getSpawnPointPosition())) {
            serverPlayerEntity.setSpawnPoint(world.getRegistryKey(), pos, 0.0F, false, true);
            world.playSound(
                    null,
                    (double)pos.getX() + 0.5,
                    (double)pos.getY() + 0.5,
                    (double)pos.getZ() + 0.5,
                    SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN,
                    SoundCategory.BLOCKS,
                    1.0F,
                    1.0F
            );
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlocks.PHYLACTERY_BLOCK_ENTITY, PhylacteryBlockEntity::tick);
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return super.getComparatorOutput(state, world, pos);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PhylacteryBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}

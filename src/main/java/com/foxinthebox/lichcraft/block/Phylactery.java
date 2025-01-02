package com.foxinthebox.lichcraft.block;

import com.foxinthebox.lichcraft.FoxsLichcraft;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.CollisionView;
import net.minecraft.world.World;

import java.util.Optional;

public class Phylactery extends Block {
    public static final Identifier ID = FoxsLichcraft.getID("phylactery");
    public static final MapCodec<Phylactery> CODEC = createCodec(Phylactery::new);
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

    public Phylactery(Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, ID))
                .sounds(BlockSoundGroup.SCULK_SHRIEKER));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
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
                return ActionResult.SUCCESS_SERVER;
            }
        }
        return ActionResult.SUCCESS;
    }
}

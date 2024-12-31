package com.foxinthebox.lichcraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class SoulMasher extends FallingBlock {
    public static final Identifier ID = ModBlocks.getID("soul_masher");
    public static final MapCodec<SoulMasher> CODEC = createCodec(SoulMasher::new);
    private static final float FALL_HURT_AMOUNT = 3.0F;
    private static final int FALL_HURT_MAX_DAMAGE = 30;

    public SoulMasher(Settings settings) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, ID))
                .sounds(BlockSoundGroup.ANVIL));
    }

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtEntities(FALL_HURT_AMOUNT, FALL_HURT_MAX_DAMAGE);
    }

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.syncWorldEvent(WorldEvents.ANVIL_LANDS, pos, 0);
        }
    }
}

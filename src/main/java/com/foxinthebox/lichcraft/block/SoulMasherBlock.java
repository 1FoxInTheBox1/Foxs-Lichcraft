package com.foxinthebox.lichcraft.block;

import com.foxinthebox.lichcraft.Lichcraft;
import com.foxinthebox.lichcraft.registry.ModDamageTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class SoulMasherBlock extends FallingBlock {
    public static final Identifier ID = Lichcraft.getID("soul_masher");
    public static final MapCodec<SoulMasherBlock> CODEC = createCodec(SoulMasherBlock::new);
    private static final float FALL_HURT_AMOUNT = 3.0F;
    private static final int FALL_HURT_MAX_DAMAGE = 30;

    public SoulMasherBlock(Settings settings) {
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
    public DamageSource getDamageSource(Entity attacker) {
        return ModDamageTypes.create(attacker.getWorld(), ModDamageTypes.LOW_SOUL_REAP, attacker);
    }

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.syncWorldEvent(WorldEvents.ANVIL_LANDS, pos, 0);
        }
    }
}

package com.foxinthebox.lichcraft.mixin;

import com.foxinthebox.lichcraft.block.PhylacteryBlock;
import com.foxinthebox.lichcraft.block.PhylacteryBlockEntity;
import com.foxinthebox.lichcraft.registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends LivingEntityMixin {
    @Inject(at = @At("RETURN"), method="applyDamage(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;F)V")
    protected void onDamage(ServerWorld world, DamageSource source, float amount, CallbackInfo ci) {
        if (((PlayerEntity) (Object) this).getHealth() <= 0 && !source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            BlockPos respawnPos = ((ServerPlayerEntity) (Object) this).getSpawnPointPosition();
            if (respawnPos != null) {
                ServerWorld respawnWorld = ((ServerPlayerEntity) (Object) this).server.getWorld(((ServerPlayerEntity) (Object) this).getSpawnPointDimension());
                BlockState blockState = respawnWorld.getBlockState(respawnPos);
                Block block = blockState.getBlock();
                BlockEntity blockEntity = respawnWorld.getBlockEntity(respawnPos);

                if (block instanceof PhylacteryBlock && blockEntity instanceof PhylacteryBlockEntity phylacteryBlockEntity) {
                    if (phylacteryBlockEntity.getSouls() >= 1000) {
                        Optional<Vec3d> optional = RespawnAnchorBlock.findRespawnPosition(EntityType.PLAYER, respawnWorld, respawnPos);
                        if (optional.isPresent()) {
                            ((PlayerEntity) (Object) this).setHealth(5.0f);
                            ((PlayerEntity) (Object) this).setVelocity(Vec3d.ZERO);
                            TeleportTarget teleportTarget = new TeleportTarget(respawnWorld, optional.get(), Vec3d.ZERO, ((ServerPlayerEntity) (Object) this).getYaw(), 0.0F, TeleportTarget.ADD_PORTAL_CHUNK_TICKET);
                            ((PlayerEntity) (Object) this).teleportTo(teleportTarget);
                        }
                        phylacteryBlockEntity.setSouls(phylacteryBlockEntity.getSouls() - 1000);
                        phylacteryBlockEntity.markDirty();
                    }
                }
            }
        }
    }
}

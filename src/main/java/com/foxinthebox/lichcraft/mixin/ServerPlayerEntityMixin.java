package com.foxinthebox.lichcraft.mixin;

import com.foxinthebox.lichcraft.block.Phylactery;
import com.foxinthebox.lichcraft.registry.ModDamageTypes;
import com.foxinthebox.lichcraft.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "findRespawnPosition(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;FZZ)Ljava/util/Optional;", cancellable = true)
    private static void findRespawnPosition(ServerWorld world, BlockPos pos, float spawnAngle, boolean spawnForced, boolean alive, CallbackInfoReturnable<Optional<ServerPlayerEntity.RespawnPos>> cir) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof Phylactery) {
            Optional<Vec3d> optional = RespawnAnchorBlock.findRespawnPosition(EntityType.PLAYER, world, pos);
            cir.setReturnValue(optional.map(respawnPos -> ServerPlayerEntity.RespawnPos.fromCurrentPos(respawnPos, pos)));
        }
    }
}

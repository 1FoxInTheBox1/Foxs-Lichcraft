package com.foxinthebox.lichcraft.mixin;

import com.foxinthebox.lichcraft.registry.ModDamageTypes;
import com.foxinthebox.lichcraft.registry.ModItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(at = @At("HEAD"), method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V")
	private void onDeath(DamageSource damageSource, CallbackInfo ci) {
		if (damageSource.isOf(ModDamageTypes.LOW_SOUL_REND)) {
			ItemStack stack = new ItemStack(ModItems.SOUL_GOO);
			ItemEntity itemEntity = new ItemEntity(((LivingEntity) (Object) this).getWorld(),
					((LivingEntity) (Object) this).getX(),
					((LivingEntity) (Object) this).getY(),
					((LivingEntity) (Object) this).getZ(),
					stack);
			((LivingEntity) (Object) this).getWorld().spawnEntity(itemEntity);
		}
	}
}
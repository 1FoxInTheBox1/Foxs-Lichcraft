package com.foxinthebox.lichcraft.mixin;

import com.foxinthebox.lichcraft.registry.ModDamageTypes;
import com.foxinthebox.lichcraft.registry.ModItems;
import com.foxinthebox.lichcraft.registry.ModLootTables;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(at = @At("HEAD"), method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V")
	private void onDeath(DamageSource damageSource, CallbackInfo ci) {
		if (damageSource.isOf(ModDamageTypes.LOW_SOUL_REND)) {
			dropSouls((ServerWorld) ((LivingEntity) (Object) this).getWorld(), damageSource);
		}
	}

	protected void dropSouls(ServerWorld world, DamageSource damageSource) {
		for (ServerPlayerEntity player : world.getPlayers()) {
			player.sendMessage(Text.literal("penis"));
		}
		Optional<RegistryKey<LootTable>> optional = Optional.of(ModLootTables.LOW_YIELD_LOW_REAP);
		if (!optional.isEmpty()) {
			for (ServerPlayerEntity player : world.getPlayers()) {
				player.sendMessage(Text.literal("penis again"));
			}
			LootTable lootTable = world.getServer().getReloadableRegistries().getLootTable((RegistryKey<LootTable>)optional.get());
			LootWorldContext.Builder builder = new LootWorldContext.Builder(world)
					.add(LootContextParameters.THIS_ENTITY, (LivingEntity) (Object) this)
					.add(LootContextParameters.ORIGIN, ((LivingEntity) (Object) this).getPos())
					.add(LootContextParameters.DAMAGE_SOURCE, damageSource);

			LootWorldContext lootWorldContext = builder.build(LootContextTypes.ENTITY);
			lootTable.generateLoot(lootWorldContext,
					((LivingEntity) (Object) this).getLootTableSeed(),
					stack -> ((LivingEntity) (Object) this).dropStack(world, stack));
		}
	}
}
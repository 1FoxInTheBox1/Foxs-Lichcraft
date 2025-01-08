package com.foxinthebox.lichcraft.mixin;

import com.foxinthebox.lichcraft.registry.ModLootTables;
import com.foxinthebox.lichcraft.registry.ModTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(at = @At("HEAD"), method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V")
	private void onDeath(DamageSource damageSource, CallbackInfo ci) {
		if (damageSource.isOf(ModTags.LOW_SOUL_REAP) || damageSource.isOf(ModTags.HIGH_SOUL_REAP)) {
			dropSouls((ServerWorld) ((LivingEntity) (Object) this).getWorld(), damageSource);
		}
	}

	@Unique
	public void dropSouls(ServerWorld world, DamageSource damageSource) {
		Optional<RegistryKey<LootTable>> optional = getSoulLootTable(damageSource);

		if (!optional.isEmpty()) {
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

	// TODO: This sucks. Fix this.
	@Unique
	protected Optional<RegistryKey<LootTable>> getSoulLootTable(DamageSource damageSource) {
		if (damageSource.isOf(ModTags.LOW_SOUL_REAP)) {
			if (((Entity) (Object) this).getType().isIn(ModTags.EXTREME_SOUL_YIELD)) {
				return Optional.of(ModLootTables.EXTREME_YIELD_LOW_REAP);
			}
			if (((Entity) (Object) this).getType().isIn(ModTags.HIGH_SOUL_YIELD)) {
				return Optional.of(ModLootTables.HIGH_YIELD_LOW_REAP);
			}
			if (((Entity) (Object) this).getType().isIn(ModTags.MID_SOUL_YIELD)) {
				return Optional.of(ModLootTables.MID_YIELD_LOW_REAP);
			}
			if (((Entity) (Object) this).getType().isIn(ModTags.LOW_SOUL_YIELD)) {
				return Optional.of(ModLootTables.LOW_YIELD_LOW_REAP);
			}
		}
		if (damageSource.isOf(ModTags.HIGH_SOUL_REAP)) {
			if (((Entity) (Object) this).getType().isIn(ModTags.EXTREME_SOUL_YIELD)) {
				return Optional.of(ModLootTables.EXTREME_YIELD_HIGH_REAP);
			}
			if (((Entity) (Object) this).getType().isIn(ModTags.HIGH_SOUL_YIELD)) {
				return Optional.of(ModLootTables.HIGH_YIELD_HIGH_REAP);
			}
			if (((Entity) (Object) this).getType().isIn(ModTags.MID_SOUL_YIELD)) {
				return Optional.of(ModLootTables.MID_YIELD_HIGH_REAP);
			}
			if (((Entity) (Object) this).getType().isIn(ModTags.LOW_SOUL_YIELD)) {
				return Optional.of(ModLootTables.LOW_YIELD_HIGH_REAP);
			}
		}
		return Optional.empty();
	}
}


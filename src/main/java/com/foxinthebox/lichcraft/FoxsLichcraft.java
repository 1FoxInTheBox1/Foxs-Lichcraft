package com.foxinthebox.lichcraft;

import com.foxinthebox.lichcraft.events.LivingEntityDeathCallback;
import com.foxinthebox.lichcraft.registry.ModBlocks;
import com.foxinthebox.lichcraft.registry.ModDamageTypes;
import com.foxinthebox.lichcraft.registry.ModItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoxsLichcraft implements ModInitializer {
	public static final String MOD_ID = "lichcraft";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModBlocks.initialize();
		ModDamageTypes.initialize();

		LivingEntityDeathCallback.EVENT.register((livingEntity) -> {


			return ActionResult.FAIL;
		});
	}

	public static Identifier getID(String id) {
		return Identifier.of(FoxsLichcraft.MOD_ID, id);
	}
}
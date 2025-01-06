package com.foxinthebox.lichcraft;

import com.foxinthebox.lichcraft.registry.*;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lichcraft implements ModInitializer {
	public static final String MOD_ID = "lichcraft";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModBlocks.initialize();
		ModTags.initialize();
		ModLootTables.initialize();
		ModStatusEffects.initialize();
	}

	public static Identifier getID(String id) {
		return Identifier.of(Lichcraft.MOD_ID, id);
	}
}
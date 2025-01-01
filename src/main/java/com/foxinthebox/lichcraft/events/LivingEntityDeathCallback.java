package com.foxinthebox.lichcraft.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface LivingEntityDeathCallback {
    Event<LivingEntityDeathCallback> EVENT = EventFactory.createArrayBacked(LivingEntityDeathCallback.class,
            (listeners) -> (livingEntity) -> {
                for (LivingEntityDeathCallback listener : listeners) {
                    ActionResult result = listener.interact(livingEntity);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(LivingEntity livingEntity);
}

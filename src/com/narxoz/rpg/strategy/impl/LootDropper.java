package com.narxoz.rpg.strategy.impl;

import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.Random;

public class LootDropper implements GameObserver {

    private static final String[] PHASE_LOOT = {
            "Old Coin",
            "Dark Essence",
            "Broken Blade"
    };

    private static final String[] FINAL_LOOT = {
            "King's Sword",
            "Ancient Crown",
            "Dragon Heart"
    };

    private final Random random;

    public LootDropper(Random random) {
        this.random = random;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            System.out.println("[LOOT] Phase drop: " + randomFrom(PHASE_LOOT));
        }
        if (event.getType() == GameEventType.BOSS_DEFEATED) {
            System.out.println("[LOOT] Final drop: " + randomFrom(FINAL_LOOT));
        }
    }

    private String randomFrom(String[] values) {
        return values[random.nextInt(values.length)];
    }
}

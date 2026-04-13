package com.narxoz.rpg.strategy.impl;

import com.narxoz.rpg.combatant.StrategyHero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AchievementTracker implements GameObserver {

    private final List<StrategyHero> heroes;
    private final Set<String> unlocked = new HashSet<>();
    private int landedAttacks = 0;
    private boolean heroDied = false;

    public AchievementTracker(List<StrategyHero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.ATTACK_LANDED) {
            landedAttacks++;
            unlock("First Blood", landedAttacks >= 1);
            unlock("Relentless", landedAttacks >= 10);
        }
        if (event.getType() == GameEventType.HERO_DIED) {
            heroDied = true;
            unlock("Fallen Comrade", true);
        }
        if (event.getType() == GameEventType.BOSS_DEFEATED) {
            unlock("Boss Slayer", true);
            unlock("No Man Left Behind", !heroDied);
            unlock("Perfect Run", allHeroesAlive());
        }
    }

    private void unlock(String name, boolean condition) {
        if (condition && unlocked.add(name)) {
            System.out.println("[ACHIEVEMENT] " + name);
        }
    }

    private boolean allHeroesAlive() {
        for (StrategyHero hero : heroes) {
            if (!hero.isAlive()) {
                return false;
            }
        }
        return true;
    }
}

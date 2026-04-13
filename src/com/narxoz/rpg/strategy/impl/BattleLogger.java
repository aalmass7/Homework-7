package com.narxoz.rpg.strategy.impl;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.StrategyHero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameObserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleLogger implements GameObserver {

    private final Map<String, StrategyHero> heroesByName = new HashMap<>();
    private final DungeonBoss boss;

    public BattleLogger(List<StrategyHero> heroes, DungeonBoss boss) {
        this.boss = boss;
        for (StrategyHero hero : heroes) {
            heroesByName.put(hero.getName(), hero);
        }

    }

    @Override
    public void onEvent(GameEvent event){
        switch (event.getType()) {
            case ATTACK_LANDED:
                System.out.println("[LOG] ATTACK_LANDED | " + event.getSourceName()
                        + " dealt " + event.getValue() + " damage.");
                break;

            case HERO_LOW_HP:
                System.out.println("[LOG] HERO_LOW_HP | " + event.getSourceName()
                        + " dropped to " + event.getValue() + " HP.");
                break;

            case HERO_DIED:
                System.out.println("[LOG] HERO_DIED | " + event.getSourceName() + " has died.");
                break;

            case BOSS_PHASE_CHANGED:
                System.out.println("[LOG] BOSS_PHASE_CHANGED | " + event.getSourceName()
                        + " entered phase " + event.getValue()
                        + " with strategy: " + strategyNameForPhase(event.getValue()) + ".");
                break;

            case BOSS_DEFEATED:
                System.out.println("[LOG] BOSS_DEFEATED | " + event.getSourceName()
                        + " was defeated after " + event.getValue() + " rounds.");
                break;

            default:
                break;

        }
    }

    private String strategyNameForPhase(int phase) {

        if (phase == 1) {
            return "Boss Phase 1";
        }
        if (phase == 2) {
            return "Boss Phase 2";
        }
        if (phase == 3) {
            return "Boss Phase 3";
        }
        return boss.getStrategy().getName();
    }
}

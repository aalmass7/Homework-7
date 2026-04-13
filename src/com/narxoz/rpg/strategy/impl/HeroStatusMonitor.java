package com.narxoz.rpg.strategy.impl;

import com.narxoz.rpg.combatant.StrategyHero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.List;

public class HeroStatusMonitor implements GameObserver {

    private final List<StrategyHero> heroes;

    public HeroStatusMonitor(List<StrategyHero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() != GameEventType.HERO_LOW_HP
                && event.getType() != GameEventType.HERO_DIED) {
            return;
        }
        System.out.println("[STATUS] Party status update:");
        for (StrategyHero hero : heroes) {
            System.out.println(" - " + hero.getName()
                    + ": HP=" + hero.getHp() + "/" + hero.getMaxHp()
                    + ", alive=" + hero.isAlive());
        }
    }
}

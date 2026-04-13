package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.combatant.StrategyHero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartySupport implements GameObserver {

        private final List<StrategyHero> heroes;
        private final int healAmount;
        private final Random random;

public PartySupport(List<StrategyHero> heroes, int healAmount, Random random){
    this.heroes = heroes;
    this.healAmount = healAmount;
    this.random = random;
}

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() != GameEventType.HERO_LOW_HP) {
            return;
        }

        List<StrategyHero> livingAllies = new ArrayList<>();
        for (StrategyHero hero : heroes) {
            if (hero.isAlive() && !hero.getName().equals(event.getSourceName())) {
                livingAllies.add(hero);
            }
        }

        if (livingAllies.isEmpty()) {
            return;
        }

        StrategyHero target = livingAllies.get(random.nextInt(livingAllies.size()));
        int before = target.getHp();
        target.heal(healAmount);

        System.out.println("[SUPPORT] " + target.getName()
                + " was healed for " + healAmount
                + " HP (" + before + " -> " + target.getHp() + ").");
    }
}

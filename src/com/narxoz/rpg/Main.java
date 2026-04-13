package com.narxoz.rpg;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.StrategyHero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.GameEventBus;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.observer.impl.AchievementTracker;
import com.narxoz.rpg.observer.impl.BattleLogger;
import com.narxoz.rpg.observer.impl.HeroStatusMonitor;
import com.narxoz.rpg.observer.impl.LootDropper;
import com.narxoz.rpg.observer.impl.PartySupport;
import com.narxoz.rpg.strategy.impl.AggressiveStrategy;
import com.narxoz.rpg.strategy.impl.BalancedStrategy;
import com.narxoz.rpg.strategy.impl.DefensiveStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        StrategyHero neo = new StrategyHero("Neo", 300, 42, 12, new AggressiveStrategy());
        StrategyHero morpheus = new StrategyHero("Morpheus", 230, 40, 13, new DefensiveStrategy());
        StrategyHero trinity = new StrategyHero("Trinity", 210, 30, 11, new BalancedStrategy());

        List<StrategyHero> heroes = new ArrayList<>();
        heroes.add(neo);
        heroes.add(morpheus);
        heroes.add(trinity);

        GameEventBus eventBus = new GameEventBus();
        DungeonBoss boss = new DungeonBoss(
                "Cursed Dungeon Boss",
                600,
                34,
                18,
                eventBus
        );

        eventBus.registerObserver(new BattleLogger(heroes, boss));
        eventBus.registerObserver(new AchievementTracker(heroes));
        eventBus.registerObserver(new PartySupport(heroes, 35, new Random(42)));
        eventBus.registerObserver(new HeroStatusMonitor(heroes));
        eventBus.registerObserver(new LootDropper(new Random(99)));
        eventBus.registerObserver(boss);

        DungeonEngine engine = new DungeonEngine(heroes, boss, eventBus, 50);

        engine.addRoundHook(3, () -> {
            System.out.println("[MAIN] Neo switches strategy from Defensive to Aggressive.");
            morpheus.setStrategy(new AggressiveStrategy());
        });

        EncounterResult result = engine.runEncounter();

        System.out.println();
        System.out.println("===== FINAL RESULT =====");
        System.out.println("Heroes won: " + result.isHeroesWon());
        System.out.println("Rounds played: " + result.getRoundsPlayed());
        System.out.println("Surviving heroes: " + result.getSurvivingHeroes());
    }
}

package com.narxoz.rpg.engine;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.StrategyHero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventBus;
import com.narxoz.rpg.observer.GameEventType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DungeonEngine {
    private final List<StrategyHero> heroes;
    private final DungeonBoss boss;
    private final GameEventBus eventBus;
    private final int maxRounds;

    private final Set<String> lowHpTriggered = new HashSet<>();
    private final Set<String> deathTriggered = new HashSet<>();
    private final Map<Integer, Runnable> roundHooks = new HashMap<>();

    public DungeonEngine(List<StrategyHero> heroes, DungeonBoss boss, GameEventBus eventBus, int maxRounds) {
        this.heroes = heroes;
        this.boss = boss;
        this.eventBus = eventBus;
        this.maxRounds = maxRounds;
    }

    public void addRoundHook(int round, Runnable action){
        if(round > 0 && action != null){
            roundHooks.put(round, action);
        }
    }

    public EncounterResult runEncounter(){
        int roundsPlayed = 0;

        for(int round = 1; round <= maxRounds; round++) {
            roundsPlayed = round;
            System.out.println();
            System.out.println("=== Round " + round + " ===");

            Runnable hook = roundHooks.get(round);
            if (hook != null) {
                hook.run();
            }

            for (StrategyHero hero : heroes) {
                if (!hero.isAlive() || !boss.isAlive()) {
                    continue;
                }

                int damage = calculateFinalDamage(
                        hero.getStrategy().calculateDamage(hero.getAttackPower()),
                        boss.getStrategy().calculateDefense(boss.getDefense())
                );

                eventBus.fireEvent(new GameEvent(GameEventType.ATTACK_LANDED, hero.getName(), damage));
                boss.takeDamage(damage);

                if(!boss.isAlive()){
                    eventBus.fireEvent((new GameEvent(GameEventType.BOSS_DEFEATED, boss.getName(), round)));
                    return new EncounterResult(true, roundsPlayed, countLivingHeroes());
                }
            }
            if(countLivingHeroes() == 0){
                return new EncounterResult(false, roundsPlayed, 0);
            }
            for(StrategyHero hero : heroes){
                if(!hero.isAlive() || !boss.isAlive()){
                    continue;
                }

                int damage = calculateFinalDamage(
                        boss.getStrategy().calculateDamage(boss.getAttackPower()),
                        hero.getStrategy().calculateDefense(hero.getDefense())
                );

                eventBus.fireEvent(new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), damage));
                hero.takeDamage(damage);

                if (!hero.isAlive()) {
                    if (deathTriggered.add(hero.getName())) {
                        eventBus.fireEvent(new GameEvent(GameEventType.HERO_DIED, hero.getName(), 0));
                    }
                    continue;
                }

                if (!lowHpTriggered.contains(hero.getName()) && isBelowThirtyPercent(hero)) {
                    lowHpTriggered.add(hero.getName());
                    eventBus.fireEvent(new GameEvent(GameEventType.HERO_LOW_HP, hero.getName(), hero.getHp()));
                }
            }
            if (countLivingHeroes() == 0) {
                return new EncounterResult(false, roundsPlayed, 0);
            }
        }
        return new EncounterResult(false, roundsPlayed, countLivingHeroes());
    }

    private int calculateFinalDamage(int attackerDamage, int defenderDefense) {
        return Math.max(1, attackerDamage - defenderDefense);
    }

    private boolean isBelowThirtyPercent(StrategyHero hero) {
        return (long) hero.getHp() * 100 < (long) hero.getMaxHp() * 30;
    }

    private int countLivingHeroes() {
        int count = 0;
        for (StrategyHero hero : heroes) {
            if (hero.isAlive()) {
                count++;
            }
        }
        return count;
    }
}

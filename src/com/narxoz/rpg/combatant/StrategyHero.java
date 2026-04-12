package com.narxoz.rpg.combatant;

import com.narxoz.rpg.strategy.CombatStrategy;
import com.narxoz.rpg.strategy.impl.BalancedStrategy;

public class StrategyHero extends Hero{

    private CombatStrategy strategy;

    public StrategyHero(String name, int hp, int attackPower, int defense, CombatStrategy strategy) {
        super(name, hp, attackPower, defense);
        if(strategy == null){
            throw new IllegalArgumentException("strategy can't be null");
        }
        this.strategy = strategy;
    }

    public StrategyHero(String name, int hp, int attackPower, int defense) {
        this(name, hp, attackPower, defense, new BalancedStrategy());
    }

    public CombatStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(CombatStrategy strategy){
        if(strategy == null){
            throw new IllegalArgumentException("strategy can't be null");
        }
        this.strategy = strategy;
    }
}

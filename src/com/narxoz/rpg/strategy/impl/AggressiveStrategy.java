package com.narxoz.rpg.strategy.impl;

import com.narxoz.rpg.strategy.CombatStrategy;

public class AggressiveStrategy implements CombatStrategy {


    @Override
    public int calculateDamage(int basePower) {
        return (basePower * 15) / 10;
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (baseDefense * 7) / 10;
    }

    @Override
    public String getName() {
        return "Aggressive";
    }
}

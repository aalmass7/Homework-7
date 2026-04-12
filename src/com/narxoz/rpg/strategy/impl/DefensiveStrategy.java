package com.narxoz.rpg.strategy.impl;

import com.narxoz.rpg.strategy.CombatStrategy;

public class DefensiveStrategy implements CombatStrategy {
    @Override
    public int calculateDamage(int basePower) {
        return (basePower * 8) / 10;
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (baseDefense * 15) / 10;
    }

    @Override
    public String getName() {
        return "Defensive";
    }
}

package com.narxoz.rpg.strategy.impl;

import com.narxoz.rpg.strategy.CombatStrategy;

public class BossPhase1Strategy implements CombatStrategy {
    @Override
    public int calculateDamage(int basePower) {
        return (basePower * 11) / 10;
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (baseDefense * 12) / 10;
    }

    @Override
    public String getName() {
        return "Boss Phase 1";
    }
}

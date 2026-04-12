package com.narxoz.rpg.strategy.impl;

import com.narxoz.rpg.strategy.CombatStrategy;

public class BossPhase2Strategy implements CombatStrategy {
    @Override
    public int calculateDamage(int basePower) {
        return (basePower * 14) / 10;
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (baseDefense * 9) / 10;
    }

    @Override
    public String getName() {
        return "Boss Phase 2";
    }
}

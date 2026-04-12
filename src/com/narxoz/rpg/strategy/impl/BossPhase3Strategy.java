package com.narxoz.rpg.strategy.impl;

import com.narxoz.rpg.strategy.CombatStrategy;

public class BossPhase3Strategy implements CombatStrategy {
    @Override
    public int calculateDamage(int basePower) {
        return (basePower * 18) / 10;
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return 0;
    }

    @Override
    public String getName() {
        return "Boss Phase 3";
    }
}

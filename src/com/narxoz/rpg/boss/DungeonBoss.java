package com.narxoz.rpg.boss;

import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventBus;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.strategy.CombatStrategy;
import com.narxoz.rpg.strategy.impl.BossPhase1Strategy;
import com.narxoz.rpg.strategy.impl.BossPhase2Strategy;
import com.narxoz.rpg.strategy.impl.BossPhase3Strategy;


public class DungeonBoss implements GameObserver{
    private final String name;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private int hp;
    private int phase;

    private final CombatStrategy phase1Strategy;
    private final CombatStrategy phase2Strategy;
    private final CombatStrategy phase3Strategy;
    private CombatStrategy activeStrategy;

    private final GameEventBus eventBus;

    public DungeonBoss(String name, int maxHp, int attackPower, int defense, GameEventBus eventBus) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.eventBus = eventBus;

        this.phase1Strategy = new BossPhase1Strategy();
        this.phase2Strategy = new BossPhase2Strategy();
        this.phase3Strategy = new BossPhase3Strategy();

        this.phase = 1;
        this.activeStrategy = phase1Strategy;
    }

    public String getName(){
        return name;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public int getAttackPower(){
        return attackPower;
    }

    public int getDefense(){
        return defense;
    }

    public int getPhase(){
        return phase;
    }

    public CombatStrategy getStrategy(){
        return activeStrategy;
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public void takeDamage(int amount){
        int safeDamage = Math.max(0, amount);
        int oldHp = hp;
        hp = Math.max(0, hp - safeDamage);

        long oldPercentScaled = (long) oldHp * 100;
        long newPercentScaled = (long) hp * 100;
        long sixty = (long) maxHp * 60;
        long thirty =   (long) maxHp * 30;

        if(oldPercentScaled >= sixty && newPercentScaled < sixty){
            eventBus.fireEvent(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 2));
        }
        if(oldPercentScaled >= thirty && newPercentScaled < thirty){
            eventBus.fireEvent(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 3));
        }
    }

    @Override
    public void onEvent(GameEvent event) {
        if(event == null){
            return;
        }
        if(event.getType() != GameEventType.BOSS_PHASE_CHANGED){
            return;
        }
        if(!name.equals(event.getSourceName())){
            return;
        }

        int newPhase = event.getValue();
        if(newPhase <= phase){
            return;
        }

        if(newPhase == 2){
            phase = 2;
            activeStrategy = phase2Strategy;
        } else if (newPhase == 3) {
            phase = 3;
            activeStrategy = phase3Strategy;
        }
    }
}

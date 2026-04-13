package com.narxoz.rpg.observer;

import java.util.ArrayList;
import java.util.List;

public class GameEventBus {

    private final List<GameObserver> observers = new ArrayList<>();

    public void registerObserver(GameObserver observer){
        if(observer != null && !observers.contains(observer)){
            observers.add(observer);
        }
    }

    public void unregisterObserver(GameObserver observer){
        observers.remove(observer);
    }

    public void fireEvent(GameEvent event){
        List<GameObserver> snapshot = new ArrayList<>(observers);
        for(GameObserver observer : snapshot){
            observer.onEvent(event);
        }
    }
}

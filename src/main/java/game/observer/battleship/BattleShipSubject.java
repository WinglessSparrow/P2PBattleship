package game.observer.battleship;

import game.observer.base.Observer;
import game.observer.base.Subject;

import java.util.ArrayList;
import java.util.List;

public class BattleShipSubject implements Subject<BattleShipGameData> {

    private final List<Observer<BattleShipGameData>> observers = new ArrayList<>();

    @Override
    public void register(Observer<BattleShipGameData> o) {
        observers.add(o);
    }

    @Override
    public void deregister(Observer<BattleShipGameData> o) {
        observers.remove(o);
    }

    @Override
    public void notify(BattleShipGameData data) {
        observers.forEach(o -> o.update(data));
    }
}

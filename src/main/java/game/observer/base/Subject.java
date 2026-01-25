package game.observer.base;

public interface Subject<T> {
    void register(Observer<T> o);

    void deregister(Observer<T> o);

    void notify(T data);
}

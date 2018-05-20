package service;

import java.io.Serializable;
import java.rmi.Remote;

public interface IObserver extends Serializable, Remote {
    void update();
}

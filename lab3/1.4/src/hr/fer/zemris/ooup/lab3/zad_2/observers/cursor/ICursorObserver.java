package hr.fer.zemris.ooup.lab3.zad_2.observers.cursor;

import hr.fer.zemris.ooup.lab3.zad_2.position.Location;

/**
 * @author Filip Gulan
 */
public interface ICursorObserver {
    void updateCursorLocation(Location location);
}

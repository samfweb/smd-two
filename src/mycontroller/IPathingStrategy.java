package mycontroller;

import utilities.Coordinate;

public interface IPathingStrategy {

    /**
     * Calculates the next Coordinate for the car object, dependent on current strategy
     * @return
     */
    public Coordinate nextCoordinate(Coordinate currentLoc, InternalMap internalMap);
}

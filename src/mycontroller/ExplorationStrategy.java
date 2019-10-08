package mycontroller;

import utilities.Coordinate;

/**
 * Default pathing strategy for the car.
 * Attempts to navigate map to fill internal viewed map.
 */
public class ExplorationStrategy implements IPathingStrategy {

    @Override
    public Coordinate nextCoordinate(Coordinate currentLoc, InternalMap internalMap) {
        return null;
    }
}

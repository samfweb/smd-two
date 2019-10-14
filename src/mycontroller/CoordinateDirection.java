package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial;

public class CoordinateDirection {

    private WorldSpatial.Direction direction;

    private Coordinate coordinate;

    public CoordinateDirection(WorldSpatial.Direction direction, Coordinate coordinate) {
        this.direction = direction;
        this.coordinate = coordinate;
    }

    public WorldSpatial.Direction getDirection() {
        return direction;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}

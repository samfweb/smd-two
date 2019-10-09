package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;

/**
 * Additional coordinate utility methods
 */
public class CoordinateUtils {

    /**
     * @param loc original coordinate
     * @param direction compass direction to get next coordinate from
     * @return the next coordinate in a given direction
     */
    public static Coordinate getNextCoordinate(Coordinate loc, WorldSpatial.Direction direction) {
        switch (direction) {
            case NORTH:
                return new Coordinate(loc.x, loc.y + 1);
            case SOUTH:
                return new Coordinate(loc.x, loc.y - 1);
            case EAST:
                return new Coordinate(loc.x + 1, loc.y);
            case WEST:
                return new Coordinate(loc.x - 1, loc.y);
        }
        return loc;
    }

    /**
     * Gets an arraylist of all touching coordinates
     * @param coordinate the coordinate to get touching coordinates from
     * @return an arraylist of all coordinates touching the given coordinate
     */
    public static ArrayList<Coordinate> getTouchingCoordinates(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        for (WorldSpatial.Direction direction: WorldSpatial.Direction.values()) {
            coordinates.add(getNextCoordinate(coordinate, direction));
        }
        return coordinates;
    }
}

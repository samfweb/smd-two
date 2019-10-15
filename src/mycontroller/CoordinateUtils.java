package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Additional coordinate utility methods
 */
public class CoordinateUtils {

    private static final Coordinate EPSILON_VECTOR = new Coordinate(0,0);

    private static final Map<Coordinate, WorldSpatial.Direction> DIRECTION_MAP;
    static{
        HashMap<Coordinate, WorldSpatial.Direction> temporaryDirection = new HashMap<>();
        temporaryDirection.put(new Coordinate(0,1), WorldSpatial.Direction.NORTH);
        temporaryDirection.put(new Coordinate(0,-1), WorldSpatial.Direction.SOUTH);
        temporaryDirection.put(new Coordinate(-1,0), WorldSpatial.Direction.WEST);
        temporaryDirection.put(new Coordinate(1,0), WorldSpatial.Direction.EAST);
        DIRECTION_MAP = temporaryDirection;
    }

    private static final WorldSpatial.Direction[] DIRECTION_ARRAY = {
            WorldSpatial.Direction.NORTH,
            WorldSpatial.Direction.EAST,
            WorldSpatial.Direction.SOUTH,
            WorldSpatial.Direction.WEST,
    };

    private static WorldSpatial.Direction getTranslatedIndex(int translation, WorldSpatial.Direction direction){
        int indexOfDirection = Arrays.asList(DIRECTION_ARRAY).indexOf(direction);
        int newIndex = indexOfDirection + translation;
        int moddedIndex = (newIndex % DIRECTION_ARRAY.length + DIRECTION_ARRAY.length) % DIRECTION_ARRAY.length;
        return DIRECTION_ARRAY[moddedIndex];

    }

    public static WorldSpatial.Direction translateRotation(WorldSpatial.Direction translation, WorldSpatial.Direction direction){
        System.out.println("Current Orientation: " + translation + " Desired Orientation: " + direction);
        switch(translation){
            case EAST:
                return getTranslatedIndex(-1, direction);
            case SOUTH:
                return getTranslatedIndex(-2, direction);
            case WEST:
                return getTranslatedIndex(-3, direction);
            default:
                return direction;
        }
    }

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


    public static WorldSpatial.Direction translateVector(Coordinate vectorCoordinate){
        return vectorCoordinate != EPSILON_VECTOR ? DIRECTION_MAP.get(vectorCoordinate) : null;
    }

}

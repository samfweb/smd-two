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

    /**
     * The empty coordinate that represents no translation (0,0) -> stationary
     */
    private static final Coordinate EPSILON_VECTOR = new Coordinate(0,0);

    /**
     * Direction Map,
     *  -> where North represents a increase in the Y Axis
     *  -> where South represents a decrease in the Y Axis
     *  -> where East represents a increase in the X Axis
     *  -> where West represents a decrease in the X Axis
     */
    private static final Map<Coordinate, WorldSpatial.Direction> DIRECTION_MAP;
    static{
        HashMap<Coordinate, WorldSpatial.Direction> temporaryDirection = new HashMap<>();
        temporaryDirection.put(new Coordinate(0,1), WorldSpatial.Direction.NORTH);
        temporaryDirection.put(new Coordinate(0,-1), WorldSpatial.Direction.SOUTH);
        temporaryDirection.put(new Coordinate(-1,0), WorldSpatial.Direction.WEST);
        temporaryDirection.put(new Coordinate(1,0), WorldSpatial.Direction.EAST);
        DIRECTION_MAP = temporaryDirection;
    }

    /**
     * Direction Array to use for rotations, following a clockwise protocol
     */
    private static final WorldSpatial.Direction[] DIRECTION_ARRAY = {
            WorldSpatial.Direction.NORTH,
            WorldSpatial.Direction.EAST,
            WorldSpatial.Direction.SOUTH,
            WorldSpatial.Direction.WEST,
    };

    /**
     *
     * @param translation the index offset to translate our array
     * @param direction the desired direction to finish at
     * @return the translated desired direction
     */
    private static WorldSpatial.Direction getTranslatedIndex(int translation, WorldSpatial.Direction direction){
        int indexOfDirection = Arrays.asList(DIRECTION_ARRAY).indexOf(direction);
        int newIndex = indexOfDirection + translation;
        int moddedIndex = (newIndex % DIRECTION_ARRAY.length + DIRECTION_ARRAY.length) % DIRECTION_ARRAY.length;
        return DIRECTION_ARRAY[moddedIndex];

    }

    /**
     * Returns the translated direction, which is calculated relative to our current direction e.g.
     *  -> Facing West, wish to end up facing North
     *  -> We have a relative direction of East as a result
     *
     * @param current the original direction of the vehicle
     * @param desired the desired direction to finish at
     * @return the translated desired direction, relative to the translation
     */
    public static WorldSpatial.Direction translateRotation(WorldSpatial.Direction current, WorldSpatial.Direction desired){
        System.out.println("Current Orientation: " + current + " Desired Orientation: " + desired);
        switch(current){
            case EAST:
                return getTranslatedIndex(-1, desired); // move one rotation clockwise
            case SOUTH:
                return getTranslatedIndex(-2, desired); // move two rotations clockwise
            case WEST:
                return getTranslatedIndex(-3, desired); // move three rotations clockwise
            default:
                return desired;
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


    /**
     * Wraps the DIRECTION_MAP to translate vectors into directions
     *
     * @param vectorCoordinate The applicative coordinate representing delta between two coordinates
     * @return the corresponding WorldSpatial.Direction
     */
    public static WorldSpatial.Direction translateVector(Coordinate vectorCoordinate){
        return vectorCoordinate != EPSILON_VECTOR ? DIRECTION_MAP.get(vectorCoordinate) : null;
    }

}

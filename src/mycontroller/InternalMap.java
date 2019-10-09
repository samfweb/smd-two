package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;

import java.util.HashMap;

/**
 * internal map of surroundings based on car's view
 */
public class InternalMap {

    private HashMap<Coordinate, MapTile> viewedMap;

    /**
     * Constructor
     */
    public InternalMap() {
        viewedMap = new HashMap<Coordinate, MapTile>();
    }

    /**
     * Updates viewedMap with a set of seen surroundings
     * @param map - HashMap<Coordinate, MapTile>
     */
    public void updateViewedMap(HashMap<Coordinate, MapTile> map) {
        for (Coordinate key : map.keySet()) {
            if (!viewedMap.containsKey(key)) {
                viewedMap.put(key, map.get(key));
            }
        }
    }

    /**
     *
     * @param coordinate coordinate to check
     * @return true if coordinate borders unknown area of map, false otherwise
     */
    public boolean nextToUnknown(Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;
        if ( !viewedMap.containsKey(new Coordinate(x+1, y))
                || !viewedMap.containsKey(new Coordinate(x-1, y))
                || !viewedMap.containsKey(new Coordinate(x, y+1))
                || !viewedMap.containsKey(new Coordinate(x, y-1)) ) {
                    return true;
        }
        return false;
    }

    /**
     *
     * @param coordinate the coordinate of the tile to check if blocked
     * @return true if tile is wall or not in internal map, false otherwise
     */
    public boolean isBlocked(Coordinate coordinate) {
        if (!viewedMap.containsKey(coordinate)
                || viewedMap.get(coordinate).getType() == MapTile.Type.WALL) {
            return true;
        }
        return false;
    }
}


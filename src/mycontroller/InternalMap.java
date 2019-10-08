package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;

import java.util.HashMap;

/**
 * Is an internal map of surroundings based on car view
 */
public class InternalMap {

    private HashMap<Coordinate, MapTile> viewedMap = new HashMap<Coordinate, MapTile>();

    public HashMap<Coordinate, MapTile> getViewedMap() {
        return this.viewedMap;
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
     * @param coordinate the coordinate of the tile to get type from
     * @return the type of the tile
     */
    public MapTile.Type getTileType(Coordinate coordinate) {
        if (viewedMap.containsKey(coordinate)) {
            return viewedMap.get(coordinate).getType();
        }
        else {
            return MapTile.Type.WALL;
        }
    }

    /**
     *
     * @param coordinate
     * @return
     */
    public boolean isWall(Coordinate coordinate) {
        if (!viewedMap.containsKey(coordinate)
                || viewedMap.get(coordinate).getType() == MapTile.Type.WALL) {
            return true;
        }
        return false;
    }
}


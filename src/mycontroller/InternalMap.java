package mycontroller;

import controller.CarController;
import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * internal map of surroundings based on car's view
 */
public class InternalMap {

    private HashMap<Coordinate, MapTile> viewedMap;
    private int mapHeight;
    private int mapWidth;

    /**
     * Constructor
     */
    public InternalMap(int mapHeight, int mapWidth) {
        this.viewedMap = new HashMap<Coordinate, MapTile>();
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * Updates viewedMap with a set of seen surroundings
     * @param map - HashMap<Coordinate, MapTile>
     */
    public void updateViewedMap(HashMap<Coordinate, MapTile> map) {
        for (Coordinate key : map.keySet()) {
            viewedMap.put(key, map.get(key));
        }
    }

    /**
     *
     * @param coordinate coordinate to check
     * @return true if coordinate borders unknown area of map, false otherwise
     */
    public boolean nextToUnknown(Coordinate coordinate) {
        for (Coordinate co : CoordinateUtils.getTouchingCoordinates(coordinate)) {
            if (!viewedMap.containsKey(co)) {
                return true;
            }
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

    /**
     *
     * @param trapType the type of the trap to check
     * @return List of coordinates corresponding to the trap type seen
     */
     public List<Coordinate> discoveredTraps(String trapType){
        return viewedMap
                .keySet()
                .stream()
                .filter(coordinate -> viewedMap.get(coordinate).isType(MapTile.Type.TRAP))
                .collect(Collectors.toList());
     }

    /**
     *
     * @param tileType the type of tile
     * @return List of coordinates corresponding to the tile seen
     */
     public List<Coordinate> discoveredTypes(MapTile.Type tileType){
         return viewedMap
                 .keySet()
                 .stream()
                 .filter(coordinate -> viewedMap.get(coordinate).getType().equals(tileType))
                 .collect(Collectors.toList());
    }
}


package mycontroller;

import controller.CarController;
import tiles.MapTile;
import utilities.Coordinate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * @param mapHeight the height of the map in tiles
     * @param mapWidth the width of the map in tiles
     * @return matrix of MapTile representing the current internal map
     */
    public MapTile[][] transformMap(int mapWidth, int mapHeight){
        MapTile[][] mapTiles = new MapTile[mapHeight][mapWidth];
        for(int row = 0; row < mapHeight; row++){
            for(int col = 0; col < mapWidth; col++){
                mapTiles[row][col] = viewedMap.getOrDefault(new Coordinate(col, mapHeight - 1 - row), new MapTile(MapTile.Type.EMPTY));
            }
        }
        return mapTiles;
    }



    public String createDisplayMap(MapTile[][] mapGrid){
        StringBuilder gridString = new StringBuilder();
        gridString.append(generateTopRow(mapGrid[0].length));
        for(MapTile[] row : mapGrid){
            gridString.append(generateDisplayRow(row));
        }
        gridString.append(generateTopRow(mapGrid[0].length));
        gridString.append('\n');
        return gridString.toString();
    }

    private String generateTopRow(int rowSize){
        StringBuilder topRowString = new StringBuilder();
        for(int i = 0; i < rowSize * 2 + 2; i++){
            topRowString.append('-');
        }
        topRowString.append('\n');
        return topRowString.toString();
    }

    private String generateDisplayRow(MapTile[] mapRow){
        StringBuilder rowString = new StringBuilder();
        rowString.append("| ");
        for(MapTile tile : mapRow){
            // Grab the first character from the MapTile.TileType to display
            rowString.append(tile.getType().toString().charAt(0));
            rowString.append(' ');
        }
        // close the row with a bar
        rowString.append("|\n");
        return rowString.toString();
    }
}


package mycontroller;

/**
 * Pathing Method that generates and returns a path to the nearest tile bordering an unexplored tile
 */

import utilities.Coordinate;
import java.util.*;

public class ExploreMethod extends MethodTemplate {

    public ExploreMethod() {
        super();
    }

    /**
     *
     * @param startPosition the first position in the path
     * @param targetPosition the end position in any path generated
     * @param internalMap the current map representing accessible tiles
     * @return Deque<Coordinate> A list of coordinates representing the shortest valid path to the targetPosition
     */
    @Override
    public Deque<Coordinate> generatePathing(Coordinate startPosition, Coordinate targetPosition, InternalMap internalMap) {
        Coordinate optimalCoordinate = findOptimalCoordinate(internalMap, startPosition);
        return optimalCoordinate != null ?
                getPathingStrategy().findPath(startPosition, optimalCoordinate, internalMap) :
                new ArrayDeque<>();
    }


    /**
     *
     * @param internalMap
     * @param position
     * @return
     */
    private Coordinate findOptimalCoordinate(InternalMap internalMap, Coordinate position){
        List<Coordinate> coordinateOptions = getAvailableCoordinates(
                internalMap.getMapWidth(),
                internalMap.getMapHeight(),
                internalMap);
        // sort the list, via comparison of path distance between two points -> in ascending fashion
        coordinateOptions.sort(Comparator.comparingInt(coordinate -> getDistance(position, coordinate, internalMap)));
        // return the head of the options list

        return coordinateOptions.get(0);
    }


    /**
     *
     * @param mapHeight
     * @param mapWidth
     * @param internalMap
     * @return
     */
    private List<Coordinate> getAvailableCoordinates(int mapHeight, int mapWidth, InternalMap internalMap){
        List<Coordinate> coordinateOptions = new ArrayList<>();
        for(int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                if (!internalMap.isBlocked(coordinate) && internalMap.nextToUnknown(coordinate)){
                    coordinateOptions.add(coordinate);

                }
            }
        }
        return coordinateOptions;
    }

    /**
     *
     * @param startCoordinate
     * @param targetCoordinate
     * @param internalMap
     * @return
     */
    private int getDistance(Coordinate startCoordinate, Coordinate targetCoordinate, InternalMap internalMap) {
        Deque<Coordinate> possiblePath = getPathingStrategy().findPath(startCoordinate, targetCoordinate, internalMap);
        return possiblePath != null ? possiblePath.size() : internalMap.getMapWidth() * internalMap.getMapHeight();
    }
}

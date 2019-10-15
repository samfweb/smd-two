package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;

import java.util.*;

public class ExploreMethod extends MethodTemplate {

    public ExploreMethod() {
        super();
    }

    @Override
    public Queue<Coordinate> generatePathing(Coordinate startPosition, Coordinate targetPosition, InternalMap internalMap) {
        Coordinate optimalCoordinate = findOptimalCoordinate(internalMap, startPosition);
        return optimalCoordinate != null ?
                getPathingStrategy().findPath(startPosition, optimalCoordinate, internalMap) :
                new ArrayDeque<>();
    }


    private Coordinate findOptimalCoordinate(InternalMap internalMap, Coordinate position){
        List<Coordinate> coordinateOptions = getAvailableCoordinates(
                internalMap.getMapWidth(),
                internalMap.getMapHeight(),
                internalMap);
        // sort the list, via comparison of path distance between two points -> in ascending fashion
        coordinateOptions.sort(Comparator.comparingInt(coordinate -> getDistance(position, coordinate, internalMap)));
        // return the head of the options list
        System.out.println(coordinateOptions.get(0));
        return coordinateOptions.get(0);
    }


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

    private int getDistance(Coordinate startCoordinate, Coordinate targetCoordinate, InternalMap internalMap) {
        int distance = getPathingStrategy().findPath(startCoordinate, targetCoordinate, internalMap).size();
        return distance > 0 ? distance : internalMap.getMapWidth() * internalMap.getMapHeight();
    }
}

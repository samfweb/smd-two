package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

/**
 * Directed point to point pathing strategy.
 * Uses BFS to calculate the shortest path.
 */
public class ShortestPath implements IPathingStrategy {

    //Records parent of a coordinate in path
    private HashMap<Coordinate, Coordinate> parentCoordinate;

    /**
     * Constructor
     */
    public ShortestPath() {
        this.parentCoordinate = new HashMap<>();
    }

    /**
     * Returns a path to the destination coordinate
     * @param start starting coordinate
     * @param internalMap known map
     * @return the path if it exists, otherwise null
     */
    @Override
    public Queue<Coordinate> findPath(Coordinate start, Coordinate destination, InternalMap internalMap) {
        //If successful path found
        if (exploreBFS(start, destination, internalMap)) {
            return buildPath(start, destination);
        }
        return null;
    }

    /**
     * Builds a path by tracing parents back to start from end
     * @param start starting coordinate
     * @param end destination
     * @return a queue of coordinates from start to end
     */
    public Queue<Coordinate> buildPath(Coordinate start, Coordinate end) {
        Queue<Coordinate> path = null;
        Coordinate currentCoordinate = end;
        // Add the destination as final coordinate
        path.add(currentCoordinate);

        //Until the head of queue is the start loc
        while (path.element() != start) {
            currentCoordinate = parentCoordinate.get(currentCoordinate);
            path.add(currentCoordinate);
        }

        return path;
    }

    /**
     * Uses the BFS algorithm to find the shortest path between two points
     * @param start starting location
     * @param internalMap known map
     * @return
     */
    public boolean exploreBFS(Coordinate start, Coordinate destination, InternalMap internalMap) {
        Coordinate currentLoc = start;
        Queue<Coordinate> BFSQueue = null;
        BFSQueue.add(start);

        //Points parent of start node to itself
        parentCoordinate.put(currentLoc, start);

        while (!BFSQueue.isEmpty()) {
            currentLoc = BFSQueue.remove();
            if (currentLoc.equals(destination)) {
                return true;
            }
            for (Coordinate coordinate : CoordinateUtils.getTouchingCoordinates(currentLoc)) {
                //If it isn't already in the queue
                if (!parentCoordinate.containsKey(coordinate)
                        //and isn't blocked
                        && !internalMap.isBlocked(coordinate)) {
                    //link to parent
                    parentCoordinate.put(coordinate, currentLoc);
                    BFSQueue.add(coordinate);
                }
            }
        }
        return false;
    }


}
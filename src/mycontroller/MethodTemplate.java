package mycontroller;

/**
 * A Template Class for higher level Methods for Pathing
 */

import utilities.Coordinate;
import java.util.Deque;


public abstract class MethodTemplate {

    private IPathingStrategy pathingStrategy;

    /**
     * Constructor
     */
    public MethodTemplate() {
        this.pathingStrategy = new ShortestPath();
    }

    /**
     *
     * @param startPosition the first position in the path
     * @param targetPosition the end position in any path generated
     * @param internalMap the current map representing accessible tiles
     * @return Deque<Coordinate> A list of coordinates representing the shortest valid path to the targetPosition
     */
    public abstract Deque<Coordinate> generatePathing(Coordinate startPosition, Coordinate targetPosition, InternalMap internalMap);

    /**
     *
     * @return The Pathing Stategy for two Coordinates
     */
    public IPathingStrategy getPathingStrategy() {
        return pathingStrategy;
    }
}

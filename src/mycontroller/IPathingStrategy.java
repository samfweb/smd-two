package mycontroller;

import utilities.Coordinate;

import java.util.Queue;

public interface IPathingStrategy {

    /**
     * @return a queue of coordinates from start to destination based on internal map, null if no path exists
     */
    public Queue<Coordinate> findPath(Coordinate start, Coordinate destination, InternalMap internalMap);
}
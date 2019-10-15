package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;

import java.util.Deque;
import java.util.HashMap;
import java.util.Queue;

public abstract class MethodTemplate {

    private IPathingStrategy pathingStrategy;

    public MethodTemplate() {
        this.pathingStrategy = new ShortestPath();
    }

    public abstract Deque<Coordinate> generatePathing(Coordinate startPosition, Coordinate targetPosition, InternalMap internalMap);

    public IPathingStrategy getPathingStrategy() {
        return pathingStrategy;
    }
}

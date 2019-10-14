package mycontroller;

import utilities.Coordinate;

import java.util.Queue;

public class DirectMethod extends MethodTemplate {

    @Override
    public Queue<Coordinate> generatePathing(Coordinate startPosition, Coordinate targetPosition, InternalMap internalMap) {
        return  getPathingStrategy().findPath(startPosition, targetPosition, internalMap);
    }
}

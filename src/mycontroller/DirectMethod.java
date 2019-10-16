package mycontroller;

import utilities.Coordinate;

import java.util.Deque;
import java.util.Queue;

public class DirectMethod extends MethodTemplate {

    /**
     *
     * @param startPosition the first position in the path
     * @param targetPosition the end position in any path generated
     * @param internalMap the current map representing accessible tiles
     * @return Deque<Coordinate> A list of coordinates representing the shortest valid path to the targetPosition
     */
    @Override
    public Deque<Coordinate> generatePathing(Coordinate startPosition, Coordinate targetPosition, InternalMap internalMap) {
        return  getPathingStrategy().findPath(startPosition, targetPosition, internalMap);
    }


}

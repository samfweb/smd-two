package mycontroller;

import utilities.Coordinate;
import world.World;
import world.WorldSpatial;

import java.util.Queue;

public interface IPathAdapter {

    /**
     *
     * @param currentPosition
     * @param nextPosition
     * @param direction
     * @param velocity
     * @return
     */
    Command convertNextMove(Coordinate currentPosition, Coordinate nextPosition, WorldSpatial.Direction direction, float velocity);
}

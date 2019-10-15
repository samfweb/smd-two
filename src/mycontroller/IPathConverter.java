package mycontroller;

import utilities.Coordinate;
import world.World;
import world.WorldSpatial;

import java.util.Queue;

public interface IPathConverter {

    Command convertNextMove(Queue<Coordinate> path, WorldSpatial.Direction direction, float velocity);

}

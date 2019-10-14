package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial;

import java.util.Queue;

public interface IPathConverter {

    Queue<Command> convertPath(Queue<Coordinate> path, WorldSpatial.Direction direction);

}

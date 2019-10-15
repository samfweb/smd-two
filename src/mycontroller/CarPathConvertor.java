package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;
import java.util.Queue;

public class CarPathConvertor implements IPathConverter{

    public static HashMap<CoordinateDirection, Command> commandMap;

    public CarPathConvertor() {
    }

    @Override
    public Command convertNextMove(Queue<Coordinate> path, WorldSpatial.Direction direction, int velocity) {


        return null;
    }

    private Coordinate coordinateDifference(Coordinate coordinateA, Coordinate coordinateB){
        return new Coordinate((coordinateA.x - coordinateB.x), (coordinateA.y - coordinateB.y));
    }
}

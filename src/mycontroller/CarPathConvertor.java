package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;
import java.util.Queue;

public class CarPathConvertor implements IPathConverter{

    public static HashMap<CoordinateDirection, Command> commandMap;

    private static void fillCommandMap(){
        commandMap = new HashMap<>();
    }

    public CarPathConvertor() {
    }


    @Override
    public Queue<Command> convertPath(Queue<Coordinate> path, WorldSpatial.Direction direction) {
        return null;
    }


    private Command convertCoordinates(Coordinate start, Coordinate next){
        Coordinate difference = coordinateDifference(start, next);
        return null;
    }

    private Coordinate coordinateDifference(Coordinate coordinateA, Coordinate coordinateB){
        return new Coordinate((coordinateA.x - coordinateB.x), (coordinateA.y - coordinateB.y));
    }
}

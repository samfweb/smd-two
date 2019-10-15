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
    public Command convertNextMove(Queue<Coordinate> path, WorldSpatial.Direction direction, float velocity) {
        Coordinate currentCoordinate = path.remove();
        Coordinate nextCoordinate = path.remove();
        System.out.println("Current Coordinate" + currentCoordinate.toString());
        System.out.println("next Coordinate" + nextCoordinate.toString());
        Coordinate diffCoordinate = coordinateDifference(nextCoordinate, currentCoordinate);
        System.out.println("coordinate difference" + diffCoordinate);

        System.out.println("directions = " + direction);
        System.out.println("velocity= " + velocity);
        if (direction == WorldSpatial.Direction.SOUTH){
            diffCoordinate.y = -diffCoordinate.y;
            diffCoordinate.x = -diffCoordinate.x;
        } else if (direction == WorldSpatial.Direction.EAST){
            int tempx = diffCoordinate.x;
            diffCoordinate.x = -diffCoordinate.y;
            diffCoordinate.y = tempx;
        } else if (direction == WorldSpatial.Direction.WEST){
            int tempx = diffCoordinate.x;
            diffCoordinate.x = diffCoordinate.y;
            diffCoordinate.y = tempx;
        }
        System.out.println("new coordinate diff" + diffCoordinate);
        if (diffCoordinate.x == 0){
            if(diffCoordinate.y == -1){
                System.out.println("Accelerate backwards");
                return Command.ACCELERATE_BACKWARDS;
            } else if (diffCoordinate.y == 1) {
                System.out.println("Accelerate forward");
                return Command.ACCELERATE_FORWARD;
            } else {
                return Command.DO_NOTHING;
            }
        } else if (diffCoordinate.x == 1){
            System.out.println("Turn Right");
            return Command.TURN_RIGHT;
        } else {
            System.out.println("Turn Left");
            return Command.TURN_LEFT;
        }
    }

    private Coordinate coordinateDifference(Coordinate coordinateA, Coordinate coordinateB){
        return new Coordinate((coordinateA.x - coordinateB.x), (coordinateA.y - coordinateB.y));
    }
}

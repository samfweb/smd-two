package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;
import java.util.Objects;
import java.util.Queue;

public class CarPathConvertor implements IPathConverter{

    public CarPathConvertor() {
    }

    @Override
    public Command convertNextMove(Coordinate currentPosition, Coordinate nextPosition, WorldSpatial.Direction direction, float velocity) {
        assert currentPosition != null;
        assert nextPosition != null;
        Coordinate deltaPosition = coordinateDifference(currentPosition, nextPosition);
        WorldSpatial.Direction nextDirection = CoordinateUtils.translateVector(deltaPosition);
        System.out.println("Current Direction = " + direction + " Next Direction = " + nextDirection);
        System.out.println("Position Delta = " + deltaPosition);
        return translateDirection(direction, nextDirection, velocity);


    }

    private Command translateDirection(WorldSpatial.Direction currentDirection, WorldSpatial.Direction newDirection, float velocity){
        WorldSpatial.Direction translatedDirection = CoordinateUtils.translateRotation(currentDirection, newDirection);
        switch(translatedDirection){
            case EAST:
                return handleCommand(Command.TURN_RIGHT, velocity);
            case SOUTH:
                return handleCommand(Command.ACCELERATE_BACKWARDS, velocity);
            case WEST:
                return handleCommand(Command.TURN_LEFT, velocity);
            default:
                return handleCommand(Command.DO_NOTHING, velocity);
        }
    }


    private Command handleCommand(Command command, float velocity){
        if (velocity <= 0){
            if (command.equals(Command.TURN_LEFT) || command.equals(Command.TURN_RIGHT)){
                return Command.ACCELERATE_FORWARD;
            }
            if (command.equals(Command.DO_NOTHING)){
                return Command.ACCELERATE_FORWARD;
            }
        }
        return command;
    }

    private Coordinate coordinateDifference(Coordinate coordinateA, Coordinate coordinateB){
        return new Coordinate((coordinateB.x - coordinateA.x), (coordinateB.y - coordinateA.y));
    }

}

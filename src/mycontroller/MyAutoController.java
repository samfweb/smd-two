package mycontroller;

import controller.CarController;
import tiles.ParcelTrap;
import world.Car;

import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class MyAutoController extends CarController {
	private MethodTemplate method;

	private Coordinate targetPosition = null;

	private InternalMap mapConstructor = new InternalMap(mapWidth(), mapHeight());

	private IPathConverter pathConverter = new CarPathConvertor();

	public MyAutoController(Car car) {
		super(car);
		this.method = new ExploreMethod();

	}


	// Coordinate initialGuess;
	// boolean notSouth = true;
	@Override
	public void update() {
		System.out.println("|********************************************************************|");
		// Gets what the car can see
		HashMap<Coordinate, MapTile> currentView = getView();
		//Updates the internal map with car surroundings
		mapConstructor.updateViewedMap(currentView);
		System.out.println(mapConstructor.createDisplayMap(mapConstructor.transformMap()));
		checkMap(mapConstructor);
		Deque<Coordinate> path = method.generatePathing(new Coordinate(getPosition()), targetPosition, mapConstructor);
		Coordinate currentPosition = path.pollLast();
		Coordinate nextPosition = path.pollLast();


		System.out.println("Current Position: " + currentPosition + " Next Position: " + nextPosition);

		Command nextCommand = pathConverter.convertNextMove(currentPosition, nextPosition, getOrientation(), getSpeed());
		System.out.println("METHOD: " + method.toString());

		executeCommand(nextCommand);
		System.out.println(nextCommand);
		System.out.println("|********************************************************************|");



	}

	private boolean checkRequirements() {
		return numParcels() == numParcelsFound();
	}

	// really could use a factory to instantiate these
	private void checkMap(InternalMap internalMap){
		System.out.println(internalMap.discoveredTypes(MapTile.Type.FINISH).size());
		System.out.println();
		if (checkRequirements() && checkTilePaths(internalMap.discoveredTypes(MapTile.Type.FINISH), currentPosition(getPosition()))){
			this.method = new DirectMethod();
		}
		else if (checkTilePaths(internalMap.discoveredTraps("parcel"), currentPosition(getPosition()))){
			this.method = new DirectMethod();
		} else {
			targetPosition = new Coordinate(getPosition());
			this.method = new ExploreMethod();
		}
	}

	private boolean checkTilePaths(List<Coordinate> candidateTiles, Coordinate position){
		if(candidateTiles != null && candidateTiles.size() > 0) {
			for (Coordinate candidate : candidateTiles) {
				if (safePath(new DirectMethod().generatePathing(position, candidate, mapConstructor))) {
					targetPosition = candidate;
					method = new DirectMethod();
					return true;
				}
			}
		}
		return false;
	}

	private Coordinate currentPosition(String position){
		return new Coordinate(position);
	}

	private boolean safePath(Deque<Coordinate> path){
		return (path != null && path.size() > 1);
	}

	private void executeCommand(Command command){
		switch (command){
			case ACCELERATE_BACKWARDS:
				applyReverseAcceleration();
				break;
			case ACCELERATE_FORWARD:
				applyForwardAcceleration();
				break;
			case DO_NOTHING:
				break;
			case TURN_LEFT:
				turnLeft();
			case BRAKE:
				applyBrake();
				break;
			case TURN_RIGHT:
				turnRight();
				break;
		}
	}
}

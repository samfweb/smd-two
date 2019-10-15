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
		// always begin with the explore method
		this.method = new ExploreMethod();

	}

	@Override
	public void update() {
		// Gets what the car can see
		HashMap<Coordinate, MapTile> currentView = getView();
		//Updates the internal map with car surroundings
		mapConstructor.updateViewedMap(currentView);
		// checks our current method & updates our strategy if fitting
		checkMap(mapConstructor);
		// Generates a path to the next position
		Deque<Coordinate> path = method.generatePathing(new Coordinate(getPosition()), targetPosition, mapConstructor);
		Coordinate currentPosition = path.pollLast();
		Coordinate nextPosition = path.pollLast();
		// Converts a move into "Car Language"
		Command nextCommand = pathConverter.convertNextMove(currentPosition, nextPosition, getOrientation(), getSpeed());
		// Executes the car command specified
		executeCommand(nextCommand);
	}

	/**
	 * Check if we have met the package requirements
	 * @return boolean: True -> No more parcels to collect, False -> More parcels to collect
	 */
	private boolean checkRequirements() {
		return numParcels() == numParcelsFound();
	}

	// really could use a factory to instantiate these
	private void checkMap(InternalMap internalMap){
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


	/**
	 * Helper Constructor for Coordinate(current position)
	 *
	 * @param position current position as given by system interface
	 * @return Coordinate representation of current position
	 */
	private Coordinate currentPosition(String position){
		return new Coordinate(position);
	}

	/**
	 * Safety Check the Path Presented
	 * @param path deque of coordinates to be tested
	 * @return True: safe path, False: unsafe path
	 */
	private boolean safePath(Deque<Coordinate> path){
		return (path != null && path.size() > 1);
	}

	/**
	 * Converts Command Types into Method Calls for the Car Movement
	 * @param command current command to call
	 */
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

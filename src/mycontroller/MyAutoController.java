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

	private MethodDecider methodDecider = new MethodDecider();

	/**
	 * Constructor
	 * @param car the car to control
	 */
	public MyAutoController(Car car) {
		super(car);
		// always begin with the explore method
		this.method = methodDecider.explore();

	}

	/**
	 * Updates the car
	 */
	@Override
	public void update() {
		// Gets what the car can see
		HashMap<Coordinate, MapTile> currentView = getView();
		//Updates the internal map with car surroundings
		mapConstructor.updateViewedMap(currentView);
		// checks our current method & updates our strategy if fitting
		this.method = methodDecider.decideMethod(mapConstructor, this);
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

	/**
	 * Sets the target position
	 * @param target the target position
	 */
	public void setTarget(Coordinate target) {
		this.targetPosition = target;
	}
}

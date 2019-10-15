package mycontroller;

import com.badlogic.gdx.math.Interpolation;
import controller.CarController;
import world.Car;
import java.util.HashMap;
import java.util.Queue;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class MyAutoController extends CarController{
		// How many minimum units the wall is away from the player.
		private int wallSensitivity = 1;

		private MethodTemplate method;
		
		private boolean isFollowingWall = false; // This is set to true when the car starts sticking to a wall.
		
		// Car Speed to move at
		private final int CAR_MAX_SPEED = 1;

		private InternalMap mapConstructor = new InternalMap(mapWidth(), mapHeight());

		private IPathConverter carPathConverter = new CarPathConvertor();
		
		public MyAutoController(Car car) {
			super(car);
			this.method = new ExploreMethod();
		}


		// Coordinate initialGuess;
		// boolean notSouth = true;
		@Override
		public void update() {
			// Gets what the car can see
			HashMap<Coordinate, MapTile> currentView = getView();

			//Updates the internal map with car surroundings
			mapConstructor.updateViewedMap(currentView);
			System.out.println(mapConstructor.createDisplayMap(mapConstructor.transformMap()));


			Queue<Coordinate> path = method.generatePathing(new Coordinate(getPosition()), new Coordinate(getPosition()), mapConstructor);
			path.forEach((e) -> System.out.println(e.toString()));
//			System.out.println("current position = " + path.peek());
			Command nextCommand = carPathConverter.convertNextMove(path, getOrientation(), getSpeed());

			if (getSpeed() == 0){
				applyForwardAcceleration();
			} else {
				switch (nextCommand) {
					case ACCELERATE_FORWARD:
						if(getSpeed()==0){
							applyForwardAcceleration();
						} else if ( getSpeed() == -1){
							applyForwardAcceleration();
							applyForwardAcceleration();
						} else
						applyForwardAcceleration();
					case ACCELERATE_BACKWARDS:
						if(getSpeed()==1){
							applyReverseAcceleration();
							applyReverseAcceleration();
						} else if (getSpeed()==0){
							applyReverseAcceleration();
						}
					case TURN_RIGHT:
						turnRight();
					case TURN_LEFT:
						turnLeft();
				}
			}
		}

		/**
		 * Check if you have a wall in front of you!
		 * @param orientation the orientation we are in based on WorldSpatial
		 * @param currentView what the car can currently see
		 * @return
		 */
		private boolean checkWallAhead(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView){
			switch(orientation){
			case EAST:
				return checkEast(currentView);
			case NORTH:
				return checkNorth(currentView);
			case SOUTH:
				return checkSouth(currentView);
			case WEST:
				return checkWest(currentView);
			default:
				return false;
			}
		}
		
		/**
		 * Check if the wall is on your left hand side given your orientation
		 * @param orientation
		 * @param currentView
		 * @return
		 */
		private boolean checkFollowingWall(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
			
			switch(orientation){
			case EAST:
				return checkNorth(currentView);
			case NORTH:
				return checkWest(currentView);
			case SOUTH:
				return checkEast(currentView);
			case WEST:
				return checkSouth(currentView);
			default:
				return false;
			}	
		}
		
		/**
		 * Method below just iterates through the list and check in the correct coordinates.
		 * i.e. Given your current position is 10,10
		 * checkEast will check up to wallSensitivity amount of tiles to the right.
		 * checkWest will check up to wallSensitivity amount of tiles to the left.
		 * checkNorth will check up to wallSensitivity amount of tiles to the top.
		 * checkSouth will check up to wallSensitivity amount of tiles below.
		 */
		public boolean checkEast(HashMap<Coordinate, MapTile> currentView){
			// Check tiles to my right
			Coordinate currentPosition = new Coordinate(getPosition());
			for(int i = 0; i <= wallSensitivity; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x+i, currentPosition.y));
				if(tile.isType(MapTile.Type.WALL)){
					return true;
				}
			}
			return false;
		}
		
		public boolean checkWest(HashMap<Coordinate,MapTile> currentView){
			// Check tiles to my left
			Coordinate currentPosition = new Coordinate(getPosition());
			for(int i = 0; i <= wallSensitivity; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x-i, currentPosition.y));
				if(tile.isType(MapTile.Type.WALL)){
					return true;
				}
			}
			return false;
		}
		
		public boolean checkNorth(HashMap<Coordinate,MapTile> currentView){
			// Check tiles to towards the top
			Coordinate currentPosition = new Coordinate(getPosition());
			for(int i = 0; i <= wallSensitivity; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y+i));
				if(tile.isType(MapTile.Type.WALL)){
					return true;
				}
			}
			return false;
		}
		
		public boolean checkSouth(HashMap<Coordinate,MapTile> currentView){
			// Check tiles towards the bottom
			Coordinate currentPosition = new Coordinate(getPosition());
			for(int i = 0; i <= wallSensitivity; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y-i));
				if(tile.isType(MapTile.Type.WALL)){
					return true;
				}
			}
			return false;
		}
		
	}

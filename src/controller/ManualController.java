package controller;

import java.util.Set;
import com.badlogic.gdx.Input;
import world.Car;
import swen30006.driving.Simulation;
import mycontroller.InternalMap;

// Manual Controls for the car
public class ManualController extends CarController {

	private InternalMap map;

	public ManualController(Car car){
		super(car);
		this.map = new InternalMap(mapWidth(), mapHeight());
	}


	public void update(){
		Set<Integer> parcels = Simulation.getParcels();
		Simulation.resetParcels();

		map.updateViewedMap(this.getView());
		System.out.println(map.createDisplayMap(map.transformMap()));


        for (int k : parcels){
		     switch (k){
		        case Input.Keys.B:
		        	applyBrake();
		            break;
		        case Input.Keys.UP:
		        	applyForwardAcceleration();
		            break;
		        case Input.Keys.DOWN:
		        	applyReverseAcceleration();
		        	break;
		        case Input.Keys.LEFT:
		        	turnLeft();
		        	break;
		        case Input.Keys.RIGHT:
		        	turnRight();
		        	break;
		        default:
		      }
		  }
	}
}

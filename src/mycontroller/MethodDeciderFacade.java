package mycontroller;

import controller.CarController;
import tiles.MapTile;
import utilities.Coordinate;

import java.util.Deque;
import java.util.List;

/**
 * Responsible for deciding which pathing method to use
 */
public class MethodDeciderFacade {

    private MethodFactory methodFactory;

    /**
     * Constructor
     */
    public MethodDeciderFacade() {
        this.methodFactory = MethodFactory.getInstance();
    }

    /**
     * @return an ExploreMethod
     */
    public MethodTemplate explore() {
        return methodFactory.makeExploreMethod();
    }

    /**
     * Decides which pathing method to use
     * @param internalMap the map to decide the method from
     */
    public MethodTemplate decideMethod(InternalMap internalMap, MyAutoController controller){
        Coordinate currentPosition = currentPosition(controller.getPosition());
        if (checkRequirements(controller)
                && checkTilePaths(internalMap.discoveredTypes(MapTile.Type.FINISH), internalMap, currentPosition, controller)){
            return methodFactory.makeDirectMethod();
        }
        else if (checkTilePaths(internalMap.discoveredTraps("parcel"), internalMap, currentPosition, controller)){
            return methodFactory.makeDirectMethod();
        } else {
            controller.setTarget(currentPosition);
            return methodFactory.makeExploreMethod();
        }
    }

    /**
     * Check if we have met the package requirements
     * @return boolean: True -> No more parcels to collect, False -> More parcels to collect
     */
    private boolean checkRequirements(MyAutoController controller) {
        return controller.numParcels() == controller.numParcelsFound();
    }

    /**
     * Checks if a tile path exists
     * @param candidateTiles the candidate tiles
     * @param internalMap the internal map
     * @param position the position
     * @return true if it exists, otherwise false
     */
    private boolean checkTilePaths(List<Coordinate> candidateTiles, InternalMap internalMap, Coordinate position, MyAutoController controller){
        if(candidateTiles != null && candidateTiles.size() > 0) {
            for (Coordinate candidate : candidateTiles) {
                MethodTemplate candidateMethod = methodFactory.makeDirectMethod();
                if (safePath(candidateMethod.generatePathing(position, candidate, internalMap))) {
                    controller.setTarget(candidate);
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
}

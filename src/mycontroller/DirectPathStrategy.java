package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;

/**
 * Directed pathing strategy for the car
 * Navigates from current point to given map coordinates using internal map.
 */
public class DirectPathStrategy implements IPathingStrategy {

    private Coordinate destination;

    //Marks coordinate as visited for exploreMaze
    private ArrayList<Coordinate> visited = new ArrayList<Coordinate>();

    //Path for car to follow
    private ArrayList<Coordinate> path = new ArrayList<Coordinate>();

    /**
     *
     * @param currentLoc car's current location
     * @param internalMap internal map constructed from traversal
     * @return the next coordinate to travel to
     */
    @Override
    public Coordinate nextCoordinate(Coordinate currentLoc, InternalMap internalMap) {

        // At destination
        if (currentLoc.equals(destination)) {
            return currentLoc; //no change
        }

        //Not at destination
        else {
            //Calculates set of commands if empty or diverged from path
            if (path.isEmpty() || !path.contains(currentLoc)) {
                path = calculatePath(currentLoc, internalMap);
            }
            /**
             * what behaviour should we have if there is no path?
             */
            if (path.isEmpty()) {
                return currentLoc; //no change
            }

            //Finds current index in path array
            int currentIndex = path.indexOf(currentLoc);

            //Returns the next coordinate
            return path.get(currentIndex + 1);
        }
    }

    /**
     * Sets a new destination
     * @param destination destination for car to travel to
     */
    public void setDestination(Coordinate destination) {
        this.destination = destination;
        //Clears visited and path
        this.visited.clear();
        this.path.clear();
    }

    /**
     * Calculates a path from current location to destination using internal map.
     * If no path exists, returns an empty array.
     */
    public ArrayList<Coordinate> calculatePath(Coordinate currentLoc, InternalMap internalMap) {
        ArrayList<Coordinate> path = new ArrayList<Coordinate>();
        //If a path exists, return it
        if (exploreMaze(currentLoc, internalMap, path)) {
            return path;
        }
        //Otherwise return empty path
        path.clear();
        return path;
    }

    /**
     * Explores the maze recursively
     * @param loc current location in path
     * @param internalMap
     * @param path
     * @return
     */
    public boolean exploreMaze(Coordinate loc, InternalMap internalMap, ArrayList<Coordinate> path) {

        //base case, hits a wall or already visited path
        // ******** MAY NEED TO FIX THIS BASE CASE TO IMPROVE EFFICIENCY
        if (internalMap.isWall(loc) || visited.contains(loc)) {
            return false;
        }

        //Add coordinate to path and visited list
        path.add(loc);
        visited.add(loc);

        //We've found a path
        if (loc.equals(destination)) {
            return true;
        }
        //Check path in each direction
        for (WorldSpatial.Direction direction : WorldSpatial.Direction.values()) {
            Coordinate nextCoordinate = getNextCoordinate(loc, direction);
            if (exploreMaze(nextCoordinate, internalMap, path)) {
                return true;
            }
        }
        //No match, removes last added coordinate
        path.remove(path.size() - 1);
        return false;

    }

    /**
     * Calculates the next location to check
     * @param loc
     * @param direction
     * @return
     */
    public Coordinate getNextCoordinate(Coordinate loc, WorldSpatial.Direction direction) {
        switch(direction) {
            case NORTH:
                return new Coordinate(loc.x, loc.y+1);
            case SOUTH:
                return new Coordinate(loc.x, loc.y-1);
            case EAST:
                return new Coordinate(loc.x+1, loc.y);
            case WEST:
                return new Coordinate(loc.x-1, loc.y);
        }
        return loc;
    }
}
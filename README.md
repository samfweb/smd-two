
# SWEN30006 SMD Project 2
## Car Auto Controller
Provided a car and a set of pregenerated maze, we were tasked with developing a pathing algorithm.

The car had no preexisting knowledge of the map, and had to collect a predetermined number of parcels before making its way to an initially undiscovered exit point. 

### To See Our Work
All code written by us is in `src/mycontroller`, and named according to the design pattern used.

All other code was provided as part of the assignment.

### Usage / Display
Open the project in your favourite Java editor/IDE.

You may need to add some of the jars provided in lib.
If using IntelliJ, you can do this through File -> Project Structure -> Modules -> Dependencies

Generate the map and start the car's exploration by running the main method located in `src/swen30006/driving/desktop/DesktopLauncher,java`.

### Custom Settings
You can easily modify basic properties in  `assets/Driving.Properties`.

#### Map Choice
Choose from one of the three pregenerated maps by uncommenting one of the three `Map=`lines.

Uncommenting multiple maps will result in an exception.

#### Controller Choice
Choose a controler by uncommenting one `Controller=` line.

Originally bundled with this assignment was a choice of two controllers.
`controller.ManualController` lets you use keyboard navigation to control the car.
`controller.SimpleAutoController` attempts to solve a map naively by following nearby walls.

The smarter `controller.MyAutoController` we developed is the default selected.

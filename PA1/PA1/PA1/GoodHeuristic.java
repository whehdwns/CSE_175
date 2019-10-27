//
// GoodHeuristic
//
// This class extends the Heuristic class, providing a reasonable
// implementation of the heuristic function method. The provided "good"
// heuristic function is admissible.
//
// YOUR NAME -- TODAY'S DATE
//
// IMPORT ANY PACKAGES THAT YOU NEED.
import java.util.*;
import java.lang.Math;

public class GoodHeuristic extends Heuristic {
	
	public StreetMap map;
	public double velocity;
	
	public GoodHeuristic(StreetMap map, Location destination) {
		this.map = map;
		//velocity gives fastest route
		this.velocity = velocity();	
		super.setDestination(destination);
	}
	// it returns good heuristic value (time)
	@Override
	public double heuristicValue(Node thisNode) {
		//initialize hvalue
		double hvalue = 0.0;
		// time = Distance / velocity
		hvalue = timecost(thisNode.loc, destination)/velocity;
		//return time
		return (hvalue);
	}
	
	//this will find minimum cost route which is admissible heuristic function
	public double velocity() {
		double temp, velocity = 0.0;
		// Calculates the fastest route between any two locations 
		for (Location location : map.locations) {
			for (Road road : location.roads) {
				temp = timecost(road.fromLocation, road.toLocation)/road.cost;
				//if velocity is smaller than velocity between two locations, then replace it
				if (velocity < temp) {
					velocity = temp;
				}
			}
		}
		return velocity;
	}
	//longitude and latitude values are Cartesian coordinates measured in miles
	//calculate the distance between two locations
	public double timecost(Location loc1, Location loc2) {
		double x, y = 0.0;
		//latitude difference between two locations
		x = loc1.latitude-loc2.latitude;
		//longtitude difference between two locations
		y = loc1.longitude - loc2.longitude;
		return Math.hypot(x, y);
	}

}


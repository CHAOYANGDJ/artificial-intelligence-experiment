package BAB;
import java.util.ArrayList;
import java.util.HashSet;

public class Solver {
	double[][] distances;
	double best_cost;
	int[] best_path;

	
	public Solver(ArrayList<City> cities) {
		distances = new double[cities.size()][cities.size()];
		for(int i = 0; i < cities.size(); i++) {
			for(int ii = 0; ii < cities.size(); ii++)
				distances[i][ii] = cities.get(i).distance(cities.get(ii));
		}
	}


	public int[] calculate() {
		HashSet<Integer> location_set = new HashSet<Integer>(distances.length);
		for(int i = 0; i < distances.length; i++)
			location_set.add(i);

		best_cost = findGreedyCost(0, location_set, distances);

		int[] active_set = new int[distances.length];
		for(int i = 0; i < active_set.length; i++)
			active_set[i] = i;

		Node root = new Node(null, 0, distances, active_set, 0);
		traverse(root);

		return best_path;
	}

	
	public double getCost() {
		return best_cost;
	}

	private double findGreedyCost(int i, HashSet<Integer> location_set, double[][] distances) {
		if(location_set.isEmpty())
			return distances[0][i];

		location_set.remove(i);

		double lowest = Double.MAX_VALUE;
		int closest = 0;
		for(int location : location_set) {
			double cost = distances[i][location];
			if(cost < lowest) {
				lowest = cost;
				closest = location;
			}
		}

		return lowest + findGreedyCost(closest, location_set, distances);
	}

	
	private void traverse(Node parent) {
		Node[] children = parent.generateChildren();

		for(Node child : children) {
			if(child.isTerminal()) {
				double cost = child.getPathCost();
				if(cost < best_cost) {
					best_cost = cost;
					best_path = child.getPath();
				}
			}
			else if(child.getLowerBound() <= best_cost) {
				traverse(child);
			}
		}
	}
}

package BAB;

public class Node {
	public Node parent;
	private double parent_cost;

	private double[][] distances;
	private int[] active_set;

	public int index;


	public Node(Node parent, double parent_cost, double[][] distances, int[] active_set, int index) {
		this.parent = parent;
		this.parent_cost = parent_cost;
		this.distances = distances;
		this.active_set = active_set;
		this.index = index;
	}

	
	public boolean isTerminal() {
		return active_set.length == 1;
	}

	public Node[] generateChildren() {
		Node[] children = new Node[active_set.length - 1];

		int[] new_set = new int[active_set.length - 1];
		int i = 0;
		for(int location : active_set) {
			if(location == index)
				continue;

			new_set[i] = location;
			i++;
		}

		for(int j = 0; j < children.length; j++)
			children[j] = new Node(this, distances[index][new_set[j]], distances, new_set, new_set[j]);

		return children;
	}

	
	public int[] getPath() {
		int depth = distances.length - active_set.length + 1;
		int[] path = new int[depth];
		getPathIndex(path, depth - 1);
		return path;
	}

	
	public void getPathIndex(int[] path, int i) {
		path[i] = index;
		if(parent != null)
			parent.getPathIndex(path, i - 1);
	}

	
	public double getLowerBound() {
		double value = 0;

		if(active_set.length == 2)
			return getPathCost() + distances[active_set[0]][active_set[1]];

		for(int location : active_set) {
			double low1 = Double.MAX_VALUE;
			double low2 = Double.MAX_VALUE;

			for(int other: active_set) {
				if(other == location)
					continue;

				double cost = distances[location][other];
				if(cost < low1) {
					low2 = low1;
					low1 = cost;
				}
				else if(cost < low2) {
					low2 = cost;
				}
			}

			value += low1 + low2;
		}

		return getParentCost() + value / 2;
	}

	public double getPathCost() {
		return distances[0][index] + getParentCost();
	}

	
	public double getParentCost() {
		if(parent == null)
			return 0;

		return parent_cost + parent.getParentCost();
	}
}

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class localSearch {

	char[][] grid;
	int size;
	int treeCount;
	int height;
	ArrayList<Point> treeLocations = new ArrayList<Point>();
	ArrayList<Point> friendLocations = new ArrayList<Point>();
	ArrayList<Integer> visited = new ArrayList<Integer>();
	HashMap<Integer, Integer> constraintCount = new HashMap<Integer, Integer>();
	ArrayList<Point> output = new ArrayList<Point>();

	public localSearch(int size, int treeCount, ArrayList<Point> treeLocations) {
		this.treeCount = treeCount;
		this.size = size;
		this.treeLocations = treeLocations;
		grid = new char[size][size];

		for (int i = 0; i < treeLocations.size(); i++) {
			Point location = treeLocations.get(i);
			grid[location.x][location.y] = 't';
		}
		setVariables(grid);

	}

	public void search() {
		int[] maxConstraint = new int[2];
		for (int i = 0; i < size; i++) {
			getConstraints(i);
		}
		maxConstraint = getMax();
		while (maxConstraint[1] != 0) {
			while (maxConstraint[1] != 0) {
				if (maxConstraint[1] > constraintCount.get(maxConstraint[0])) {

					break;
				}
				if (backTrack()) {
					if ((maxConstraint[0] + 1) < size) {
						changeLocation(friendLocations.get(maxConstraint[0] + 1), maxConstraint[0] + 1);
					} else {
						changeLocation(friendLocations.get(maxConstraint[0] - 1), maxConstraint[0] - 1);
					}
					break;
				}
				changeLocation(friendLocations.get(maxConstraint[0]), maxConstraint[0]);
				getConstraints(maxConstraint[0]);

			}

			visited.clear();
			maxConstraint = getMax();

		}
		printGrid();
		printOutput();
	}

	public void getConstraints(int index) {
		Point p = friendLocations.get(index);
		constraintCount.put(index, 0);
		Point check;
		int count = 0;
		check = new Point(p.x, p.y - 1);
		if (!checkDirection(p, check)) {
			count++;
		}

		check = new Point(p.x + 1, p.y - 1);
		if (!checkDirection(p, check)) {
			count++;
		}
		check = new Point(p.x + 1, p.y + 1);
		if (!checkDirection(p, check)) {
			count++;
		}

		check = new Point(p.x, p.y + 1);
		if (!checkDirection(p, check)) {
			count++;
		}

		check = new Point(p.x - 1, p.y + 1);
		if (!checkDirection(p, check)) {
			count++;
		}

		check = new Point(p.x - 1, p.y - 1);
		if (!checkDirection(p, check)) {
			// changeLocation(p,index);
			// search(index);
			count++;
		}

		constraintCount.put(index, count);

	}

	public void setVariables(char[][] grid) {
		Random generator = new Random();
		for (int i = 0; i < size; i++) {
			int y = generator.nextInt(8);
			if (grid[y][i] == 't') {

				while (grid[y][i] == 't') {
					y = generator.nextInt(8);
					if (grid[y][i] != 't') {
						grid[y][i] = 'f';
						friendLocations.add(new Point(y, i));
						break;
					}
				}
			} else {
				grid[y][i] = 'f';
				friendLocations.add(new Point(y, i));
			}

		}

	}

	public void changeLocation(Point p, int index) {
		Random generator = new Random();
		int y = generator.nextInt(8);
		visited.add(y);
		grid[p.x][p.y] = 'x';
		if (!visited.contains(y) && grid[y][p.y] != 't') {
			grid[y][p.y] = 'f';
			friendLocations.remove(index);
			friendLocations.add(index, new Point(y, p.y));
		}
		while (visited.contains(y) || grid[y][p.y] == 't') {
			if (grid[y][p.y] != 't') {
				grid[y][p.y] = 'f';
				visited.add(y);
				friendLocations.remove(index);
				friendLocations.add(index, new Point(y, p.y));
				break;
			}

			y = generator.nextInt(8);
		}
	}

	public int[] getMax() {
		int[] returnValue = new int[2];
		int tmp = 0;
		int max = 0;
		for (int i = 0; i < constraintCount.size(); i++) {
			getConstraints(i);
			tmp = constraintCount.get(i);
			if (tmp >= max) {
				max = tmp;
				returnValue[1] = max;
				returnValue[0] = i;

			}
		}
		return returnValue;

	}

	public boolean checkDirection(Point start, Point end) {

		if (end.x < 0 || end.x >= size || end.y < 0 || end.y >= size) {
			return true;
		}
		int xcoord = end.x;
		int ycoord = end.y;

		boolean value;
		if (start.x - end.x > 0) {
			xcoord = end.x - 1;
		} else if (start.x - end.x < 0) {
			xcoord = end.x + 1;
		}
		if (start.y - end.y > 0) {
			ycoord = end.y - 1;
		} else if (start.y - end.y < 0) {
			ycoord = end.y + 1;
		}

		if (grid[end.x][end.y] == 't') {
			return true;
		} else if (grid[end.x][end.y] != 'f') {

			return checkDirection(end, new Point(xcoord, ycoord));

		} else {
			return false;
		}

	}

	public boolean backTrack() {
		for (int i = 0; i < size; i++) {
			if (!visited.contains(i)) {
				return false;
			}
		}
		return true;
	}

	public void printGrid() {
		for (int k = 0; k < size; k++) {
			System.out.println(" ");
			for (int l = 0; l < size; l++) {
				System.out.print(grid[k][l]);
			}
		}
		System.out.println(" ");
	}
	public void printOutput(){
		
		for (int k = 0; k < size; k++) {
			for (int l = 0; l < size; l++) {
				if(grid[l][k] == 'f'){
					System.out.println(l + " " + k);
				}
			}
		}
		
	}
}
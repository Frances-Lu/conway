public class Game {
	public int rows;
	public int columns; 
	public Cell[][] grid;
	
	public Game(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		grid = new Cell[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				grid[i][j] = new Cell();
			}
		}
	}	

	public void cellStatus( int x, int y, boolean alive) {
		grid[x][y].status(alive);
	}

	public void newGeneration() {
		Cell [][] newGen = new Cell[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				newGen[i][j] = new Cell();
				int neighbors = countNeighbors(i, j);

				if (grid[i][j].isAlive()) {
					if (neighbors < 2 || neighbors > 3) { //accounts for under and over population
						newGen[i][j].status(false);
					}
					else {
						newGen[i][j].status(true);
					}
				}
				else {
					if (neighbors == 3) {
						newGen[i][j].status(true);
					}
				}
			}
		}
		grid = newGen;
	}

	private int countNeighbors(int x, int y) {
		int count = 0;

		//checks all the surrounding cells nearby it
		for (int i=-1; i<= 1; i++) {
			for (int j=-1; j<=1; j++) {
				if (i == 0 && j==0) {
					//this means that we are checking the cell we are on
					continue;
				}
				int positionX = x + i;
				int positionY = y + j;

				if (positionX < rows && positionX >= 0 && positionY < columns && positionY >=0) {
					if (grid[positionX][positionY].isAlive()) {
						count++;
					}
				}
			}
		}
		return count;
	}
}
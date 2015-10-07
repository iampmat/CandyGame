import java.util.Random;


public class GameBoard {
	Cell[][] board;
	Random rand;
	int max = 99;
	int min = 1;
	public GameBoard() {
		board = new Cell[6][6];
		fillboard();
	}
	
	public void fillboard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				int randomNum = rand.nextInt((max - min) + 1) + min;
				board[i][j] = new Cell(randomNum, j, i);
			}
		}
	}
	
	public void play(char val, int x, int y) {
		board[y][x].color = val;
	}
	
	public boolean isFull() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].color == 'B') {
					return false;
				}
			}
		}
		return true;
	}
}

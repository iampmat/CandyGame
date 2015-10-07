import java.util.List;


public class Minimax {
	GameBoard board;
	public Minimax(GameBoard gb) {
		board = gb;
	}
	
	public Node Minimax(String color, int depth) {
		List<Cell> nextMoves = board.nextMoves();
		
		if (nextMoves.isEmpty() || depth == 0) {
			
		}
		
		
	}
}

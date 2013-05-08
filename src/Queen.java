import java.util.HashSet;


public class Queen extends Piece {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5078411154146479462L;

	public Queen(int x, int y, boolean isWhite) {
		super(x, y, isWhite);
		
		if (isWhite){
			setImage("Icons/WQ.png");
		} else {
			setImage("Icons/BQ.png");
		}
	}

	@Override
	public HashSet<Square> getMoves() {
		return moves;
	}

	@Override
	public HashSet<Square> calcMoves(ChessBoard board) {
		moves.clear();
		for(int i = 1; checkMove(board.getSquare(x+i, y)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x-i, y)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x, y+i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x, y-i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x+i, y+i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x+i, y-i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x-i, y+i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x-i, y-i)); i++) {}
		return moves;
	}
}

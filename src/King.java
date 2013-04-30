import java.util.HashSet;


public class King extends Piece{
	public King(int x, int y, boolean isWhite) {
		super(x, y, isWhite);
		
	}

	@Override
	public HashSet<Square> getMoves() {
		return moves;
	}

	@Override
	public HashSet<Square> calcMoves(ChessBoard board) {
		moves = new HashSet<Square>();
		checkMove(board.getSquare(x+1,y));
		checkMove(board.getSquare(x-1,y));
		checkMove(board.getSquare(x,y+1));
		checkMove(board.getSquare(x,y-1));
		checkMove(board.getSquare(x+1,y+1));
		checkMove(board.getSquare(x-1,y-1));
		checkMove(board.getSquare(x+1,y-1));
		checkMove(board.getSquare(x-1,y+1));
		return moves;
	}

}

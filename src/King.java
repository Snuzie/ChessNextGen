import java.util.HashSet;


public class King extends Piece{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6479794776026917913L;

	public King(int x, int y, boolean isWhite) {
		super(x, y, isWhite);
		
		if(isWhite){
			setImage("Icons/WKi.png");
		} else {
			setImage("Icons/BKi.png");
		}
	}

	@Override
	public HashSet<Square> getMoves() {
		return moves;
	}

	@Override
	public HashSet<Square> calcMoves(ChessBoard board) {
		moves.clear();
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

import java.util.HashSet;


public class Knight extends Piece {
	/**
	 * 
	 */
	private static final long serialVersionUID = 821727645988419378L;

	public Knight(int x, int y, boolean isWhite){
		super(x, y, isWhite);
		
		if(isWhite){
			setImage("Icons/WK.png");
		} else {
			setImage("Icons/BK.png");
		}
	}

	@Override
	public HashSet<Square> getMoves() {
		return moves;
	}

	@Override
	public HashSet<Square> calcMoves(ChessBoard board) {
		moves.clear();
		checkMove(board.getSquare(x+2,y+1));
		checkMove(board.getSquare(x+1,y+2));
		checkMove(board.getSquare(x-2,y-1));
		checkMove(board.getSquare(x-1,y-2));
		checkMove(board.getSquare(x-2,y+1));
		checkMove(board.getSquare(x-1,y+2));
		checkMove(board.getSquare(x+2,y-1));
		checkMove(board.getSquare(x+1,y-2));
		return moves;
	}
}

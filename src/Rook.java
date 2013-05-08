import java.util.HashSet;


public class Rook extends Piece {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4794900229705246262L;

	public Rook(int x, int y, boolean isWhite) {
		super(x, y, isWhite);
		
		if(isWhite) {
			setImage("Icons/WR.png");
		} else {
			setImage("Icons/BR.png");
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
		return moves;
	}
}

import java.util.HashSet;


public class Knight extends Piece {
	public Knight(int x, int y, boolean isWhite){
		super(x, y, isWhite);
	}

	@Override
	public HashSet<Square> getMoves() {
		return moves;
	}

	@Override
	public HashSet<Square> calcMoves(ChessBoard board) {
		moves = new HashSet<Square>();
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

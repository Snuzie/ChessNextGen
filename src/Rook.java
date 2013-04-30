import java.util.HashSet;


public class Rook extends Piece {
	public Rook(int x, int y, boolean isWhite) {
		super(x, y, isWhite);
	}

	@Override
	public HashSet<Square> getMoves() {
		return moves;
	}

	@Override
	public HashSet<Square> calcMoves(ChessBoard board) {
		moves = new HashSet<Square>();
		for(int i = 1; checkMove(board.getSquare(x+i, y)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x-i, y)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x, y+i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x, y-i)); i++) {}
		return moves;
	}
}

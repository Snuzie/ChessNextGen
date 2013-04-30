import java.util.HashSet;

public class Pawn extends Piece {
	private int movement;

	public Pawn(int x, int y, boolean isWhite) {
		super(x, y, isWhite);
		if (isWhite) {
			movement = -1;
		} else {
			movement = 1;
		}
	}

	@Override
	public HashSet<Square> calcMoves(ChessBoard board) {
		moves.clear();
		checkMove(board.getSquare(x, y + movement));
		checkAttackMove(board.getSquare(x - 1, y + movement));
		checkAttackMove(board.getSquare(x + 1, y + movement));
		return moves;
	}

	@Override
	public HashSet<Square> getMoves() {
		return moves;
	}

	@Override
	protected boolean checkMove(Square square) {
		if (square.isBlocked()) {
			return false;
		}
		moves.add(square);
		return true;
	}

	private boolean checkAttackMove(Square square) {
		if (square.isBlocked()) {
			if (isWhite != square.getPiece().isWhite) {
				moves.add(square);
				return false;
			}
			return false;
		}
		return false;
	}
}

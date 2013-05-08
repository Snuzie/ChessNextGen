import java.util.HashSet;


public class King extends Piece{
	private HashSet<Piece> possibleCheckers = new HashSet<Piece>();
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
		if (!(numMoves > 0) && !isChecked(board)) {
			if (!board.getSquare(x+1, y).isBlocked() &&
					!board.getSquare(x+2, y).isBlocked() &&
					board.getSquare(x+3, y).isBlocked() &&
					!(board.getSquare(x+3, y).getPiece().numMoves > 0)){
				moves.add(board.getSquare(x+2, y));
			}
			if (!board.getSquare(x-1, y).isBlocked() &&
					!board.getSquare(x-2, y).isBlocked() &&
					!board.getSquare(x-3, y).isBlocked() &&
					board.getSquare(x-4, y).isBlocked() &&
					!(board.getSquare(x-4, y).getPiece().numMoves > 0)){ 
				moves.add(board.getSquare(x-2, y));
			}
		}
		return moves;
	}
	
	public boolean isChecked(ChessBoard board){
		for (int i = 1; isPossibleChecker(board.getSquare(x+i, y+i)); i++) {}
		for (int i = 1; isPossibleChecker(board.getSquare(x-i, y-i)); i++) {}
		for (int i = 1; isPossibleChecker(board.getSquare(x+i, y-i)); i++) {}
		for (int i = 1; isPossibleChecker(board.getSquare(x-i, y+i)); i++) {}
		for (int i = 1; isPossibleChecker(board.getSquare(x, y+i)); i++) {}
		for (int i = 1; isPossibleChecker(board.getSquare(x, y-i)); i++) {}
		for (int i = 1; isPossibleChecker(board.getSquare(x+i, y)); i++) {}
		for (int i = 1; isPossibleChecker(board.getSquare(x-i, y)); i++) {}
		isPossibleChecker(board.getSquare(x+2,y+1));
		isPossibleChecker(board.getSquare(x+1,y+2));
		isPossibleChecker(board.getSquare(x-2,y-1));
		isPossibleChecker(board.getSquare(x-1,y-2));
		isPossibleChecker(board.getSquare(x-2,y+1));
		isPossibleChecker(board.getSquare(x-1,y+2));
		isPossibleChecker(board.getSquare(x+2,y-1));
		isPossibleChecker(board.getSquare(x+1,y-2));
		
		for (Piece piece : possibleCheckers){
			if (piece.calcMoves(board).contains(board.getSquare(x, y)))
				possibleCheckers.clear();
				return true;
		}
		possibleCheckers.clear();
		return false;
	}
	
	private boolean isPossibleChecker(Square square) {
		if (square == null) return false;
		if (square.isBlocked()){
			if (isWhite == square.getPiece().isWhite)
				return false;
			possibleCheckers.add(square.getPiece());
			return false;
		}
		return true;
	}

}

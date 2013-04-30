import java.util.HashSet;


public class Bishop extends Piece{
	public Bishop(int x, int y, boolean isWhite){
		super(x,y,isWhite);
		
		if(isWhite){
			setImage("Icons/WB.png");
		} else {
			setImage("Icons/BB.png");
		}
	}

	@Override
	public HashSet<Square> getMoves() {
		return moves;
	}

	@Override
	public HashSet<Square> calcMoves(ChessBoard board) {
		moves.clear();
		for(int i = 1; checkMove(board.getSquare(x+i, y+i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x+i, y-i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x-i, y+i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x-i, y-i)); i++) {}
		return moves;
	}
}

import java.util.HashSet;
import javax.swing.ImageIcon;

public abstract class Piece {
	protected int x, y;
	protected boolean isWhite;
	protected HashSet<Square> moves;
	protected ImageIcon icon;

	public Piece(int x, int y, boolean isWhite){
		this.x = x;
		this.y = y;
		this.isWhite = isWhite;
	}
	
	public void setIcon(ImageIcon icon) {
		this.icon = icon;  
	}
	public ImageIcon getIcon() {
		return icon;
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract HashSet<Square> getMoves();
	/**
	 * 
	 * @param board
	 * @return
	 */
	public abstract HashSet<Square> calcMoves(ChessBoard board);

	protected boolean checkMove(Square square) {
		if (square == null) return false;
		if (square.isBlocked()){
			if (isWhite != square.getPiece().isWhite) {
				moves.add(square);
				return false;
			}
			return false;
		}
		moves.add(square);
		return true;
	}
}

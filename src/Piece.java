import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public abstract class Piece {
	protected int x, y, numMoves=-1;
	protected boolean isWhite;
	protected HashSet<Square> moves;
	protected BufferedImage img;
	protected ImageIcon icon;

	public Piece(int x, int y, boolean isWhite){
		this.x = x;
		this.y = y;
		this.isWhite = isWhite;
		moves = new HashSet<Square>();
	}
	
	public void setIcon(ImageIcon icon) {
		this.icon = icon;  
	}
	public ImageIcon getIcon() {
		return icon;
	}
	
	/**
	 * Set the location of this piece
	 * @param row
	 * @param col
	 */
	public void setLocation(int row, int col) {
		this.y = col;
		this.x = row;
		this.numMoves++;
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
	
	protected void setImage(String url){
		try {
			img = ImageIO.read(getClass().getResource(url));
			Image scaledImg = img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH );
			setIcon(new ImageIcon(scaledImg));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

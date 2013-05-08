import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public abstract class Piece implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3577514704115456525L;
	protected int x, y;
	protected boolean isWhite;
	protected HashSet<Square> moves;
	transient BufferedImage img;
	protected ImageIcon icon;

	public Piece(int x, int y, boolean isWhite) {
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
	
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(1); // how many images are serialized?
        ImageIO.write(img, "png", out); // png is lossless
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();        
        img = ImageIO.read(in);
    }

}

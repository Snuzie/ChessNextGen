import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class Square extends JButton {

	private static final long serialVersionUID = 1L;
	private Piece piece;
	private Location location;
	

	public Square(String text, Color color, Location location, ActionListener al) {
		this.location = location;
		super.setPreferredSize(new Dimension(50, 50));
		super.setOpaque(true);
		super.setForeground(Color.BLACK);
		super.setBackground(color);
		setBorderColor(Color.BLACK);
		super.addActionListener(al);
	}
	
	public Piece removePiece() {
		Piece p = piece;
		setIcon(null);
		piece = null;
		return p;
	}

	public void setPiece(Piece p) {
		setIcon(p.getIcon());
		piece = p;
		int row = location.getRow();
		int col = location.getColumn();
		piece.setLocation(row, col);
		super.setIcon(p.getIcon());
	}

	public Piece getPiece() {
		return piece;
	}

	public boolean isBlocked() {
		if (piece == null)
			return false;
		return true;
	}
	
	public Location getPos() {
		return location;
	}
	
	/**
	 * Change the background color of the square
	 */
	public void changeColor(Color color) {
		super.setBackground(color);
	}
	
	/**
	 * Mark the square
	 */
	public void mark() {
		setBorderColor(Color.RED);
	}
	
	/**
	 * Unmark the square
	 */
	public void unmark() {
		setBorderColor(Color.BLACK);
	}

	/**
	 * Change the color of the square's border
	 * @param color
	 */
	private void setBorderColor(Color color) {
		Border line = new LineBorder(color);
		Border margin = new EmptyBorder(1, 1, 1, 1);
		Border compound = new CompoundBorder(line, margin);
		super.setBorder(compound);
	}
}

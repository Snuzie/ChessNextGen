
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PiecesLog extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PiecesLog() {
		super.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		super.setPreferredSize(new Dimension(40, 100));
	}

	/**
	 * Add a taken piece to pieces log
	 * 
	 * @param piece
	 *            The piece to add to the log
	 * 
	 */
	public void addTakenPiece(Piece piece) {
		
		JLabel picIcon = new JLabel(piece.getIcon());
		super.add(picIcon);
	}
	
	/**
	 * Clear the log
	 */
	public void clear() {
		this.removeAll();
		this.updateUI();
	}
}

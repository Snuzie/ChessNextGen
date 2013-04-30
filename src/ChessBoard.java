import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.*;

/**
 * 
 * @author Edward Grippe
 * 
 */
public class ChessBoard {
	private JFrame frame;
	private Square[][] squares; // En matris med alla rutor på brädet
	private Square markedSquare;

	public ChessBoard() {
		makeBoard();
		squares[3][3].setPiece(new Queen(3, 3, true));
	}

	private void makeBoard() {
		frame = new JFrame("ChessWindow");
		squares = new Square[8][8];

		GridLayout layout = new GridLayout(8, 8, 0, 0);

		Container contentPane = frame.getContentPane();
		contentPane.setLayout(layout);

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				
				ActionListener al = makeActionListener();
				
				Square enruta = null;
				String text = row + ":" + col;
				Location location = new Location(row, col);
				if ((row + col) % 2 == 0) {
					enruta = new Square(text, Color.GRAY, location, al);
				} else {
					enruta = new Square(text, Color.WHITE, location, al);
				}
				contentPane.add(enruta);
				squares[row][col] = enruta;
			}
		}

		frame.pack();
		frame.setVisible(true);
	}

	public Square getMarkedSquare() {
		return markedSquare;
	}

	public Square[][] getSquares() {
		return squares;
	}

	public Square getSquare(int row, int col) {
		if (row > 7 || col > 7 || row < 0 || col < 0) {
			return null; // Index out of bounds
		}
		
		return squares[row][col];
	}

	private ActionListener makeActionListener() {
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Square square = (Square) evt.getSource();
				Piece selectedPiece = null;
				
				if(square.isBlocked()) {
					selectedPiece = square.getPiece();
					HashSet<Square> possibilities = selectedPiece.calcMoves(ChessBoard.this);
					for (Square s:possibilities) {
						s.setBackground(Color.BLUE);
					}
				}
				square.setBackground(Color.RED);
			}
		};
		return al;
	}

}

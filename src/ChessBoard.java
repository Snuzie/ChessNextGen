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
	private Square[][] squares; // En matris med alla rutor pŒ brŠdet
	private Square markedSquare;
	private boolean lastMoveWhite = false;
	private Log log;

	public ChessBoard() {
		makeBoard();
		//newGame();
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

	private void makeBoard() {
		frame = new JFrame("ChessWindow");
		makeMenuBar(frame);
		log = new Log();
		squares = new Square[8][8];

		GridLayout boardLayout = new GridLayout(8, 8, 0, 0);
		GridLayout layout = new GridLayout(1, 2, 10, 10);

		Container contentPane = frame.getContentPane();
		JPanel chessBoard = new JPanel(boardLayout);
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
				chessBoard.add(enruta);
				squares[row][col] = enruta;
			}
		}
		contentPane.add(chessBoard);
		contentPane.add(log);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Create a menu bar and attach it to a frame.
	 * 
	 * @param frame
	 *            The frame the menu bar will be added to.
	 */
	private void makeMenuBar(JFrame frame) {

		// Add a menu bar
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);

		// Add menus to the menu bar
		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);

		JMenu helpMenu = new JMenu("Help");
		menubar.add(helpMenu);

		// Add menu items to the menus
		JMenuItem openItem = new JMenuItem("New Game");
		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		fileMenu.add(openItem);
	}

	/**
	 * Create a eventListener. Used by all square objects on the board
	 */
	private ActionListener makeActionListener() {
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				Square clickedSquare = (Square) evt.getSource();

				if (markedSquare == null) {
					// There's no marked square on the board
					if (clickedSquare.isBlocked()) {
						Piece p = clickedSquare.getPiece();
						if (p.isWhite != lastMoveWhite) {
							markSquare(clickedSquare);
						}
					}
				} else if (markedSquare != clickedSquare) {
					// There's already a marked square on the board
					Piece piece = markedSquare.getPiece();
					HashSet<Square> moves = piece.calcMoves(ChessBoard.this);
					if (moves.contains(clickedSquare)) {
						// The move's OK
						log.addMove(markedSquare, clickedSquare);
						markedSquare.removePiece();
						unmarkSquare();
						clickedSquare.setPiece(piece);
						
						lastMoveWhite = !lastMoveWhite;
					} else {
						// The move's not OK
						unmarkSquare();
					}
				} else {
					// The same square's been clicked twice
					unmarkSquare();
				}

			}
		};
		return al;
	}

	/**
	 * Start an new game.
	 */
	private void newGame() {
		clearBoard();
		for (int i = 0; i < 8; i++) {
			squares[i][1].setPiece(new Pawn(i, 1, false));
			squares[i][6].setPiece(new Pawn(i, 6, true));
		}
		squares[0][0].setPiece(new Rook(0, 0, false));
		squares[1][0].setPiece(new Knight(1, 0, false));
		squares[2][0].setPiece(new Bishop(2, 0, false));
		squares[3][0].setPiece(new King(3, 0, false));
		squares[4][0].setPiece(new Queen(4, 0, false));
		squares[5][0].setPiece(new Bishop(5, 0, false));
		squares[6][0].setPiece(new Knight(6, 0, false));
		squares[7][0].setPiece(new Rook(7, 0, false));

		squares[0][7].setPiece(new Rook(0, 7, true));
		squares[1][7].setPiece(new Knight(1, 7, true));
		squares[2][7].setPiece(new Bishop(2, 7, true));
		squares[3][7].setPiece(new King(3, 7, true));
		squares[4][7].setPiece(new Queen(4, 7, true));
		squares[5][7].setPiece(new Bishop(5, 7, true));
		squares[6][7].setPiece(new Knight(6, 7, true));
		squares[7][7].setPiece(new Rook(7, 7, true));
	}

	/**
	 * Clear the board of all pieces.
	 */
	private void clearBoard() {
		for (Square[] row : squares) {
			for (Square s : row) {
				if (s.isBlocked()) {
					s.removePiece();
				}
			}
		}
	}

	private void markSquare(Square square) {
		square.mark();
		markedSquare = square;
	}

	/**
	 * Unmark the marked square if there's one.
	 */
	private void unmarkSquare() {
		if (markedSquare == null) {
			return;
		}
		markedSquare.unmark();
		markedSquare = null;
	}

	/**
	 * Unmark a given square.
	 */
	private void unmarkSquare(Square s) throws NullPointerException {
		if (s == null) {
			throw new NullPointerException();
		}
		s.unmark();
	}

}

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	private boolean lastMoveWhite = false;
	private Log log;
	private PiecesLog piecesLog;
	private ArrayList<Piece> takenPieces;

	public ChessBoard() {
		makeBoard();
		newGame();
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
		frame = new JFrame("Chess Leet!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Program exits when window closes

		makeMenuBar(frame);
		piecesLog = new PiecesLog();
		log = new Log();

		squares = new Square[8][8];
		takenPieces = new ArrayList<Piece>();

		Container contentPane = frame.getContentPane();

		BoxLayout layout = new BoxLayout(contentPane, BoxLayout.LINE_AXIS);
		GridLayout boardLayout = new GridLayout(8, 8, 0, 0);

		contentPane.setLayout(layout);
		JPanel chessBoard = new JPanel(boardLayout);

		squares = new Square[8][8];

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
		contentPane.add(piecesLog);
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

		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(quitItem);
	}

	/**
	 * Create a eventListener. Used by all square objects on the board
	 */
	private ActionListener makeActionListener() {
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				Square clickedSquare = (Square) evt.getSource();
				Piece clickedPiece;
				if (clickedSquare.isBlocked()) {
					clickedPiece = clickedSquare.getPiece();
				}

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
						move(markedSquare, clickedSquare);
					} else if (clickedSquare.isBlocked()) {
						Piece p = clickedSquare.getPiece();
						if (p.isWhite != lastMoveWhite) {
							unmarkSquare();
							markSquare(clickedSquare);
						}
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
	 * Move a piece from one square to another.
	 * 
	 * @param from
	 * @param to
	 */
	private void move(Square from, Square to) {

		log.addMove(from, to);
		if (to.isBlocked()) {
			Piece taken = to.removePiece();
			piecesLog.addTakenPiece(taken);
			takenPieces.add(taken);
			if (King.class.isInstance(taken)) {
				log.gameOver();

				Object[] options = { "New Game", "Quit" };

				int n = JOptionPane.showOptionDialog(new JFrame(),
						"Would you like to start a new game?", "New game?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, // do not use a
															// custom Icon
						options, // the titles of buttons
						options[0]); // default button title
				if (n == 0) {
					newGame();
					unmarkSquare();
					return;
				} else {
					System.exit(0);
				}
			}
		}
		to.setPiece(from.removePiece());
		if (Pawn.class.isInstance(to.getPiece())) {
			Pawn pawn = (Pawn) to.getPiece();
			if (pawn.isPromoteable())
				pawn.promote(this);
		}
		unmarkSquare();
		lastMoveWhite = !lastMoveWhite;
	}

	/**
	 * Start an new game.
	 */
	private void newGame() {
		clearBoard();
		log.clearLog();
		piecesLog.clear();
		lastMoveWhite = false;
		for (int i = 0; i < 8; i++) {
			squares[i][1].setPiece(new Pawn(i, 1, true));
			squares[i][6].setPiece(new Pawn(i, 6, false));
		}
		squares[0][0].setPiece(new Rook(0, 0, true));
		squares[1][0].setPiece(new Knight(1, 0, true));
		squares[2][0].setPiece(new Bishop(2, 0, true));
		squares[3][0].setPiece(new Queen(3, 0, true));
		squares[4][0].setPiece(new King(4, 0, true));
		squares[5][0].setPiece(new Bishop(5, 0, true));
		squares[6][0].setPiece(new Knight(6, 0, true));
		squares[7][0].setPiece(new Rook(7, 0, true));

		squares[0][7].setPiece(new Rook(0, 7, false));
		squares[1][7].setPiece(new Knight(1, 7, false));
		squares[2][7].setPiece(new Bishop(2, 7, false));
		squares[3][7].setPiece(new Queen(3, 7, false));
		squares[4][7].setPiece(new King(4, 7, false));
		squares[5][7].setPiece(new Bishop(5, 7, false));
		squares[6][7].setPiece(new Knight(6, 7, false));
		squares[7][7].setPiece(new Rook(7, 7, false));
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

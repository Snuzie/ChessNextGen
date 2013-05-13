import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 
 * @author Edward Grippe
 * 
 */
public class ChessBoard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2893850641232344190L;
	private JFrame frame;
	private Square[][] squares; // En matris med alla rutor pŒ brŠdet
	private JPanel board;
	private Square markedSquare;
	private boolean lastMoveWhite = false;
	private Log logWhite;
	private Log logBlack;
	private PiecesLog piecesLog;
	private ArrayList<Piece> takenPieces;
	private IOReader ioReader;
	private King kingW, kingB;
	private JLabel turnLabel;
	private JPanel turnPanel = new JPanel();

	public ChessBoard() {
		makeBoard();
		newGame();
		ioReader = new IOReader(board, squares, lastMoveWhite, logWhite,
				takenPieces);
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
		frame.setResizable(false);

		makeMenuBar(frame);
		piecesLog = new PiecesLog();
		logWhite = new Log(Color.white, Color.black);
		logBlack = new Log(Color.black, Color.white);

		squares = new Square[8][8];
		takenPieces = new ArrayList<Piece>();

		Container contentPane = frame.getContentPane();

		BoxLayout layout = new BoxLayout(contentPane, BoxLayout.LINE_AXIS);
		GridLayout boardLayout = new GridLayout(9, 9, 0, 0);
		GridLayout miscLayout = new GridLayout(10, 1, 10, 10);

		contentPane.setLayout(layout);
		board = new JPanel(boardLayout);
		JPanel misc = new JPanel(miscLayout);

		squares = new Square[8][8];
		String[] letters = new String[] { "A", "B", "C", "D", "E", "F", "G",
				"H" };
		board.add(new JLabel(""));
		for (int i = 1; i <= 8; i++) {
			board.add(new JLabel("      " + i));
		}

		for (int row = 0; row < 8; row++) {
			board.add(new JLabel("      " + letters[row]));
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
				board.add(enruta);
				squares[row][col] = enruta;
			}
		}
		
		turnLabel = new JLabel("White's turn");
		turnLabel.setForeground(Color.black);
		turnPanel.add(turnLabel);
		turnPanel.setBackground(Color.white);
		
		// Create the "give up"-button
		JButton giveUp = new JButton("Give up");
		giveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "New Game", "Quit" };
				String winner = "";
				if (lastMoveWhite) {
					winner = "White";
				} else {
					winner = "Black";
				}

				int n = JOptionPane
						.showOptionDialog(
								new JFrame(),
								"Congratulations! "
										+ winner
										+ " won the game!\nWould you like to start a new game?",
								"New game?", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]); // default button title
				if (n == 0) {
					newGame();
				} else {
					System.exit(0);
				}
			}
		});

		misc.add(turnPanel);
		misc.add(giveUp);
		
		contentPane.add(misc);
		contentPane.add(piecesLog);
		contentPane.add(board);
		contentPane.add(logWhite);
		contentPane.add(logBlack);
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
		// Add option under file tostart new game.
		JMenuItem newGameItem = new JMenuItem("New Game");
		newGameItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		fileMenu.add(newGameItem);

		// Adds option under file where you can load a saved game.
		JMenuItem openItem = new JMenuItem("Load Game");
		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] arg = ioReader.getBoard();
				// ChessBoard.this.board = (JPanel) arg[0];
				// ChessBoard.this.squares = (Square[][]) arg[1];
				fillBoard((Square[][]) arg[1], (Boolean) arg[2], (Log) arg[3],
						(ArrayList<Piece>) arg[4]);
				System.out.println("LOADED!");
			}
		});
		fileMenu.add(openItem);

		// Addsan option under file where you can save the current game.
		JMenuItem saveItem = new JMenuItem("Save Game");
		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ioReader.saveBoard();
				} catch (Exception ex) {
				}

				System.out.println("Saved!");
			}
		});
		fileMenu.add(saveItem);

		// Adds an option under file that quits the game.
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(quitItem);

		// Adds an option under help where you can see the petterns in which the
		// pieces can move.
		JMenuItem movesItem = new JMenuItem("How pieces move");
		movesItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame movesFrame = new JFrame("Moves");
				movesFrame.setResizable(false);

				JPanel pane = new JPanel();
				GridLayout layout = new GridLayout(2, 3, 10, 10);
				pane.setLayout(layout);
				BufferedImage img;
				try {
					img = ImageIO.read(getClass().getResource(
							"Icons/MovesOfAKing.png"));
					pane.add(new JLabel(new ImageIcon(img)));
				} catch (IOException exception) {
					System.out.println(exception.getMessage());
					exception.printStackTrace();
				}
				try {
					img = ImageIO.read(getClass().getResource(
							"Icons/MovesOfAQueen.png"));
					pane.add(new JLabel(new ImageIcon(img)));
				} catch (IOException exception) {
					System.out.println(exception.getMessage());
					exception.printStackTrace();
				}
				try {
					img = ImageIO.read(getClass().getResource(
							"Icons/MovesOfABishop.png"));
					pane.add(new JLabel(new ImageIcon(img)));
				} catch (IOException exception) {
					System.out.println(exception.getMessage());
					exception.printStackTrace();
				}
				try {
					img = ImageIO.read(getClass().getResource(
							"Icons/MovesOfAKnight.png"));
					pane.add(new JLabel(new ImageIcon(img)));
				} catch (IOException exception) {
					System.out.println(exception.getMessage());
					exception.printStackTrace();
				}
				try {
					img = ImageIO.read(getClass().getResource(
							"Icons/MovesOfARook.png"));
					pane.add(new JLabel(new ImageIcon(img)));
				} catch (IOException exception) {
					System.out.println(exception.getMessage());
					exception.printStackTrace();
				}
				try {
					img = ImageIO.read(getClass().getResource(
							"Icons/MovesOfAPawn.png"));
					pane.add(new JLabel(new ImageIcon(img)));
				} catch (IOException exception) {
					System.out.println(exception.getMessage());
					exception.printStackTrace();
				}
				movesFrame.add(pane);
				movesFrame.pack();
				movesFrame.repaint();
				movesFrame.setVisible(true);
			}
		});
		helpMenu.add(movesItem);

		// Adds an option under help with short info about the game.
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(),
						"This is a chess game made by\n"
								+ "Edward Grippe and Mattias Lönnerberg.\n\n"
								+ "Version 0.1");
			}
		});
		helpMenu.add(aboutItem);
	}

	private void fillBoard(Square[][] squares, boolean lastMoveWhite, Log log,
			ArrayList<Piece> takenPieces) {
		Container contentPane = frame.getContentPane();
		try {
			this.piecesLog.clear();

			this.piecesLog.updateUI();
			contentPane.remove(this.piecesLog);
			contentPane.remove(this.board);
			contentPane.remove(this.logWhite);
			markedSquare = null;

			this.takenPieces = takenPieces;
			this.logWhite = log;
			this.lastMoveWhite = lastMoveWhite;

			BoxLayout layout = new BoxLayout(contentPane, BoxLayout.LINE_AXIS);
			GridLayout boardLayout = new GridLayout(9, 9, 0, 0);

			// contentPane.setLayout(layout);
			board = new JPanel(boardLayout);

			this.squares = new Square[8][8];
			Square[][] oldSquares = squares;

			for (Piece p : takenPieces) {
				this.piecesLog.addTakenPiece(p);
			}
			this.piecesLog.updateUI();

			String[] letters = new String[] { "A", "B", "C", "D", "E", "F",
					"G", "H" };
			board.add(new JLabel(""));
			for (int i = 1; i <= 8; i++) {
				board.add(new JLabel("      " + i));
			}

			for (int row = 0; row < 8; row++) {
				board.add(new JLabel("      " + letters[row]));
				for (int col = 0; col < 8; col++) {

					ActionListener al = makeActionListener();

					Square enruta = null;
					Piece p = null;
					String text = row + ":" + col;
					Location location = new Location(row, col);
					if ((row + col) % 2 == 0) {
						enruta = new Square(text, Color.GRAY, location, al);
						if (squares[row][col].isBlocked()) {
							p = oldSquares[row][col].getPiece();
							enruta.setPiece(p);
						}
					} else {
						enruta = new Square(text, Color.WHITE, location, al);
						if (squares[row][col].isBlocked()) {
							p = oldSquares[row][col].getPiece();
							enruta.setPiece(p);
						}
					}
					board.add(enruta);
					this.squares[row][col] = enruta;
				}
			}
			contentPane.add(this.piecesLog);
			contentPane.add(board);
			contentPane.add(log);

			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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

						move(markedSquare, clickedSquare);
					} else if (clickedSquare.isBlocked()) {
						Piece p = clickedSquare.getPiece();

						if (p.isWhite != lastMoveWhite) {
							unmarkSquare();
							markSquare(clickedSquare);
						}
					} else {
						// An invalid move has been made
						Toolkit.getDefaultToolkit().beep();
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
	 * Move a piece from one square to another.
	 * 
	 * @param from
	 * @param to
	 */
	private void move(Square from, Square to) {

		// Check if move is castling.
		if (King.class.isInstance(from.getPiece())) {
			// Short castling
			if (to.getPos().getRow() - from.getPos().getRow() == 2) {
				move(squares[7][from.getPos().getColumn()], squares[5][from
						.getPos().getColumn()]);
				lastMoveWhite = !lastMoveWhite;
				// Long castling
			} else if (to.getPos().getRow() - from.getPos().getRow() == -2) {
				move(squares[0][from.getPos().getColumn()], squares[3][from
						.getPos().getColumn()]);
				lastMoveWhite = !lastMoveWhite;
			}
		}
		if (to.isBlocked()) {
			Piece taken = to.removePiece();
			piecesLog.addTakenPiece(taken);
			takenPieces.add(taken);
			/*
			 * if (King.class.isInstance(taken)) { log.addMove(from, to);
			 * log.gameOver();
			 * 
			 * Object[] options = { "New Game", "Quit" };
			 * 
			 * int n = JOptionPane.showOptionDialog(new JFrame(),
			 * "Would you like to start a new game?", "New game?",
			 * JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, //
			 * do not use a // custom Icon options, // the titles of buttons
			 * options[0]); // default button title if (n == 0) { newGame();
			 * unmarkSquare(); return; } else { System.exit(0); } return; }/*
			 * Försök till att fixa buggen som gör att pjäsen blir tagen när man
			 * tar en pjäs men då blir i schack trots att ens drag återställs
			 * och det är ens tur igen. if (kingW.isChecked(this) &&
			 * !lastMoveWhite){ JOptionPane.showMessageDialog(new JFrame(),
			 * "Invalid move. King is checked.");
			 * piecesLog.removeTakenPiece(taken); takenPieces.remove(taken);
			 * return; } if (kingB.isChecked(this) && lastMoveWhite){
			 * JOptionPane.showMessageDialog(new JFrame(),
			 * "Invalid move. King is checked.");
			 * piecesLog.removeTakenPiece(taken); takenPieces.remove(taken);
			 * return; }
			 */
		}

		to.setPiece(from.removePiece());
		if (kingW.isChecked(this) && !lastMoveWhite) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Invalid move. King is checked.");
			from.setPiece(to.removePiece());
			return;
		}
		if (kingB.isChecked(this) && lastMoveWhite) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Invalid move. King is checked.");
			from.setPiece(to.removePiece());
			return;
		}
		if (kingW.isChecked(this) && lastMoveWhite) {
			JOptionPane.showMessageDialog(new JFrame(), "Check");
		}
		if (kingB.isChecked(this) && !lastMoveWhite) {
			JOptionPane.showMessageDialog(new JFrame(), "Check");
		}

		// Check if pawn and if promoteable.
		if (Pawn.class.isInstance(to.getPiece())) {
			Pawn pawn = (Pawn) to.getPiece();
			if (pawn.isPromoteable())
				pawn.promote(this);
		}
		unmarkSquare();
		lastMoveWhite = !lastMoveWhite;
		if (lastMoveWhite) {
			logWhite.addMove(from, to);
			turnLabel.setForeground(Color.white);
			turnLabel.setText("Black's turn");
			turnPanel.setBackground(Color.black);
		} else {
			logBlack.addMove(from, to);
			turnLabel.setForeground(Color.black);
			turnLabel.setText("White's turn");
			turnPanel.setBackground(Color.white);
		}
	}

	/**
	 * Start an new game.
	 */
	private void newGame() {
		clearBoard();
		logWhite.clearLog();
		logBlack.clearLog();
		piecesLog.clear();
		lastMoveWhite = false;
		for (int i = 0; i < 8; i++) {
			squares[i][1].setPiece(new Pawn(i, 1, true));
			squares[i][6].setPiece(new Pawn(i, 6, false));
		}
		kingW = new King(4, 0, true);
		squares[0][0].setPiece(new Rook(0, 0, true));
		squares[1][0].setPiece(new Knight(1, 0, true));
		squares[2][0].setPiece(new Bishop(2, 0, true));
		squares[3][0].setPiece(new Queen(3, 0, true));
		squares[4][0].setPiece(kingW);
		squares[5][0].setPiece(new Bishop(5, 0, true));
		squares[6][0].setPiece(new Knight(6, 0, true));
		squares[7][0].setPiece(new Rook(7, 0, true));

		kingB = new King(4, 7, false);
		squares[0][7].setPiece(new Rook(0, 7, false));
		squares[1][7].setPiece(new Knight(1, 7, false));
		squares[2][7].setPiece(new Bishop(2, 7, false));
		squares[3][7].setPiece(new Queen(3, 7, false));
		squares[4][7].setPiece(kingB);
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

	// /**
	// * Unmark a given square.
	// */
	// private void unmarkSquare(Square s) throws NullPointerException {
	// if (s == null) {
	// throw new NullPointerException();
	// }
	// s.unmark();
	// }

}

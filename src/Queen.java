import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Queen extends Piece {
	public Queen(int x, int y, boolean isWhite) {
		super(x, y, isWhite);
		BufferedImage img;
		try {
			img = ImageIO.read(getClass().getResource("/enikon.png"));
			Image scaledImg = img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH );
			super.setIcon(new ImageIcon(scaledImg));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		

	}

	@Override
	public HashSet<Square> getMoves() {
		return moves;
	}

	@Override
	public HashSet<Square> calcMoves(ChessBoard board) {
		moves = new HashSet<Square>();
		for(int i = 1; checkMove(board.getSquare(x+i, y)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x-i, y)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x, y+i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x, y-i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x+i, y+i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x+i, y-i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x-i, y+i)); i++) {}
		for(int i = 1; checkMove(board.getSquare(x-i, y-i)); i++) {}
		return moves;
	}
}

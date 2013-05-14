import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class IOReader {
	private File file;
	private Square[][] squares;
	private ChessBoard board;
	private Boolean lastMoveWhite;
	private Log whiteLog;
	private Log blackLog;
	private ArrayList<Piece> takenPieces;
	private static JFileChooser fileChooser = new JFileChooser(
			System.getProperty("user.dir"));

	public IOReader(ChessBoard board) {
		this.board = board;
//		squares = board.getSquares();
//		lastMoveWhite = board.getLastMoveWhite();
//		whiteLog = getWhiteLog();
//		blackLog = getBlackLog;
//		takenPieces = getTakenPieces;
	}

	public Object[] getBoard() {
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return null; // cancelled
		}
		File selectedFile = fileChooser.getSelectedFile();
		return loadFile(selectedFile);
	}

	public boolean saveBoard() {
		int returnVal = fileChooser.showSaveDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return false; // cancelled
		}
		File selectedFile = fileChooser.getSelectedFile();
		return store(selectedFile);
	}

	public boolean store(File f) {
		try {
			squares = board.getSquares();
			lastMoveWhite = board.getLastMoveWhite();
			whiteLog = board.getWhiteLog();
			blackLog = board.getBlackLog();
			takenPieces = board.getTakenPieces();
			
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(squares);
//			System.out.println(lastMoveWhite);
			oos.writeObject(lastMoveWhite);
			oos.writeObject(whiteLog);
			oos.writeObject(blackLog);
			oos.writeObject(takenPieces);
			oos.close();
			fos.close();
		} catch (IOException ex) {
			return false;
		}
		
		return true;
	}

	private Object[] loadFile(File f) {
		Object[] arg = new Object[5];
		try {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			arg[0] = (Square[][]) ois.readObject();
			arg[1] = (Boolean) ois.readObject();
			arg[2] = (Log) ois.readObject();
			arg[3] = (Log) ois.readObject();
			arg[4] = (ArrayList<Piece>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
		return arg;
	}
}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class IOReader {
	private File file;
	private Square[][] squares;
	private JPanel board;
//	private ChessBoard chessboard;
	private static JFileChooser fileChooser = new JFileChooser(
			System.getProperty("user.dir"));

	public IOReader(JPanel board, Square[][] squares) {
		this.board = board;
		this.squares = squares;
//		this.chessboard = board;
		File file = new File("savedgame.ser");
		store(file);
	}

	public Object[] getBoard() {
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return null; // cancelled
		}
		File selectedFile = fileChooser.getSelectedFile();
		return loadFile(selectedFile);
	}

	public boolean store(File f) {
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(board);
			oos.writeObject(squares);
			oos.close();
			fos.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage() + "\n");
			ex.printStackTrace();
			return false;
		}
		System.out.println("Chessboard saved!");
		return true;
	}

	public Object[] loadFile(File f) {
		Object[] arg = new Object[2];
		try {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			arg[0] = (JPanel) ois.readObject();
			arg[1] = (Square[][]) ois.readObject();
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

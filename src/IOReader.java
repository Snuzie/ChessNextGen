import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

public class IOReader {
	private File file;
	private Square[][] board;
	private static JFileChooser fileChooser = new JFileChooser(
			System.getProperty("user.dir"));

	public IOReader(Square[][] board) {
		this.board = board;
		File file = new File("savedgame.ser");
		store(file);
	}

	public boolean getBoard() {
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return false; // cancelled
		}
		File selectedFile = fileChooser.getSelectedFile();
		return loadFile(selectedFile);
	}

	public boolean store(File f) {
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(board);
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

	public boolean loadFile(File f) {
		try {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			board = (Square[][]) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
}

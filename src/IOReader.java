import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOReader {
	private File file;
	private Square[][] board;

	public IOReader(Square[][] board) {
		this.board = board;
		File file = new File("savedgame.ser");
		store(file);
	}

	public boolean store(File f) {
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for (Square[] row : board) {
				for (Square s : row) {
					oos.writeObject(s);
				}
 			}
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

	public boolean load(File f) {
		try {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
//			this.games = (HashMap<Game, GameStatus>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException e) {
			return false;
//		} catch (ClassNotFoundException e) {
//			return false;
		}
		return true;
	}
}

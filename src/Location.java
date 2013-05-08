import java.io.Serializable;

/**
 * Holds the location of a square on the chessboard.
 * @author Edward Grippe
 *
 */
public class Location implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6447023600342089559L;
	private int row;
	private int column;
	
	public Location(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
}

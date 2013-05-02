import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class Log extends JPanel {
	private JList list;
	private DefaultListModel listModel;
	private int moveCount = 1;
	private static String letters[] = new String[]
			{"A","B","C","D","E","F","G","H"};
	
	public Log() {
		super(new BorderLayout());
		super.setPreferredSize(new Dimension(200, 200));
        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(5);
        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane);
	}
	
	public void addMove(Square from, Square to) {
		//rows=letters, Cols=numbers
		String s1 = "" + letters[from.getPos().getRow()]
				+ (from.getPos().getColumn()+1);
		String s2 = "" + letters[to.getPos().getRow()]
				+ (to.getPos().getColumn()+1);
		listModel.addElement(moveCount + ". " + s1 + "->" + s2);
		moveCount++;
	}
	
	public void gameOver() {
		listModel.addElement("Game Over!");
	}
	
	public void clearLog(){
		listModel.clear();
	}
}

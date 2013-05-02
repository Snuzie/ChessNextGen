import java.awt.BorderLayout;
import javax.swing.*;

public class Log extends JPanel {
	private JList<String> list;
	private DefaultListModel<String> listModel;
	
	public Log() {
		super(new BorderLayout());
		
        listModel = new DefaultListModel<String>();
        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(5);
        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane);
	}
	
	public void addMove(Square from, Square to) {
		String s1 = ""+from.getPos().getRow()+from.getPos().getColumn();
		String s2 = ""+to.getPos().getRow()+to.getPos().getColumn();
		listModel.addElement(s1 + "->" + s2);
	}
}

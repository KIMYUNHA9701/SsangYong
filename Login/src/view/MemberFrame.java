package view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.MemberDao;
import model.TableModel;

public class MemberFrame extends JFrame{
	String sessionId = null;
	
	public void initTable() {
		TableModel tableModel = new TableModel();
		JTable jTable = new JTable(MemberDao.injectTable(tableModel));
		this.add(new JScrollPane(jTable));
	}

	MemberFrame(String id){
		super("Member");
		sessionId = id;
		initTable();
		this.setBounds(100,100,500,500);
		this.setResizable(false);
		this.setVisible(true);
	}
}

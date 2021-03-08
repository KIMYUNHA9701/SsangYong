package view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.MemberDao;
import model.TableModel;

public class InventoryFrame extends JFrame {
	String id = null;
	
	public void initTable() {
		TableModel tableModel = new TableModel();
		JTable jTable = new JTable(MemberDao.injectInventory(tableModel,id));
		this.add(new JScrollPane(jTable));
	}
	
	//°¡Áî¾Æ
	InventoryFrame(String id){
		super("Inventory");
		this.id = id;
		initTable();
		this.setBounds(100,100,500,500);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}

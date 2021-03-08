package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.MemberDao;

public class StoreFrame extends JFrame implements ActionListener {

	JPanel panel;
	JLabel titleLabel, pointLabel;
	ImageIcon icon;
	String[] strBtns = { "FindDifference - timeReset [20]", "FindDifference", "Pacman - slow [20]", "Pacman - fast [20]",
			"2048 - back [20]", "Inventory" };
	RoundedButton[] buttons = new RoundedButton[strBtns.length];
	private String id;
	private int point;
	ArrayList<Object[]> itemlist = new ArrayList<Object[]>();

	private int slowcnt;
	private int fastcnt;
	
	private int timeResetCnt;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == buttons[0]) { //
			if (point < 20)
				return;
			point -= 20;
			pointLabel.setText("Point : " + point);
			timeResetCnt++;
			MemberDao.updatePoint(id, point);
			MemberDao.updateGameItem(id, 1, "timeReset", timeResetCnt);
			
		} else if (e.getSource() == buttons[1]) {

		} else if (e.getSource() == buttons[2]) {
			if (point < 20)
				return;
			point -= 20;
			pointLabel.setText("Point : " + point);
			slowcnt++;
			System.out.println("slowcnt" + slowcnt);
			MemberDao.updatePoint(id, point);
			MemberDao.updateGameItem(id, 2, "slow", slowcnt);
		} else if (e.getSource() == buttons[3]) {
			if (point < 20)
				return;
			point -= 20;
			pointLabel.setText("Point : " + point);
			fastcnt++;
			MemberDao.updatePoint(id, point);
			MemberDao.updateGameItem(id, 2, "fast", fastcnt);
		} else if (e.getSource() == buttons[4]) {
			if (point < 20)
				return;
			point -= 20;
			pointLabel.setText("Point : " + point);
			MemberDao.updateGameItem(id, 3, "back", (int) MemberDao.selectGameItem(id, 3).get(0)[1] + 1);
			MemberDao.updatePoint(id, point);
		} else if (e.getSource() == buttons[5]) {
			new InventoryFrame(id);
		}
	}

	public void backImage() {
		icon = new ImageIcon("Image\\binigame.jpg");
		Image iconImg = icon.getImage();
		iconImg = iconImg.getScaledInstance(300, 500, Image.SCALE_SMOOTH);
		icon.setImage(iconImg);
	}

	public void labelInit() {
		titleLabel = new JLabel("STORE");
		titleLabel.setBounds(50, 20, 200, 30);
		titleLabel.setFont(new Font("title", Font.BOLD, 30));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		pointLabel = new JLabel("Point : " + point);
		pointLabel.setBounds(50, 60, 200, 30);
		pointLabel.setHorizontalAlignment(JLabel.RIGHT);
		panel.add(titleLabel);
		panel.add(pointLabel);
	}

	public void buttonInit() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new RoundedButton(strBtns[i]);
			buttons[i].addActionListener(this);
			buttons[i].setBounds(50, 120 + (i * 40), 200, 30);
			panel.add(buttons[i]);
		}
	}

	public void init() {
		backImage();
		panel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
			}
		};
		labelInit();
		buttonInit();
		this.add(panel);
	}

	StoreFrame(String id) {
		super("STORE");
		this.id = id;
		point = MemberDao.selectPoint(id);
		itemlist = MemberDao.selectGameItem(id, 2);
		slowcnt = (int) itemlist.get(0)[1];
		fastcnt = (int) itemlist.get(1)[1];

		init();
		this.setBounds(100, 100, 300, 500);
		this.setVisible(true);
		this.setResizable(false);
	}
}

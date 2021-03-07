package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.MemberDao;

public class StoreFrame extends JFrame implements ActionListener {
	JScrollPane jScrollPane;
	ImageIcon icon;
	JLabel label, labelPoint;
	String[] strItem = { "Pacman: slow", "Pacman: fast", "Pacman: resurrect" };
	JLabel[] labelItem = new JLabel[strItem.length];
	RoundedButton[] btn = new RoundedButton[strItem.length];
	private String id;
	private int point;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void backImage() {
		icon = new ImageIcon("Image\\binigame.jpg");
		Image iconImg = icon.getImage();
		iconImg = iconImg.getScaledInstance(300, 500, Image.SCALE_SMOOTH);
		icon.setImage(iconImg);
	}

	public void init() {
		backImage();

		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
			}
		};
		panel.setLayout(new BorderLayout());

		JPanel panel1 = new JPanel(new GridLayout(2,1));
		label = new JLabel("STORE");
		label.setBounds(50, 15, 250, 50);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("title", Font.BOLD, 25));
		labelPoint = new JLabel("point : " + point);
		labelPoint.setBounds(50, 40, 250, 50);
		labelPoint.setHorizontalAlignment(JLabel.RIGHT);
		panel1.add(label);
		panel1.add(labelPoint);

		JPanel panel2 = new JPanel();
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new RoundedButton(strItem[i]);
			btn[i].addActionListener(this);
			btn[i].setBounds(50, 80 + (i * 60), 200, 30);
			panel2.add(btn[i]);
		}

		panel1.setBackground(new Color(255, 0, 0, 0));
		panel2.setBackground(new Color(255, 0, 0, 0));
		// panel 배경 투명

		panel.add("North", panel1);
		panel.add("Center", panel2);
		jScrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setPreferredSize(new Dimension(300, 500));
		this.add(jScrollPane);
	}

	StoreFrame(String id) {
		super("Store");
		this.id = id;
		point = MemberDao.selectPoint(id);
		init();
		this.setBounds(100, 100, 300, 500); 
		this.setLayout(new FlowLayout());
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

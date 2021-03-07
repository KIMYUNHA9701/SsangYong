package view;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import findDifference.GamePlay;
import first.*;
import pacman.Pacman;

public class GameFrame extends JFrame implements ActionListener {

	JPanel panel;
	ImageIcon icon;
	String[] strBtns = { "FindDifference", "Pacman", "2048", "Member Info", "Store", "LogOut" };
	RoundedButton[] buttons = new RoundedButton[strBtns.length];
	String sessionId = null;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == buttons[0]) {
			new GamePlay(sessionId);
		} else if (e.getSource() == buttons[1]) {
			new Pacman(sessionId);
		} else if (e.getSource() == buttons[2]) {
			new Puzzle(sessionId);
		} else if (e.getSource() == buttons[3]) {
			new MemberFrame(sessionId);
		} else if (e.getSource() == buttons[4]) {
			new StoreFrame(sessionId);
		} else if (e.getSource() == buttons[5]) {
			new LoginFrame();
			GameFrame.this.dispose();
		}
	}

	public void backImage() {
		icon = new ImageIcon("Image\\binigame.jpg");
		Image iconImg = icon.getImage();
		iconImg = iconImg.getScaledInstance(300, 500, Image.SCALE_SMOOTH);
		icon.setImage(iconImg);
	}

	public void buttonInit() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new RoundedButton(strBtns[i]);
			buttons[i].addActionListener(this);
			buttons[i].setBounds(50, 80 + (i * 60), 200, 30);
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
		buttonInit();
		this.add(panel);
	}

	GameFrame(String sid) {
		super("GameSelect");
		sessionId = sid;
		init();
		this.setBounds(100, 100, 300, 500);
		this.setVisible(true);
		this.setResizable(false);
	}
}

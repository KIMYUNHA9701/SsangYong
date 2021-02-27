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
import first.*;

public class GameFrame extends JFrame implements ActionListener{
	
	JPanel panel;
	ImageIcon icon;
	JButton[] buttons = new JButton[5];
	String[] strBtns = {"PackMan", "SearchWeird","2048","Member Info","LogOut"};
	String sessionId = null;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == buttons[2]) {
			new Puzzle(sessionId);
		}
		if(e.getSource() == buttons[3]) {
			new MemberFrame(sessionId);
		}
		if(e.getSource() == buttons[4]) {
			new LoginFrame();
			GameFrame.this.dispose(); 
		}
	}
	
	public void backImage() {
		icon = new ImageIcon("Image\\minigame.jpg");
		Image iconImg = icon.getImage();
		iconImg = iconImg.getScaledInstance(300, 500, Image.SCALE_SMOOTH);
		icon.setImage(iconImg);
	}

	public void buttonInit() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(strBtns[i]);
			buttons[i].addActionListener(this);
			buttons[i].setBounds(10,80 + (i * 60),250,30);
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
	
	GameFrame(String sid){
		super("GameSelect");
		sessionId = sid;
		init();
		this.setBounds(100, 100, 300, 500);
		this.setVisible(true);
		this.setResizable(false);
	}
}

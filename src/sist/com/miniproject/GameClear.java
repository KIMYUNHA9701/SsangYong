
package sist.com.miniproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameClear extends JFrame implements ActionListener {
	
	JLabel text, score;
	JPanel jPanel, jPanel2, jPanel3;
	JButton restart, exit;
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == restart) {
			DifferenceDao.getDifferenceDao().dataAllReset();
			this.dispose();
			new Difference();
		}
		if (e.getSource() == exit) {
			System.exit(0);
		}

	}

	public void view() {

		jPanel = new JPanel(new BorderLayout());
		jPanel.add("Center", text = new JLabel("    Game Clear!!"));
		jPanel.add("South", score = new JLabel("                 score: " + DifferenceDao.member.getGamescore()));
		text.setFont(new Font("±¼¸²", Font.BOLD, 100));
		score.setFont(new Font("±¼¸²", Font.BOLD, 50));
		jPanel.setBackground(Color.ORANGE);

		jPanel2 = new JPanel(new FlowLayout());
		jPanel2.add(restart = new JButton("restart"));
		restart.addActionListener(this);
		jPanel2.add(exit = new JButton("exit"));
		exit.addActionListener(this);
		jPanel2.setBackground(Color.ORANGE);

		jPanel3 = new JPanel(new GridLayout(2, 1));
		jPanel3.add(jPanel);
		jPanel3.add(jPanel2);
		jPanel3.setBackground(Color.ORANGE);

		this.setLayout(new BorderLayout());
		this.add("Center", jPanel3);

	}

	public GameClear(){
		
			view();
			this.setTitle("Æ²¸° ±×¸² Ã£±â");
			this.setSize(984, 600);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setResizable(false);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setBackground(Color.ORANGE);
		}


}

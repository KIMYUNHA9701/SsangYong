package findDifference;


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

import model.MemberDao;

public class GameOver extends JFrame implements ActionListener{
	
	
	 JLabel text, score;
	 JPanel jPanel ,jPanel2, jPanel3;
	 JButton restart , exit;
	
	 @Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == restart ) {
			DifferenceDao.getDifferenceDao().dataAllReset();
			this.dispose();
			new Difference();
		}
		if(e.getSource() == exit ) {
			this.dispose();
//			System.exit(0);
		} 
	}

	public void view() {
		resultScore();
		jPanel = new JPanel(new BorderLayout());
		jPanel.add("Center",text = new JLabel("     Game Over"));
		jPanel.add("South",score = new JLabel("                 score: " + DifferenceDao.member.getGamescore() ));
		text.setFont(new Font("±¼¸²",Font.BOLD,100));
		score.setFont(new Font("±¼¸²",Font.BOLD,50)); 
		jPanel.setBackground(Color.ORANGE);
		
		jPanel2 = new JPanel(new FlowLayout());
		jPanel2.add(restart = new JButton("restart"));
		restart.addActionListener(this);
		jPanel2.add(exit = new JButton("exit"));
		exit.addActionListener(this);
		jPanel2.setBackground(Color.ORANGE);
		
		
		jPanel3 = new JPanel(new GridLayout(2,1));
		jPanel3.add(jPanel);
		jPanel3.add(jPanel2);
	    jPanel3.setBackground(Color.ORANGE);
		
		this.setLayout(new BorderLayout());
		this.add("Center",jPanel3);
		
	 }
	 
	 
 
	public GameOver() {
		view();
		this.setTitle("Æ²¸° ±×¸² Ã£±â");
		this.setSize(984, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.ORANGE);
	}

	public void resultScore() {
		String id = DifferenceDao.id;
		int hiscore = Difference.gethiScore();
		int score = DifferenceDao.member.getGamescore();
		if(hiscore < score) hiscore = score;
		MemberDao.updateGameScore(id, hiscore, 1);
		System.out.println("(GameOver) id : " + id);
		System.out.println("(GameOver) hiscore : " + hiscore);
	}
}

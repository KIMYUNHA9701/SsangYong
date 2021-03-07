package findDifference;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.MemberDao;

public class GameClear extends JFrame implements ActionListener {
	
	JLabel text, score;
	JPanel jPanel, jPanel2, jPanel3;
	RoundedButton restart, exit;
	String id;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == restart) {
			DifferenceDao.getDifferenceDao().dataAllReset();
			this.dispose();
			new Difference();
		}
		if (e.getSource() == exit) {
			this.dispose();
//			System.exit(0);
		}

	}

	public void view() {
		resultScore();
		jPanel = new JPanel(new BorderLayout());
		jPanel.add("Center", text = new JLabel("    Game Clear!!"));
		jPanel.add("South", score = new JLabel("                 score: " + DifferenceDao.member.getGamescore()));
		text.setFont(new Font("±¼¸²", Font.BOLD, 100));
		score.setFont(new Font("±¼¸²", Font.BOLD, 50));
		jPanel.setBackground(new Color(255,204,102));

		jPanel2 = new JPanel(new FlowLayout());
		jPanel2.add(restart = new RoundedButton("restart"));
		restart.addActionListener(this);
		jPanel2.add(exit = new RoundedButton("exit"));
		exit.addActionListener(this);
		jPanel2.setBackground(new Color(255,204,102));

		jPanel3 = new JPanel(new GridLayout(2, 1));
		jPanel3.add(jPanel);
		jPanel3.add(jPanel2);
		jPanel3.setBackground(new Color(255,204,102));

		this.setLayout(new BorderLayout());
		this.add("Center", jPanel3);

	}

	public GameClear(String id){
			this.id = id;
			view();
			this.setTitle("Æ²¸° ±×¸² Ã£±â");
			this.setSize(984, 600);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setResizable(false);
			this.setBackground(new Color(255,204,102));
			this.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					dispose();
				
				}
			
			});
		}

	public void resultScore() {
		String id = DifferenceDao.id;
		int hiscore = Difference.gethiScore();
		int score = DifferenceDao.member.getGamescore();
		if(hiscore < score) hiscore = score;
		MemberDao.updateGameScore(id, hiscore, 1);
		System.out.println("(GameClear) hiscore : " + hiscore);
	}
}

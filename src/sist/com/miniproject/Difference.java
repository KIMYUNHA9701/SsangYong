package sist.com.miniproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Difference extends JFrame implements ActionListener {

	static DifferenceMember member;
	static int gamescore = 0;
	
	
    Thread thread;

	JLabel score;
    JProgressBar jpbar;
	JPanel jPanelscore, jPanelbar, jPanelbutton, jPanelimg;
	JButton restart, exit;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
    if(e.getSource()==restart) {
    	jpbar.setValue(0);
    	DifferenceDao.getDifferenceDao().dataAllReset();
    	this.dispose();
    	new Difference();
    }
	if(e.getSource()==exit)	{
		System.exit(0);
	  }
	}

	public void view() {

		Mouse mouse = new Mouse();

		this.setLayout(new BorderLayout());

		this.addMouseListener(mouse);
		this.add("Center", DifferenceDao.currentImg);

		jpbar = new JProgressBar(0, 100);
		jpbar.setStringPainted(true);
		jpbar.setBackground(Color.ORANGE);
		jpbar.setForeground(Color.RED);
		jpbar.setFont(new Font("����", Font.BOLD, 15));
		jpbar.setString("15 seconds");

		jPanelbar = new JPanel(new GridLayout(1, 1));
		jPanelbar.add(jpbar);
		this.add("South", jPanelbar);

		jPanelscore = new JPanel(new BorderLayout());
		jPanelscore.add("West", score = new JLabel("Score: " + gamescore + "��"));
		score.setFont(new Font("����", Font.BOLD, 30));

		jPanelbutton = new JPanel(new FlowLayout());
		jPanelbutton.add(restart = new JButton("restart"));
		restart.addActionListener(this);
		jPanelbutton.add(exit = new JButton("exit"));
		exit.addActionListener(this);

		jPanelscore.add("East", jPanelbutton);
		this.add("North", jPanelscore);
	
		thread = new Thread(new Runnable() {
			public void run() {
				
				try {
					int a = 0; //Thread test
					for (int count = 0; count <= jpbar.getMaximum(); count++) {
						count += 1;
						jpbar.setValue(count);
						Thread.sleep(300);
						System.out.println(a++); //Thread test
						if(member.isFound1()==true&&member.isFound2()==true&&
								member.isFound3()==true&&member.isFound4()==true && 
								jpbar.getValue()!=jpbar.getMaximum())	{ 
								 jpbar.setValue(0);
								 return;	 
							 }
							 if(jpbar.getValue()==jpbar.getMaximum()) {
								 if( DifferenceDao.getDifferenceDao().clear !=1) {
								 new GameOver();
								 System.out.println("clear: "+DifferenceDao.getDifferenceDao().clear);//
								 DifferenceDao.gameOverCheck = true; 
								 viewDispose();
								 }	
							}
						 } 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}); 
		thread.start();
	
	}

	public class Mouse extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

			Point p = e.getPoint();
			if (DifferenceDao.getDifferenceDao().correctPoint(0, p)) {
				member.setFound1(true);
				if (member.getOnce1() == 0) {
					addScore();
					member.setOnce1(1);
				}
				repaint();
			}
			if (DifferenceDao.getDifferenceDao().correctPoint(1, p)) {
				member.setFound2(true);
				if (member.getOnce2() == 0) {
					addScore();
					member.setOnce2(1);
				}
				repaint();
			}
			if (DifferenceDao.getDifferenceDao().correctPoint(2, p)) {
				member.setFound3(true);
				if (member.getOnce3() == 0) {
					addScore();
					member.setOnce3(1);
				}
				repaint();

			}
			if (DifferenceDao.getDifferenceDao().correctPoint(3, p)) {
				member.setFound4(true);
				if (member.getOnce4() == 0) {
					addScore();
					member.setOnce4(1);
				}
				repaint();
			}
			
			DifferenceDao.getDifferenceDao().isAllFound();
			viewDispose();
			
		}

	}

	public Difference() {

		DifferenceDao.getDifferenceDao().imgChange();
		this.member = DifferenceDao.member;
		view();
		this.setTitle("Ʋ�� �׸� ã��");
		this.setSize(984, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void addScore() {
		gamescore += 500;
		member.setGamescore(gamescore);
		score.setText("score: " + gamescore + "��");

	}

	public  void viewDispose() {
		if (DifferenceDao.allFoundCheck||DifferenceDao.gameClearCheck||DifferenceDao.gameOverCheck) {
			dispose();
			DifferenceDao.allFoundCheck = false;
			DifferenceDao.gameClearCheck = false;
			DifferenceDao.gameOverCheck = false;
		}
	}

}

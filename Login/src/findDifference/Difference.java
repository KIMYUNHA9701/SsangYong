package findDifference;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import model.MemberDao;
import view.Main;

public class Difference extends JFrame implements ActionListener {

	static DifferenceMember member;
	static int gamescore = 0;
	static int hiscore;
    Thread thread;

	JLabel score;
    JProgressBar jpbar;
	JPanel jPanelscore, jPanelbar, jPanelbutton, jPanelimg;
	RoundedButton restart, exit;
	boolean threadOut = false;
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
    if(e.getSource()==restart) {
    	jpbar.setValue(0);
    	DifferenceDao.getDifferenceDao().dataAllReset();
    	this.dispose();
    	threadOut = true; 
    	new Difference();
    }
	if(e.getSource()==exit)	{
		this.dispose();
		threadOut = true;
			
	  }
	
	}
	public void view() {

		Mouse mouse = new Mouse();

		this.setLayout(new BorderLayout());

		this.addMouseListener(mouse);
		this.add("Center", DifferenceDao.currentImg);

		jpbar = new JProgressBar(0, 100);
		jpbar.setStringPainted(true);
		jpbar.setBackground(new Color(255,204,102));
		jpbar.setForeground(new Color(0xbbada0));
		jpbar.setFont(new Font("굴림", Font.BOLD, 15));
		jpbar.setString("15 seconds");

		jPanelbar = new JPanel(new GridLayout(1, 1));
		jPanelbar.add(jpbar);
		this.add("South", jPanelbar);

		jPanelscore = new JPanel(new BorderLayout());
		jPanelscore.add("West", score = new JLabel("Score: " + gamescore + "점"));
		jPanelscore.setBackground(new Color(0xbbada0)); 
		score.setFont(new Font("굴림", Font.BOLD, 30));

		jPanelbutton = new JPanel(new FlowLayout());
		jPanelbutton.add(restart = new RoundedButton("restart"));
		jPanelbutton.setBackground(new Color(0xbbada0));
		restart.addActionListener(this);
		jPanelbutton.add(exit = new RoundedButton("exit"));
		exit.addActionListener(this);

		jPanelscore.add("East", jPanelbutton);
		this.add("North", jPanelscore);
	
		thread = new Thread(new Runnable() {
			public void run() {
				
				try {
					int a = 0; 
					for (int count = 0; count <= jpbar.getMaximum(); count++) {
						count += 1;
						jpbar.setValue(count);
						Thread.sleep(300);
						System.out.println(a++); 
						if(a==30) {
							jpbar.setForeground(Color.RED);
						}  
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
							 
							 if(threadOut ==true) { //쓰레드 종료
								 return;
								
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
		hiscore = MemberDao.selectGameScore(DifferenceDao.id, 1);
		DifferenceDao.getDifferenceDao().imgChange();
		this.member = DifferenceDao.member;
		view();
		this.setTitle("틀린 그림 찾기");
		this.setBounds(100, 100, 984, 600);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				dispose();
				threadOut = true;
			}
		
		});
	}

	public void addScore() {
		gamescore += 500;
		member.setGamescore(gamescore);
		score.setText("score: " + gamescore + "점");

	}

	public  void viewDispose() {
		if (DifferenceDao.allFoundCheck||DifferenceDao.gameClearCheck||DifferenceDao.gameOverCheck) {
			dispose();
			DifferenceDao.allFoundCheck = false;
			DifferenceDao.gameClearCheck = false;
			DifferenceDao.gameOverCheck = false;
		}
	}

	public static int gethiScore() {
		return hiscore; 
		
	
	}
	
	public static void main(String[] args) {
		new Difference();
		
	}
	
}

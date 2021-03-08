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
	static boolean Wrong = false;
	static Point point; //클릭 좌표
	int count =0;
	
	
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
        //프로그레스바 생성
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
		
		//쓰레드 생성
		thread = new Thread(new Runnable() { 
			public void run() {
				
				try {
					int a = 0; 
					for (count = 0; count <= jpbar.getMaximum(); count++) {
						count += 1;
						jpbar.setValue(count);
						Thread.sleep(300);
						System.out.println(a++); 
						if(a==30) { 
							jpbar.setForeground(Color.RED); 
						}  
						//다른 부분 4 곳 모두 찾으면  프로그레스바 0으로 리셋
						if(member.isFound1()==true&&member.isFound2()==true&&
								member.isFound3()==true&&member.isFound4()==true && 
								jpbar.getValue()!=jpbar.getMaximum())	{ 
								 jpbar.setValue(0);
								 return;	 
							 }
						//프로그레스바가  지정한 최대값에 도달했을 때, 다른 부분 4개를 모두 찾지 못했으면 게임오버
							 if(jpbar.getValue()==jpbar.getMaximum()) {
								 if( DifferenceDao.getDifferenceDao().clear !=1) {
								 new GameOver();
								 System.out.println("clear: "+DifferenceDao.getDifferenceDao().clear);//
								 DifferenceDao.gameOverCheck = true; 
								 viewDispose();
								 
								 }	 
							}
							
							//threadOut의 초기값은 false
							//restart 또는 exit 버튼을 누르게 되면  threadOut이 true로 초기화
							//threadOut이 true와 같을 때, for문을 빠져나가게 되면서  쓰레드 종료 
							 if(threadOut ==true) { 
								 return;
								
							 }
						 } 
					
			
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}); 
		thread.start(); //쓰레드 실행
	
	}

	public class Mouse extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

			Point p = e.getPoint();
			//correctPoint(정답 좌표 배열의 인덱스(0~4), 마우스로 클릭한 포인트)
			//정답좌표를 클릭했을 시, true 반환 -> if문 실행.
			if (DifferenceDao.getDifferenceDao().correctPoint(0, p)) {
				member.setFound1(true);
			   //정답 좌표를 클릭했을때 점수를 얻는 상황을 한 스테이지 당 한 번으로 제한.
				if (member.getOnce1() == 0) { 
					addScore(); // score +500
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
			
			DifferenceDao.getDifferenceDao().isAllFound(); //다른 부분 4 곳 모두 찾았는지 확인 
			viewDispose();
			//틀린 좌표 클릭 시 if문 실행
			if(!(DifferenceDao.getDifferenceDao().correctPoint(0, p))&&!(DifferenceDao.getDifferenceDao().correctPoint(1, p))&&
					!(DifferenceDao.getDifferenceDao().correctPoint(2, p))&&!(DifferenceDao.getDifferenceDao().correctPoint(3, p))) {
				 Wrong = true; 
				 count +=10; // 남은 시간 -10 
				 point = p;  // 변수 point에 틀린 좌표값 초기화
				repaint();
			}
			
			
		}

	}

	public Difference() {
		hiscore = MemberDao.selectGameScore(DifferenceDao.id, 1);
		DifferenceDao.getDifferenceDao().imgChange();
		this.member = DifferenceDao.member;
		view();
		this.setTitle("다른 그림 찾기");
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
		//정답 좌표 클릭 시, 점수 증가
		gamescore += 500;
		member.setGamescore(gamescore);
		score.setText("score: " + gamescore + "점");

	}

	public  void viewDispose() { 
		//한 스테이지에서 정답을 모두 찾거나, 게임클리어 하거나, 또는 게임오버 했을때 해당 창 종료
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

	
}
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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	RoundedButton restart, exit, timeReset;
	boolean threadOut = false;
	static boolean Wrong = false;
	static Point point; //Ŭ�� ��ǥ
	int count =0; //���α׷����� ���� ����
	int a = 0;    //���α׷����� ���� ����
	
	private int timeResetCnt;//���� ������ timeReset���� ������ �� ������ ����
	ArrayList<Object[]> itemlist = new ArrayList<Object[]>();
	
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
	if(e.getSource()==timeReset)	{ //�ð� �ʱ�ȭ ������ ���
		if(timeResetCnt<=0) {
		 JOptionPane.showMessageDialog(null, "��� ������ �������� �����ϴ�.");
		}else {
		count = 0;
		a = 0;
		jpbar.setForeground(new Color(0xbbada0));
		timeResetCnt--;
		MemberDao.updateGameItem(DifferenceDao.id, 1, "timeReset", timeResetCnt);
		timeReset.setText("timeReset " + timeResetCnt);
	
		}
	  }
	
	
	}
	public void view() {

		Mouse mouse = new Mouse();

		this.setLayout(new BorderLayout());

		this.addMouseListener(mouse);
		this.add("Center", DifferenceDao.currentImg);
        //���α׷����� ����
		jpbar = new JProgressBar(0, 100);
		jpbar.setStringPainted(true);
		jpbar.setBackground(new Color(255,204,102));
		jpbar.setForeground(new Color(0xbbada0));
		jpbar.setFont(new Font("����", Font.BOLD, 15));
		jpbar.setString("15 seconds");

		jPanelbar = new JPanel(new GridLayout(1, 1));
		jPanelbar.add(jpbar);
		this.add("South", jPanelbar);

		jPanelscore = new JPanel(new BorderLayout());
		jPanelscore.add("West", score = new JLabel("Score: " + gamescore + "��"));
		jPanelscore.setBackground(new Color(0xbbada0)); 
		score.setFont(new Font("����", Font.BOLD, 30));

		jPanelbutton = new JPanel(new FlowLayout());
		jPanelbutton.setBackground(new Color(0xbbada0));
		jPanelbutton.add(timeReset = new RoundedButton("timeReset " + timeResetCnt ));
		timeReset.addActionListener(this);
		jPanelbutton.add(restart = new RoundedButton("restart"));
		restart.addActionListener(this);
		jPanelbutton.add(exit = new RoundedButton("exit"));
		exit.addActionListener(this);
		

		jPanelscore.add("East", jPanelbutton);
		this.add("North", jPanelscore);
		
		//������ ����
		thread = new Thread(new Runnable() { 
			public void run() {
				
				try {
					
					for (count = 0; count <= jpbar.getMaximum(); count++) {
						count += 1;
						jpbar.setValue(count);
						Thread.sleep(300);
						System.out.println(a++);  //test
						if(a==30) { 
							jpbar.setForeground(Color.RED); //�ð��� ���� �̻� ������ ���α׷����� �� change
						}  
						//�ٸ� �κ� 4 �� ��� ã����  ���α׷����� 0���� ����
						if(member.isFound1()==true&&member.isFound2()==true&&
								member.isFound3()==true&&member.isFound4()==true && 
								jpbar.getValue()!=jpbar.getMaximum())	{ 
								 jpbar.setValue(0);
								 return;	 
							 }
						//���α׷����ٰ�  ������ �ִ밪�� �������� ��, �ٸ� �κ� 4���� ��� ã�� �������� ���ӿ���
							 if(jpbar.getValue()==jpbar.getMaximum()) {
								 if( DifferenceDao.getDifferenceDao().clear !=1) {
								 new GameOver();
								 DifferenceDao.getDifferenceDao().dataAllReset();
								 DifferenceDao.getDifferenceDao().addPoint();
								 System.out.println("clear: "+DifferenceDao.getDifferenceDao().clear);//
								 DifferenceDao.gameOverCheck = true; 
								 viewDispose();
								 
								 }	 
							}
							
							//threadOut�� �ʱⰪ�� false
							//restart �Ǵ� exit ��ư�� ������ �Ǹ�  threadOut�� true�� �ʱ�ȭ
							//threadOut�� true�� ���� ��, for���� ���������� �Ǹ鼭  ������ ���� 
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
		thread.start(); //������ ����
	
	}

	public class Mouse extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

			Point p = e.getPoint();
			//correctPoint(���� ��ǥ �迭�� �ε���(0~4), ���콺�� Ŭ���� ����Ʈ)
			//������ǥ�� Ŭ������ ��, true ��ȯ -> if�� ����.
			if (DifferenceDao.getDifferenceDao().correctPoint(0, p)) {
				member.setFound1(true);
			   //���� ��ǥ�� Ŭ�������� ������ ��� ��Ȳ�� �� �������� �� �� ������ ����.
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
			
			DifferenceDao.getDifferenceDao().isAllFound(); //�ٸ� �κ� 4 �� ��� ã�Ҵ��� Ȯ�� 
			viewDispose();
			//Ʋ�� ��ǥ Ŭ�� �� if�� ����
			if(!(DifferenceDao.getDifferenceDao().correctPoint(0, p))&&!(DifferenceDao.getDifferenceDao().correctPoint(1, p))&&
					!(DifferenceDao.getDifferenceDao().correctPoint(2, p))&&!(DifferenceDao.getDifferenceDao().correctPoint(3, p))) {
				 Wrong = true; 
				 count +=10; // ���� �ð� -10 
				 point = p;  // ���� point�� Ʋ�� ��ǥ�� �ʱ�ȭ
				repaint();
			}
			
			
		}

	}

	public Difference() {
		
		itemlist = MemberDao.selectGameItem(DifferenceDao.id, 1);	
		timeResetCnt = (int) itemlist.get(0)[1];
		
		hiscore = MemberDao.selectGameScore(DifferenceDao.id, 1);
		DifferenceDao.getDifferenceDao().imgChange();
		this.member = DifferenceDao.member;
		view();
		this.setTitle("�ٸ� �׸� ã��");
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
		//���� ��ǥ Ŭ�� ��, ���� ����
		gamescore += 500;
		member.setGamescore(gamescore);
		score.setText("score: " + gamescore + "��");

	}
	
	
	

	public static int gethiScore() {
		return hiscore; 
		
	
	}
	
	public  void viewDispose() { 
		//�� ������������ ������ ��� ã�ų�, ����Ŭ���� �ϰų�, �Ǵ� ���ӿ��� ������ �ش� â ����
		if (DifferenceDao.allFoundCheck||DifferenceDao.gameClearCheck||DifferenceDao.gameOverCheck) {
			dispose();
			DifferenceDao.allFoundCheck = false;
			DifferenceDao.gameClearCheck = false;
			DifferenceDao.gameOverCheck = false;
		}
	}

	
}
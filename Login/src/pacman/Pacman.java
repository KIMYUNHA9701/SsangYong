package pacman;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import model.MemberDao;

public class Pacman extends JFrame {
	Pan pan = new Pan();
	Timer timer = new Timer();
	Thread thread;
	Thread threadtime;
	boolean running = true;
	// 쓰레드의 상태
	int state = 0;
	// 처음 시작(오른쪽으로 누르면 쓰레드를 스타트시키기 위함)
	int time = 60;
	private JLabel jlabel, jlabel2;
	private int score = 0;
	private int sel = 2; // 팩맨 시작 그림
	private JProgressBar pb;
	
	String id;
	int hiscore;

	public Pacman(String id) {
		this.id = id;
		hiscore = MemberDao.selectGameScore(id,2);
		this.setLayout(new BorderLayout());
		jlabel = new JLabel("SCORE: " + score);
		jlabel.setHorizontalAlignment(JLabel.RIGHT);
		this.add("North", jlabel);
		jlabel2 = new JLabel(time + "초");
		jlabel2.setHorizontalAlignment(JLabel.CENTER);
		pb = new JProgressBar(1,60);
		pb.setValue(time);
		this.add("South", pb);
		this.add("Center", pan);
		this.getContentPane().setBackground(Color.WHITE);
		this.setBounds(0, 0, 500, 570);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); // 창크기 조절 불가능
		// close 이벤트
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// 키보드 이벤트
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// 키보드 액션
				if (e.getKeyCode() == e.VK_LEFT) {
					sel = 0;
				} else if (e.getKeyCode() == e.VK_RIGHT && state == 0) {
					sel = 2;
					state = 1;
					threadstart();
				} else if (e.getKeyCode() == e.VK_RIGHT && state == 1) {
					sel = 2;
				} else if (e.getKeyCode() == e.VK_UP) {
					sel = 4;
				} else if (e.getKeyCode() == e.VK_DOWN) {
					sel = 6;
				}
				repaint();
			}
		});
	}

	public class Pan extends Canvas implements Runnable {
		String dir = "PacmanImage\\";
		private Image image;
		private int x = 225;
		private int y = 225; // 팩맨 시작 위치(가운데)
		private Image[] foodImg = new Image[5]; // 푸드 이미지 배열 생성
		private int[][] food = new int[foodImg.length][2]; // 5개의 먹이의 좌표
		private int count; // 먹이 먹은 횟수 저장

		public Pan() {
			// 먹이의 좌표 배열
			for (int i = 0; i < food.length; i++) {
				for (int j = 0; j < 2; j++) {
					food[i][j] = (int) (Math.random() * 401) + 50; // 좌표 값50~450사이의 난수 생성
					for (int k = 0; k < i; k++) {
						if (food[i][j] > food[k][j] - 25 && food[i][j] < food[k][j] + 25) { // 겹치지 않게
							i--;
							break;
						}
					}
				}
			}

		}// Pacman 생성자()

		public void paint(Graphics g) {
			g.drawRect(0, 0, 500, 500);
			Toolkit t = Toolkit.getDefaultToolkit();
			image = t.getImage(dir + "pacman.png");
			for (int i = 0; i < 5; i++) {
				int x;
				int y;
				foodImg[i] = t.getImage(dir + "cookie.png");
				x = food[i][0];
				y = food[i][1];
				g.drawImage(foodImg[i], x, y, this);
			}
			// 팩맨 이미지 변경
			g.drawImage(image, x, y, x + 50, y + 50, sel * 50, 0, sel * 50 + 50, 50, this);
		}

		// 스레드 run 메소드 오버라이드
		@Override
		public void run() {
			while (running) {
				// 입 왔다갔다하는 if문으로 이미지 변경
				if (sel % 2 == 0)
					sel++;
				else
					sel--;
				// 키를 눌렀을 때 한쪽 방향으로 반복 이동
				if (sel == 0) { // 왼쪽으로 입벌림
					if (x < -50) { // 화면 넘어가면 반대편으로 나옴
						x = 520;
					} else { // 계속 왼쪽으로 10씩 이동
						x -= 10;
					}
				} else if (sel == 2) { // 오른쪽으로 입벌림
					if (x > 520) {
						x = -50;
					} else {
						x += 10;
					}
				} else if (sel == 4) { // 위쪽
					if (y < -50) {
						y = 510;
					} else {
						y -= 10;
					}
				} else if (sel == 6) { // 아래쪽
					if (y > 520) {
						y = -50;
					} else {
						y += 10;
					}
				}

				// 사과가 완전히 잡아 먹혀야 먹힘
				for (int i = 0; i < food.length; i++) {
					if (food[i][0] + 10 > x + 15 && food[i][0] + 10 < x + 35) {
						if (food[i][1] + 10 > y + 15 && food[i][1] + 10 < y + 35) {
							food[i][0] = -100;
							food[i][1] = -100;
							// 먹힌 먹이는 프레임 밖으로 보낸다.
							score += 20;
							jlabel.setText("SCORE: " + score);
							count++;
						}
					}
				}
				// 사과의 중앙이 pacman에 닿기만 해도 먹힘
//				for (int i = 0; i < food.length; i++) { 
//					if (food[i][0] + 10 > x && food[i][0] + 10 < x + 50) {
//						if (food[i][1] + 10 > y && food[i][1] + 10 < y + 50) {
//							System.out.println("x,y = (" + x + "," + y + ")");
//							System.out.println("food = (" + food[i][0] + "," + food[i][1] + ")");
//							food[i][0] = -10;
//							food[i][1] = -10;
//							// 먹힌 먹이는 프레임 밖으로 보낸다.
//							count++;
//						}
//					}
//				}
				// 먹이를 다 먹었을 때 스레드 종료
				if (count == food.length) {
					running = false;
					break;
				}
				repaint();
				// Exception 잡기
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public class Timer extends Thread {
		// 타이머 메소드
		@Override
		public void run() {
			while (running) {
				try {
					time--;
					showSec();
					pb.setValue(time);
					jlabel.setText("SCORE: " + score);
					sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (time == 0) {
					running = false;
					break;
				}
			}
			restart();
		}

		public void showSec() {
			jlabel2.setText(time + "초");
		}
	}

	public void restart() {
		// 다이얼로그 띄우기
		score += time;
		resultScore();
		int result = JOptionPane.showConfirmDialog(null, "최종 SCORE: " + score + "\n 다시 시작하시겠습니까?", "restart",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			Pacman.this.dispose();
			// 현재 창 닫고 다시 시작
			new Pacman(id);
		} else {
			Pacman.this.dispose();
		}
	}

	public void threadstart() {
		thread = new Thread(pan);
		thread.start();
		threadtime = new Thread(timer);
		threadtime.start();
	}
	
	public void resultScore() { 
		if(hiscore < score) hiscore = score;
		MemberDao.updateGameScore(id, hiscore,2);
	}


}
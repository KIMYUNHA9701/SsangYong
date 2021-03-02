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
	// �������� ����
	int state = 0;
	// ó�� ����(���������� ������ �����带 ��ŸƮ��Ű�� ����)
	int time = 60;
	private JLabel jlabel, jlabel2;
	private int score = 0;
	private int sel = 2; // �Ѹ� ���� �׸�
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
		jlabel2 = new JLabel(time + "��");
		jlabel2.setHorizontalAlignment(JLabel.CENTER);
		pb = new JProgressBar(1,60);
		pb.setValue(time);
		this.add("South", pb);
		this.add("Center", pan);
		this.getContentPane().setBackground(Color.WHITE);
		this.setBounds(0, 0, 500, 570);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); // âũ�� ���� �Ұ���
		// close �̺�Ʈ
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// Ű���� �̺�Ʈ
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// Ű���� �׼�
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
		private int y = 225; // �Ѹ� ���� ��ġ(���)
		private Image[] foodImg = new Image[5]; // Ǫ�� �̹��� �迭 ����
		private int[][] food = new int[foodImg.length][2]; // 5���� ������ ��ǥ
		private int count; // ���� ���� Ƚ�� ����

		public Pan() {
			// ������ ��ǥ �迭
			for (int i = 0; i < food.length; i++) {
				for (int j = 0; j < 2; j++) {
					food[i][j] = (int) (Math.random() * 401) + 50; // ��ǥ ��50~450������ ���� ����
					for (int k = 0; k < i; k++) {
						if (food[i][j] > food[k][j] - 25 && food[i][j] < food[k][j] + 25) { // ��ġ�� �ʰ�
							i--;
							break;
						}
					}
				}
			}

		}// Pacman ������()

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
			// �Ѹ� �̹��� ����
			g.drawImage(image, x, y, x + 50, y + 50, sel * 50, 0, sel * 50 + 50, 50, this);
		}

		// ������ run �޼ҵ� �������̵�
		@Override
		public void run() {
			while (running) {
				// �� �Դٰ����ϴ� if������ �̹��� ����
				if (sel % 2 == 0)
					sel++;
				else
					sel--;
				// Ű�� ������ �� ���� �������� �ݺ� �̵�
				if (sel == 0) { // �������� �Թ���
					if (x < -50) { // ȭ�� �Ѿ�� �ݴ������� ����
						x = 520;
					} else { // ��� �������� 10�� �̵�
						x -= 10;
					}
				} else if (sel == 2) { // ���������� �Թ���
					if (x > 520) {
						x = -50;
					} else {
						x += 10;
					}
				} else if (sel == 4) { // ����
					if (y < -50) {
						y = 510;
					} else {
						y -= 10;
					}
				} else if (sel == 6) { // �Ʒ���
					if (y > 520) {
						y = -50;
					} else {
						y += 10;
					}
				}

				// ����� ������ ��� ������ ����
				for (int i = 0; i < food.length; i++) {
					if (food[i][0] + 10 > x + 15 && food[i][0] + 10 < x + 35) {
						if (food[i][1] + 10 > y + 15 && food[i][1] + 10 < y + 35) {
							food[i][0] = -100;
							food[i][1] = -100;
							// ���� ���̴� ������ ������ ������.
							score += 20;
							jlabel.setText("SCORE: " + score);
							count++;
						}
					}
				}
				// ����� �߾��� pacman�� ��⸸ �ص� ����
//				for (int i = 0; i < food.length; i++) { 
//					if (food[i][0] + 10 > x && food[i][0] + 10 < x + 50) {
//						if (food[i][1] + 10 > y && food[i][1] + 10 < y + 50) {
//							System.out.println("x,y = (" + x + "," + y + ")");
//							System.out.println("food = (" + food[i][0] + "," + food[i][1] + ")");
//							food[i][0] = -10;
//							food[i][1] = -10;
//							// ���� ���̴� ������ ������ ������.
//							count++;
//						}
//					}
//				}
				// ���̸� �� �Ծ��� �� ������ ����
				if (count == food.length) {
					running = false;
					break;
				}
				repaint();
				// Exception ���
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public class Timer extends Thread {
		// Ÿ�̸� �޼ҵ�
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
			jlabel2.setText(time + "��");
		}
	}

	public void restart() {
		// ���̾�α� ����
		score += time;
		resultScore();
		int result = JOptionPane.showConfirmDialog(null, "���� SCORE: " + score + "\n �ٽ� �����Ͻðڽ��ϱ�?", "restart",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			Pacman.this.dispose();
			// ���� â �ݰ� �ٽ� ����
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
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
	// ���� ����
	// 60�� �ȿ� Ű����� �Ѹ��� ������ ��Ʈ�� ���� ���̸� �Դ� ����
	// ���̴� �ϳ��� 20��*5
	// time item�� ������ �ð� +30��(�ִ� 60��)
	// x2 item�� ������ ����*2
	// ��Ʈ�� �⺻ ������ ���� ���η� �����̵� Ư�� �������� �Ѹ��� �����Ǹ� �����
	// ���̸� �� ������ ���� stop -> ȹ���� ���� + ���� �ð� = ��������
	// time=0 �� �ǵ� ���� stop -> ȹ���� ���� + ���� �ð�(=0) = ��������
	// ��Ʈ�� ��Ƶ� ���� stop -> 0��
	
	String dir = "PacmanImage\\";
	Pan pan = new Pan();
	Timer timer = new Timer();
	Thread pacmanthread;
	Thread timeThread;
	boolean pacmanRunning = true;
	boolean timeRunning = true;
	int state = 0; // ������ ����(���������� ������ �����带 ��ŸƮ��Ű�� ����)
	int time = 60;
	
	private JLabel jlabel;
	private int score = 0;
	private int sel = 2; // �Ѹ� ���� �׸�
	private JProgressBar pb;

	//�Ѹ�
	private Image pacImg;
	private int x = 225;
	private int y = 225; // �Ѹ� ���� ��ġ(���)
	private int count; // ���� ���� Ƚ�� ����

	//��Ʈ(ó�� ��ġ ����)
	private Image ghostImg1;
	private int ghostx = 10;
	private int ghosty = 30;
	private Image ghostImg2;
	private int ghostx2 = 430;
	private int ghosty2 = 400;

	//������
	private Image x2Img;
	private int x2x;
	private int x2y;
	private Image timeImg;
	private int timex;
	private int timey;

	String id;
	int hiscore;
	
	public Pacman(String id) {
		this.id = id;
		hiscore = MemberDao.selectGameScore(id,2);
		this.setLayout(new BorderLayout());
		jlabel = new JLabel("SCORE: " + score);
		jlabel.setHorizontalAlignment(JLabel.RIGHT);
		this.add("North", jlabel);
		
		//timer�� ���� ���α׷�����
		pb = new JProgressBar(1, 60);
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
		private Image[] foodImg = new Image[5]; // Ǫ�� �̹��� �迭 ����
		private int[][] food = new int[foodImg.length][2]; // 5���� ������ ��ǥ

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
			x2x = (int) (Math.random() * 401) + 50; // ��ǥ ��50~450������ ���� ����
			x2y = (int) (Math.random() * 401) + 50; // ��ǥ ��50~450������ ���� ����
			timex = (int) (Math.random() * 401) + 50; // ��ǥ ��50~450������ ���� ����
			timey = (int) (Math.random() * 401) + 50; // ��ǥ ��50~450������ ���� ����
		}// Pacman ������()

		public void paint(Graphics g) {
			g.drawRect(0, 0, 500, 500);
			Toolkit t = Toolkit.getDefaultToolkit();
			pacImg = t.getImage(dir + "pacman.png");
			for (int i = 0; i < 5; i++) {
				int foodx;
				int foody;
				foodImg[i] = t.getImage(dir + "circle1.png");
				foodx = food[i][0];
				foody = food[i][1];
				g.drawImage(foodImg[i], foodx, foody, this);

			}
			// �Ѹ� �̹��� ����
			g.drawImage(pacImg, x, y, x + 50, y + 50, sel * 50, 0, sel * 50 + 50, 50, this);

			ghostImg1 = t.getImage(dir + "ghost1.png");
			g.drawImage(ghostImg1, ghostx, ghosty, this);
			ghostImg2 = t.getImage(dir + "ghost2.png");
			g.drawImage(ghostImg2, ghostx2, ghosty2, this);

			x2Img = t.getImage(dir + "x2.png");
			g.drawImage(x2Img, x2x, x2y, this);

			timeImg = t.getImage(dir + "time.png");
			g.drawImage(timeImg, timex, timey, this);

		}

		// ������ run �޼ҵ� �������̵�
		@Override
		public void run() {
			while (pacmanRunning) {
				// ghost1
				// �ذ��ؾ��Ұ� -> ghost�� �Ʒ��� �����̱� ������ �Ʒ��� ����ĥ �� ����
				// ghost�� y���� ���� �����̴ٰ� �Ѹǰ� y���� �������� x���� ���� �����δ�
				if (ghosty < y + 10 && ghosty > y - 10) {
					if (ghostx < x) {
						if (ghostx > 400) {
							ghostx -= 20;
						} else {
							ghostx += 10;
						}
					} else {
						if (ghostx < -50) { // ȭ�� �Ѿ�� �ݴ������� ����
							ghostx = 520;
						} else { // ��� �������� 10�� �̵�
							ghostx -= 10;
						}
					}
				} else {
					if (ghosty > 520) {
						ghosty = -50;
					} else {
						ghosty += 10;
					}
				}
				// ghost2
				if (ghostx2 < x + 10 && ghostx2 > x - 10) {
					if (ghosty2 < y) {
						if (ghosty2 > 400) {
							ghosty2 -= 20;
						} else {
							ghosty2 += 10;
						}
					} else {
						if (ghosty2 < -50) { // ȭ�� �Ѿ�� �ݴ������� ����
							ghosty2 = 520;
						} else { // ��� �������� 10�� �̵�
							ghosty2 -= 10;
						}
					}
				} else {
					if (ghostx2 > 520) {
						ghostx2 = -50;
					} else {
						ghostx2 += 10;
					}
				}

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

				// ���̰� ������ ��� ������ ����
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
				// ghost�� pacman�� ����
				if (ghostx - 50 <= x && ghostx + 50 >= x) {
					if (ghosty - 50 <= y && ghosty + 50 >= y) {
						score = 0;
						pacmanRunning = false;
						timeRunning = false;
					}
				}
				// ghost2�� pacman�� ����
				if (ghostx2 - 50 <= x && ghostx2 + 50 >= x) {
					if (ghosty2 - 50 <= y && ghosty2 + 50 >= y) {
						score = 0;
						pacmanRunning = false;
						timeRunning = false;
					}
				}
				// 2�� �������� ����
				if (x2x + 10 > x + 15 && x2x + 10 < x + 35) {
					if (x2y + 10 > y + 15 && x2y + 10 < y + 35) {
						x2x = -100;
						x2y = -100;
						// ���� ���̴� ������ ������ ������.
						score *= 2;
						jlabel.setText("SCORE: " + score);
					}
				}
				// time �������� ����
				if (timex + 10 > x + 15 && timex + 10 < x + 35) {
					if (timey + 10 > y + 15 && timey + 10 < y + 35) {
						timex = -100;
						timey = -100;
						// ���� ���̴� ������ ������ ������.
						if (time <= 40) {
							time += 20;
						} else {
							time = 60;
						}
						pb.setValue(time);
					}
				}
				// ���̸� �� �Ծ��� �� ������ ����
				if (count == food.length) {
					pacmanRunning = false;
					break;
				}
				repaint();
				// Exception ���
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public class Timer extends Thread {
		// Ÿ�̸� �޼ҵ�
		@Override
		public void run() {
			while (pacmanRunning) {
				try {
					time--;
					pb.setValue(time);
					sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (time == 0) {
					pacmanRunning = false;
					timeRunning = false;
					break;
				}
			}
			while (timeRunning && !pacmanRunning) {
				// ���̸� �� �Ծ��� �� �ð��� ���ھ ������
				try {
					time--;
					score++;
					pb.setValue(time);
					jlabel.setText("SCORE: " + score);
					sleep(50);
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (time == 0) {
					timeRunning = false;
					break;
				}
			}
			restart();
		}
	}

	public void restart() {
		// ���̾�α� ����
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
		pacmanthread = new Thread(pan);
		pacmanthread.start();
		timeThread = new Thread(timer);
		timeThread.start();
	}

	//������ �ְ� ���� ����
	public void resultScore() { 
		if(hiscore < score) hiscore = score;
		MemberDao.updateGameScore(id, hiscore,2);
	}

}
package pacman;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import first.RoundedButton;
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

	// panel
	private JPanel panel1;
//	private JButton slowBtn, fastBtn;
	private RoundedButton slowBtn, fastBtn;
	private JLabel[] label = new JLabel[3];
	private String[] labelText = { "PACMAN", "�ְ����� : ", "���� : " };
	private int FrameWidth = 700;
	private int FrameHeight = 900;

	// pan, thread
	private String dir = "PacmanImage\\"; // �̹�����ġ
	Pan pan = new Pan();
	Timer timer = new Timer();
	Hero hero = new Hero();
	Ghost ghost = new Ghost();
	Thread panThread;
	Thread pacmanThread;
	Thread ghostThread;
	Thread timeThread;
	boolean pacmanRunning = true;
	boolean timeRunning = true;
	private int state = 0; // ������ ����(���������� ������ �����带 ��ŸƮ��Ű�� ����)
	private int time = 60;
	private int slowTime;
	private int fastTime;
	private boolean slow = false;
	private boolean fast = false;
	private JProgressBar pb;

	// �Ѹ�
	private Image pacImg;
	private int x = FrameWidth / 2;
	private int y = (FrameHeight - 200) / 2; // �Ѹ� ���� ��ġ(���)
	private int sel = 2; // �Ѹ� ���� �׸�
	private int count; // ���� ���� Ƚ�� ����

	// ��Ʈ(ó�� ��ġ ����)
	private Image ghostImg1;
	private int ghostx = 10;
	private int ghosty = 30;
	private Image ghostImg2;
	private int ghostx2 = FrameWidth - 100;
	private int ghosty2 = FrameHeight - 200;

	// ������
	private Image x2Img;
	private int x2x;
	private int x2y;
	private Image timeImg;
	private int timex;
	private int timey;

	// dao�� ���� �޾ƿ� �� �����ϴ� ����
	private String id;
	private int hiscore;

	// ���� ����
	private int score = 0;

	public Pacman(String id) {
		// dao���� �޾ƿ���
		this.id = id;
		hiscore = MemberDao.selectGameScore(id, 2);

		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(Color.WHITE);
		this.setBounds(100, 100, FrameWidth, FrameHeight);
		this.setVisible(true);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false); // âũ�� ���� �Ұ���

		initPanel();

		// timer�� ���� ���α׷�����
		pb = new JProgressBar(1, 60);
		pb.setBackground(new Color(255, 204, 102));
		pb.setForeground(new Color(0xbbada0));
		pb.setString(time + "seconds");
		pb.setStringPainted(true);
		pb.setValue(time);
		this.add("South", pb);
		this.add("Center", pan);
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

	public void initPanel() {
		panel1 = new JPanel();
		panel1.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
		panel1.setLayout(new GridLayout(1, 5, 10, 10));
		panel1.setBackground(new Color(0xbbada0));
		label[0] = new JLabel(labelText[0]);
		label[1] = new JLabel(labelText[1] + hiscore);
		label[2] = new JLabel(labelText[2] + score);

		label[0].setFont(new Font(labelText[0], Font.BOLD, 30));

		for (int i = 1; i < 3; i++) {
			label[i].setHorizontalAlignment(JLabel.RIGHT);
			label[i].setBackground(new Color(0xbbada0));
			label[i].setPreferredSize(new Dimension(this.WIDTH, 40));
			label[i].setOpaque(true);
		}
		slowBtn = new RoundedButton("slow");
		slowBtn.setFocusable(false);
		fastBtn = new RoundedButton("fast");
		fastBtn.setFocusable(false);

		slowBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				slow = true;
				slowTime = time;
			}
		});
		fastBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fast = true;
				fastTime = time;
			}
		});

		panel1.add(label[0]);
		panel1.add(label[1]);
		panel1.add(label[2]);
		panel1.add(slowBtn);
		panel1.add(fastBtn);

		this.add("North", panel1);
	}

	public class Pan extends Canvas implements Runnable {
		private Image[] foodImg = new Image[5]; // Ǫ�� �̹��� �迭 ����
		private int[][] food = new int[foodImg.length][2]; // 5���� ������ ��ǥ
		private int[][] item = new int[4][2]; // 4���� ������

		public Pan() {
			this.setBackground(new Color(255, 204, 102));
			// ������ ��ǥ �迭
			for (int i = 0; i < food.length; i++) {
				for (int j = 0; j < 2; j++) {
					food[i][j] = (int) (Math.random() * 601) + 50; // ��ǥ ��50~650������ ���� ����
					for (int k = 0; k < i; k++) {
						if (food[i][j] > food[k][j] - 25 && food[i][j] < food[k][j] + 25) { // ��ġ�� �ʰ�
							i--;
							break;
						}
					}
				}
			}
			for (int i = 0; i < item.length; i++) {
				for (int j = 0; j < 2; j++) {
					item[i][j] = (int) (Math.random() * 601) + 50; // ��ǥ ��50~650������ ���� ����
					for (int k = 0; k < i; k++) {
						if (item[i][j] > item[k][j] - 25 && item[i][j] < item[k][j] + 25) { // ��ġ�� �ʰ�
							i--;
							break;
						}
					}
				}
			}
			x2x = item[0][0];
			x2y = item[0][1];
			timex = item[1][0];
			timey = item[1][1];

		}// Pacman ������()

		public void paint(Graphics g) {
			Toolkit t = Toolkit.getDefaultToolkit();
			pacImg = t.getImage(dir + "pacman2.png");
			for (int i = 0; i < 5; i++) {
				int foodx;
				int foody;
				foodImg[i] = t.getImage(dir + "circle.png");
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
				// ���̰� ������ ��� ������ ����
				for (int i = 0; i < food.length; i++) {
					if (food[i][0] + 10 > x + 15 && food[i][0] + 10 < x + 35) {
						if (food[i][1] + 10 > y + 15 && food[i][1] + 10 < y + 35) {
							food[i][0] = -100;
							food[i][1] = -100;
							// ���� ���̴� ������ ������ ������.
							score += 20;
							label[2].setText("����: " + score);
							count++;
						}
					}
				}
				// ghost�� pacman�� ����
				if (ghostx - 50 <= x && ghostx + 50 >= x) {
					if (ghosty - 50 <= y && ghosty + 50 >= y) {
						pacmanRunning = false;
						timeRunning = false;
					}
				}
				// ghost2�� pacman�� ����
				if (ghostx2 - 50 <= x && ghostx2 + 50 >= x) {
					if (ghosty2 - 50 <= y && ghosty2 + 50 >= y) {
						pacmanRunning = false;
						timeRunning = false;
					}
				}
				// 2�� �������� ����
				if (x2x + 10 > x + 15 && x2x + 10 < x + 35) {
					if (x2y + 10 > y + 15 && x2y + 10 < y + 35) {
						x2x = -100;
						x2y = -100;
						score *= 2;
						label[2].setText("����: " + score);
					}
				}
				// time �������� ����
				if (timex + 10 > x + 15 && timex + 10 < x + 35) {
					if (timey + 10 > y + 15 && timey + 10 < y + 35) {
						timex = -100;
						timey = -100;
						if (time <= 40) {
							time += 20;
						} else {
							time = 60;
						}
						pb.setValue(time);
						pb.setString(time + "seconds");
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
					// 0.1�ʿ� �ѹ���
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public class Hero extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (pacmanRunning) {
				// �� �Դٰ����ϴ� if������ �̹��� ����
				if (sel % 2 == 0)
					sel++;
				else
					sel--;
				// Ű�� ������ �� ���� �������� �ݺ� �̵�
				if (sel == 0) { // �������� �Թ���
					if (x < -50) { // ȭ�� �Ѿ�� �ݴ������� ����
						x = FrameWidth + 20;
					} else { // ��� �������� 10�� �̵�
						x -= 10;
					}
				} else if (sel == 2) { // ���������� �Թ���
					if (x > FrameWidth + 20) {
						x = -50;
					} else {
						x += 10;
					}
				} else if (sel == 4) { // ����
					if (y < -50) {
						y = FrameHeight + 20;
					} else {
						y -= 10;
					}
				} else if (sel == 6) { // �Ʒ���
					if (y > FrameHeight + 20) {
						y = -50;
					} else {
						y += 10;
					}
				}
				try {
					if (fast == true) {
						Thread.sleep(50);
						if (time == fastTime - 10) {
							fast = false;
						}
					} else {
						// 0.3�ʿ� �ѹ���
						Thread.sleep(150);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public class Ghost extends Thread {
		@Override
		public void run() {
			while (pacmanRunning) {
				// ghost1
				// �ذ��ؾ��Ұ� -> ghost�� �Ʒ��� �����̱� ������ �Ʒ��� ����ĥ �� ����
				// ghost�� y���� ���� �����̴ٰ� �Ѹǰ� y���� �������� x���� ���� �����δ�
				if (ghosty < y + 10 && ghosty > y - 10) {
					if (ghostx < x) {
						if (ghostx > FrameWidth - 50) {
							ghostx -= 20;
						} else {
							ghostx += 10;
						}
					} else {
						if (ghostx < 50) { // ȭ�� �Ѿ�� �ݴ������� ����
							ghostx += 20;
						} else { // ��� �������� 10�� �̵�
							ghostx -= 10;
						}
					}
				} else {
					if (ghosty > FrameHeight + 20) {
						ghosty = -50;
					} else {
						ghosty += 10;
					}
				}
				// ghost2
				if (ghostx2 < x + 10 && ghostx2 > x - 10) {
					if (ghosty2 < y) {
						if (ghosty2 > FrameHeight - 150) {
							ghosty2 -= 20;
						} else {
							ghosty2 += 10;
						}
					} else {
						if (ghosty2 < 50) { // ȭ�� �Ѿ�� �ݴ������� ����
							ghosty2 += 20;
						} else { // ��� �������� 10�� �̵�
							ghosty2 -= 10;
						}
					}
				} else {
					if (ghostx2 > FrameWidth + 20) {
						ghostx2 = -50;
					} else {
						ghostx2 += 10;
					}
				}

				try {
					if (slow == true) {
						Thread.sleep(400);
						if (time == slowTime - 10) {
							slow = false;
						}
					} else {
						// 0.3�ʿ� �ѹ���
						Thread.sleep(150);
					}
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
					pb.setString(time + "seconds");
					sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (time < 20) {
					pb.setForeground(Color.RED);
				}
				if (time == 0) {
					pacmanRunning = false;
					timeRunning = false;
					break;
				}
			}
			while (timeRunning && !pacmanRunning) {
				// �ð����� ���� ���̸� �� �Ծ��� �� �ð��� ���ھ ������
				try {
					time--;
					score++;
					pb.setValue(time);
					label[2].setText("����: " + score);
					sleep(50);
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (time == 0) {
					timeRunning = false;
					break;
				}
			}
			resultScore();
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

	// ������ �ְ� ���� ����
	public void resultScore() {
		if (hiscore < score)
			hiscore = score;
		MemberDao.updateGameScore(id, hiscore, 2);
	}

	// ���������
	public void threadstart() {
		panThread = new Thread(pan);
		panThread.start();
		ghostThread = new Thread(ghost);
		ghostThread.start();
		pacmanThread = new Thread(hero);
		pacmanThread.start();
		timeThread = new Thread(timer);
		timeThread.start();
	}
}
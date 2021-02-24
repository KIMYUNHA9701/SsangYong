package sist.com.pacman;

import java.awt.event.*;

import java.awt.*;

class Pacman extends Frame implements Runnable {
	String dir = "C:\\Users\\YUNA\\git\\repository\\Exercise\\src\\sist\\com\\pacman\\";
	private Image image;
	private int sel = 2; // �Ѹ� ���� �׸�
	private int x = 225;
	private int y = 225; // �Ѹ� ���� ��ġ(���)
	private Image[] foodImg = new Image[5]; // Ǫ�� �̹��� �迭 ����
	private int[][] food = new int[foodImg.length][2]; // 5���� ������ ��ǥ
	private int count; // ���� ���� Ƚ�� ����

	public Pacman() {
		this.setBounds(300, 300, 500, 500);
		this.setVisible(true);
		this.setResizable(false); // âũ�� ���� �Ұ���
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
				} else if (e.getKeyCode() == e.VK_RIGHT) {
					sel = 2;
				} else if (e.getKeyCode() == e.VK_UP) {
					sel = 4;
				} else if (e.getKeyCode() == e.VK_DOWN) {
					sel = 6;
				}
				repaint();
			}
		});
	}// Pacman ������()

	public void paint(Graphics g) {
		Toolkit t = Toolkit.getDefaultToolkit();
		image = t.getImage(dir + "pacman.png");
//		image = t.getImage(dir + "circle2.png");
		// ���̿� ���� �߻��Ͽ� ��ǥ�� ���� �� �����ӿ� �ø���
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
		while (true) {
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
						System.out.println("x,y = (" + x + "," + y + ")");
						System.out.println("food = (" + food[i][0] + "," + food[i][1] + ")");
						food[i][0] = -10;
						food[i][1] = -10;
						// ���� ���̴� ������ ������ ������.
						count++;
					}
				}
			}
			// ����� �߾��� pacman�� ��⸸ �ص� ����
//			for (int i = 0; i < food.length; i++) { 
//				if (food[i][0] + 10 > x && food[i][0] + 10 < x + 50) {
//					if (food[i][1] + 10 > y && food[i][1] + 10 < y + 50) {
//						System.out.println("x,y = (" + x + "," + y + ")");
//						System.out.println("food = (" + food[i][0] + "," + food[i][1] + ")");
//						food[i][0] = -10;
//						food[i][1] = -10;
//						// ���� ���̴� ������ ������ ������.
//						count++;
//					}
//				}
//			}
			// ���̸� �� �Ծ��� �� ������ ����
			if (count == food.length) {
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

	// main �޼ҵ�
	public static void main(String[] args) {
		Pacman p = new Pacman();
		Thread thread = new Thread(p);
		thread.start();
	}
}

package sist.com.pacman;

import java.awt.event.*;

import java.awt.*;

class Pacman extends Frame implements Runnable {
	String dir = "C:\\Users\\YUNA\\git\\repository\\Exercise\\src\\sist\\com\\pacman\\";
	private Image image;
	private int sel = 2; // 팩맨 시작 그림
	private int x = 225;
	private int y = 225; // 팩맨 시작 위치(가운데)
	private Image[] foodImg = new Image[5]; // 푸드 이미지 배열 생성
	private int[][] food = new int[foodImg.length][2]; // 5개의 먹이의 좌표
	private int count; // 먹이 먹은 횟수 저장

	public Pacman() {
		this.setBounds(300, 300, 500, 500);
		this.setVisible(true);
		this.setResizable(false); // 창크기 조절 불가능
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
	}// Pacman 생성자()

	public void paint(Graphics g) {
		Toolkit t = Toolkit.getDefaultToolkit();
		image = t.getImage(dir + "pacman.png");
//		image = t.getImage(dir + "circle2.png");
		// 먹이에 난수 발생하여 좌표값 지정 후 프레임에 올리기
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
		while (true) {
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
						System.out.println("x,y = (" + x + "," + y + ")");
						System.out.println("food = (" + food[i][0] + "," + food[i][1] + ")");
						food[i][0] = -10;
						food[i][1] = -10;
						// 먹힌 먹이는 프레임 밖으로 보낸다.
						count++;
					}
				}
			}
			// 사과의 중앙이 pacman에 닿기만 해도 먹힘
//			for (int i = 0; i < food.length; i++) { 
//				if (food[i][0] + 10 > x && food[i][0] + 10 < x + 50) {
//					if (food[i][1] + 10 > y && food[i][1] + 10 < y + 50) {
//						System.out.println("x,y = (" + x + "," + y + ")");
//						System.out.println("food = (" + food[i][0] + "," + food[i][1] + ")");
//						food[i][0] = -10;
//						food[i][1] = -10;
//						// 먹힌 먹이는 프레임 밖으로 보낸다.
//						count++;
//					}
//				}
//			}
			// 먹이를 다 먹었을 때 스레드 종료
			if (count == food.length) {
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

	// main 메소드
	public static void main(String[] args) {
		Pacman p = new Pacman();
		Thread thread = new Thread(p);
		thread.start();
	}
}

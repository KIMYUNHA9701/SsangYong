package sist.com.minigame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Omok extends JFrame {
	int x, y; // 좌표
	int start; // 게임판
	int turn = 1; // 턴(사용자)
	int[][] XY = new int[100][2];
	Object[][] check = new Object[100][2];

	public void gamepan(Graphics g) {
		int a = 0;
//		int a1 = 0, b1 = 0;
		for (int i = 50; i <= 500; i = i + 50) {
			for (int j = 100; j <= 550; j = j + 50) {
				g.drawRect(i, j, 50, 50);
				XY[a][0] = i;
				XY[a][1] = j;
				check[a][0] = false;
				check[a][1] = 0; 
				a++;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		if (start == 0) { // 오목판 그리기
			gamepan(g);
			start++;
		} else { // 돌 그리기
			if (turn == 1) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.BLACK);
			}
			g.fillOval(x, y, 50, 50);
			g.setColor(Color.BLACK);
			g.drawOval(x, y, 50, 50);
		}
	}

	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		super.update(g);
	}

	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		super.repaint();
	}

	public Omok() {
		this.setBounds(1000, 100, 600, 650);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				for (int i = 0; i < XY.length; i++) {
					if (Math.abs(e.getX() - XY[i][0]) < 20 && Math.abs(e.getY() - XY[i][1]) <  20) {
						// 돌을 정확한 위치에 두기(배열)
						if ((boolean) check[i][0] == false) { // 해당 칸의 돌 유뮤 확인
							x = XY[i][0] - 25;
							y = XY[i][1] - 25;
							check[i][0] = true;
							check[i][1] = turn;
							repaint();
							turn = turn == 1 ? 2 : 1;
							// turn 바꿔주기
						} else {
							System.out.println("이미 돌이 놓아져있습니다");
						}
						break;
					}
				}
				if (isCheckMate1() == true || isCheckMate2() == true||isCheckMate3() == true || isCheckMate4() == true) {
					System.out.println(turn + "님 승리");
					int result = JOptionPane.showConfirmDialog(null, turn + "님 승리!\n 다시 도전하시겠습니까?", "restart",
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						System.out.println(this);
						Omok.this.dispose();
						new Omok();
					}
				}
			}

		});
	}

	public boolean isCheckMate1() {
		int cnt = 0;
		for (int j = 0; j < XY.length; j += 10) { // 세로 빙고
			for (int i = j; i < j + 10; i++) {
				if ((int) check[i][0] == 1) {
					cnt++;
					if (cnt == 5) {
						System.out.println("5개");
						return true;
					}
				} else {
					cnt = 0;
				}
			}
		}
		return false;
	}

	public boolean isCheckMate2() { // 가로
		int cnt = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = i; j < 100; j += 10) {
				if ((int) check[j][1] == 1) {
					cnt++;
					if (cnt == 5) {
						System.out.println("5개");
						return true;
					}
				} else {
					cnt = 0;
				}
			}
		}
		return false;
	}

	public boolean isCheckMate3() { // 오른쪽대각선
		int cnt = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = i; j < 100; j += 11) {
				if ((int) check[j][1] == 1) {
					cnt++;
					if (cnt == 5) {
						System.out.println("5개");
						return true;
					}
				} else {
					cnt = 0;
				}
			}
		}
		return false;
	}

	public boolean isCheckMate4() { // 왼쪽대각선
		int cnt = 0;
		for (int i = 90; i >= 0; i-=10) {
			for (int j = i; j >=0; j -= 9) {
				if ((int) check[j][1] == 1) {
					cnt++;
					if (cnt == 5) {
						System.out.println("5개");
						return true;
					}
				} else {
					cnt = 0;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Omok m = new Omok();
	}
}

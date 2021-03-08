package findDifference;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DifferenceImage extends JPanel {

	ImageIcon img1;
	JLabel imgbox;
	int imgNum;

	public DifferenceImage(String imgFile, int imgNum) {
		this.setLayout(new GridLayout(1,1));
		img1 = new ImageIcon(imgFile);
		imgbox = new JLabel(img1);
		imgbox.setBounds(0, 0, img1.getIconWidth(), img1.getIconHeight());
		add(imgbox);
		this.imgNum = imgNum;
		this.setBounds(0, 0, 984, 580);

	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
       
		super.paint(g);
		 Graphics2D g2=(Graphics2D)g;
		 g2.setColor(Color.RED);
		 g2.setStroke(new BasicStroke(5.0f));
		
			    
		//정답 좌표 배열의 인덱스(0~4)에 해당하는 정답 클릭 시, true가 반환되면서 if문 실행    
		if (DifferenceDao.member.isFound1() == true ) { //정답 좌표 배열의 인덱스[0]에 해당하는 정답 클릭
			
			if (imgNum == 1) { // 이미지1(첫 번째 스테이지)
				g2.drawOval(761 - 20, 89 - 100, 50, 50); //왼쪽 그림에 동그라미
				g2.drawOval(255 - 20, 89 - 100, 50, 50); //오른쪽 그림에 동그라미
				//첫번째 스테이지의 정답 좌표 배열의 인덱스[0]에 해당하는 정답 클릭시, 
				//양쪽 그림 모두에서 정답에 해당하는 부분 동그라미 표시 
				
			}
			if (imgNum == 2) {// 이미지2(첫 번째 스테이지)
				g2.drawOval(626 - 20, 236 - 100, 50, 50);
				g2.drawOval(108 - 20, 236 - 100, 50, 50);
				
			}
			if (imgNum == 3) {// 이미지3(두 번째 스테이지)
				g2.drawOval(900 - 20, 159 - 100, 50, 50);
				g2.drawOval(404 - 20, 159 - 100, 50, 50);
				
			}
			if (imgNum == 4) {// 이미지4(세 번째 스테이지)
				g2.drawOval(541 - 20, 390 - 100, 50, 50);
				g2.drawOval(42 - 20, 390 - 100, 50, 50);
				
			}
			if (imgNum == 5) {// 이미지5(마지막 스테이지)
				g2.drawOval(892 - 20, 108 - 100, 50, 50);
				g2.drawOval(379 - 20, 108 - 100, 50, 50);
				
			}
		}
		if (DifferenceDao.member.isFound2() == true) {//정답 좌표 배열의 인덱스[1]에 해당하는 정답 클릭
			if (imgNum == 1) {// 이미지1
				g2.drawOval(840 - 20, 361 - 100, 50, 50);
				g2.drawOval(342 - 20, 361 - 100, 50, 50);
			} 
			if (imgNum == 2) {// 이미지2
				g2.drawOval(959 - 20, 127 - 100, 50, 50);
				g2.drawOval(462 - 20, 127 - 100, 50, 50);
				
			}
			if (imgNum == 3) {// 이미지3
				g2.drawOval(855 - 20, 361 - 100, 50, 50);
				g2.drawOval(345 - 20, 361 - 100, 50, 50);
				
			}
			if (imgNum == 4) {// 이미지4
				g2.drawOval(955 - 20, 315 - 100, 50, 50);
				g2.drawOval(439 - 20, 315 - 100, 50, 50);
				
			}
			if (imgNum == 5) {// 이미지5
				g2.drawOval(884 - 20, 309 - 100, 50, 50);
				g2.drawOval(386 - 20, 309 - 100, 50, 50);
				
			}
		}
		if (DifferenceDao.member.isFound3() == true) {//정답 좌표 배열의 인덱스[2]에 해당하는 정답 클릭
			if (imgNum == 1) {// 이미지1
				g2.drawOval(752 - 20, 422 - 100, 50, 50);
				g2.drawOval(249 - 20, 422 - 100, 50, 50);
			}
			if (imgNum == 2) {// 이미지2
				g2.drawOval(934 - 20, 482 - 100, 50, 50);
				g2.drawOval(442 - 20, 482 - 100, 50, 50);
				
			}
			if (imgNum == 3) {// 이미지3
				g2.drawOval(878 - 20, 495 - 100, 50, 50);
				g2.drawOval(383 - 20, 495 - 100, 50, 50);
				
			}
			if (imgNum == 4) {// 이미지4
				g2.drawOval(671 - 20, 252 - 100, 50, 50);
				g2.drawOval(161 - 20, 252 - 100, 50, 50);
				
			}
			if (imgNum == 5) {// 이미지5
				g2.drawOval(784 - 20, 546 - 100, 50, 50);
				g2.drawOval(270 - 20, 546 - 100, 50, 50);
				
			}
		}
		if (DifferenceDao.member.isFound4() == true) {//정답 좌표 배열의 인덱스[3]에 해당하는 정답 클릭
			if (imgNum == 1) {// 이미지1
				g2.drawOval(898 - 20, 432 - 100, 50, 50);
				g2.drawOval(402 - 20, 432 - 100, 50, 50);
			
			}
			if (imgNum == 2) {// 이미지2
				g2.drawOval(961 - 20, 300 - 100, 50, 50);
				g2.drawOval(462 - 20, 300 - 100, 50, 50);
			
			}
			if (imgNum == 3) {// 이미지3
				g2.drawOval(573 - 20, 169 - 100, 50, 50);
				g2.drawOval(68 - 20, 169 - 100, 50, 50);
				
			}
			if (imgNum == 4) {// 이미지4
				g2.drawOval(860 - 20, 503 - 100, 50, 50);
				g2.drawOval(353 - 20, 503 - 100, 50, 50);
				
			}
			if (imgNum == 5) {// 이미지5
				g2.drawOval(708 - 20, 136 - 100, 50, 50);
				g2.drawOval(197 - 20, 136 - 100, 50, 50);
				
			}
		}///
        if(Difference.Wrong==true) { //틀린좌표 클릭시 해당 좌표에 엑스표시
        	g2.drawLine(Difference.point.x- 10+25 , Difference.point.y -80+25, Difference.point.x - 10-25, Difference.point.y-80-25 );
        	g2.drawLine(Difference.point.x- 10 -25 , Difference.point.y-80 +25, Difference.point.x - 10 +25, Difference.point.y -80-25);
        	Difference.Wrong =false; 
        }
		
		
		
	}

}

package sist.com.miniproject;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DifferenceImage extends JPanel {

	ImageIcon img1;
	JLabel imgbox;
	int imgNum;

	public DifferenceImage(String imgFile, int imgNum) {

		img1 = new ImageIcon(imgFile);
		imgbox = new JLabel(img1);
		imgbox.setBounds(0, 0, img1.getIconWidth(), img1.getIconHeight());
		add(imgbox);
		this.imgNum = imgNum;

	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
       
		super.paint(g);
		g.setColor(Color.RED);
		
			    
		if (DifferenceDao.member.isFound1() == true ) {
			
			if (imgNum == 1) { // 이미지1
				g.drawOval(761 - 20, 89 - 80, 50, 50);
				g.drawOval(255 - 20, 89 - 80, 50, 50);
				
			}
			if (imgNum == 2) {// 이미지2
				g.drawOval(626 - 20, 236 - 80, 50, 50);
				g.drawOval(108 - 20, 236 - 80, 50, 50);
				
			}
			if (imgNum == 3) {// 이미지3
				g.drawOval(900 - 20, 159 - 80, 50, 50);
				g.drawOval(404 - 20, 159 - 80, 50, 50);
				
			}
			if (imgNum == 4) {// 이미지4
				g.drawOval(541 - 20, 390 - 80, 50, 50);
				g.drawOval(42 - 20, 390 - 80, 50, 50);
				
			}
			if (imgNum == 5) {// 이미지5
				g.drawOval(892 - 20, 108 - 80, 50, 50);
				g.drawOval(379 - 20, 108 - 80, 50, 50);
				
			}
		}
		if (DifferenceDao.member.isFound2() == true) {
			if (imgNum == 1) {// 이미지1
				g.drawOval(840 - 20, 361 - 80, 50, 50);
				g.drawOval(342 - 20, 361 - 80, 50, 50);
			} 
			if (imgNum == 2) {// 이미지2
				g.drawOval(959 - 20, 127 - 80, 50, 50);
				g.drawOval(462 - 20, 127 - 80, 50, 50);
				
			}
			if (imgNum == 3) {// 이미지3
				g.drawOval(855 - 20, 361 - 80, 50, 50);
				g.drawOval(345 - 20, 361 - 80, 50, 50);
				
			}
			if (imgNum == 4) {// 이미지4
				g.drawOval(955 - 20, 315 - 80, 50, 50);
				g.drawOval(439 - 20, 315 - 80, 50, 50);
				
			}
			if (imgNum == 5) {// 이미지5
				g.drawOval(884 - 20, 309 - 80, 50, 50);
				g.drawOval(386 - 20, 309 - 80, 50, 50);
				
			}
		}
		if (DifferenceDao.member.isFound3() == true) {
			if (imgNum == 1) {// 이미지1
				g.drawOval(752 - 20, 422 - 80, 50, 50);
				g.drawOval(249 - 20, 422 - 80, 50, 50);
			}
			if (imgNum == 2) {// 이미지2
				g.drawOval(934 - 20, 482 - 80, 50, 50);
				g.drawOval(442 - 20, 482 - 80, 50, 50);
				
			}
			if (imgNum == 3) {// 이미지3
				g.drawOval(878 - 20, 495 - 80, 50, 50);
				g.drawOval(383 - 20, 495 - 80, 50, 50);
				
			}
			if (imgNum == 4) {// 이미지4
				g.drawOval(671 - 20, 252 - 80, 50, 50);
				g.drawOval(161 - 20, 252 - 80, 50, 50);
				
			}
			if (imgNum == 5) {// 이미지5
				g.drawOval(784 - 20, 546 - 80, 50, 50);
				g.drawOval(270 - 20, 546 - 80, 50, 50);
				
			}
		}
		if (DifferenceDao.member.isFound4() == true) {
			if (imgNum == 1) {// 이미지1
				g.drawOval(898 - 20, 432 - 80, 50, 50);
				g.drawOval(402 - 20, 432 - 80, 50, 50);
			
			}
			if (imgNum == 2) {// 이미지2
				g.drawOval(961 - 20, 300 - 80, 50, 50);
				g.drawOval(462 - 20, 300 - 80, 50, 50);
			
			}
			if (imgNum == 3) {// 이미지3
				g.drawOval(573 - 20, 169 - 80, 50, 50);
				g.drawOval(68 - 20, 169 - 80, 50, 50);
				
			}
			if (imgNum == 4) {// 이미지4
				g.drawOval(860 - 20, 503 - 80, 50, 50);
				g.drawOval(353 - 20, 503 - 80, 50, 50);
				
			}
			if (imgNum == 5) {// 이미지5
				g.drawOval(708 - 20, 136 - 80, 50, 50);
				g.drawOval(197 - 20, 136 - 80, 50, 50);
				
			}
		}

	}

}

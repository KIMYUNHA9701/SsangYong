package sist.com.miniproject;

import java.awt.Point;
import javax.swing.JOptionPane;


public class DifferenceDao  {
	
	static DifferenceImage currentImg;
	static DifferenceMember member;
	static boolean allFoundCheck;
	static boolean gameClearCheck;
	static boolean gameOverCheck;
	static int count = 1;
	static final int r = 15;
	static int clear = 0; //0�� �� gameover, 1�� �� gameclear
	private static DifferenceDao dDao;
	
	
	public DifferenceDao() {
	
	}


	public static DifferenceDao getDifferenceDao() {
		if(dDao ==null)
			dDao = new DifferenceDao();
		return dDao;
	}
	
	
	
	public void imgChange() {

		if (count == 1) {
			int[] correctX1 = { 761, 840, 752, 898 };
			int[] correctY1 = { 89, 361, 422, 432 };
			currentImg = new DifferenceImage("d:\\imagediffer\\img.png", 1);
			member = new DifferenceMember(correctX1, correctY1);
		}
		if (count == 2) {
			int[] correctX2 = { 626, 959, 934, 961 };
			int[] correctY2 = { 236, 127, 482, 300 };
			currentImg = new DifferenceImage("d:\\imagediffer\\img22.png", 2);
			member = new DifferenceMember(correctX2, correctY2);
		}
		if (count == 3) {
			int[] correctX3 = { 900, 855, 878, 573 };
			int[] correctY3 = { 159, 361, 495, 169 };
			currentImg = new DifferenceImage("d:\\imagediffer\\img333.png", 3);
			member = new DifferenceMember(correctX3, correctY3);
		}
		if (count == 4) {
			int[] correctX4 = { 541, 955, 671, 860 };
			int[] correctY4 = { 390, 315, 252, 503 };
			currentImg = new DifferenceImage("d:\\imagediffer\\img44.png", 4);
			member = new DifferenceMember(correctX4, correctY4);
		}
		if (count == 5) {
			int[] correctX5 = { 892, 884, 784, 708 };
			int[] correctY5 = { 108, 309, 546, 136 };
			currentImg = new DifferenceImage("d:\\imagediffer\\img55.png", 5);
			member = new DifferenceMember(correctX5, correctY5);
		}
	}
	
	
	public void isAllFound() {
		if (member.isFound1() == true && member.isFound2() == true && 
		    member.isFound3() == true && member.isFound4() == true) {
			clear = 1;
			JOptionPane.showMessageDialog(null, "stage clear!");
			dataReset();
			if(count ==5) {
				gameClearCheck = true;
				new GameClear();
				return;
			}else {
			count++;
			imgChange();  
		    clear =0; //�ʱⰪ���� �ʱ�ȭ
			new Difference();
			
			allFoundCheck = true;
		  }
			
	   }//if
	 }
	

	public  void dataReset() {
		//���� ���������� �Ѿ���� �����ؾ� �� ����Ÿ ����
		member.setFound1(false);
		member.setFound2(false);
		member.setFound3(false);
		member.setFound4(false);
		member.setOnce1(0);
		member.setOnce2(0);
		member.setOnce3(0);
		member.setOnce4(0);
		
	}

	public  void dataAllReset() {
		//�ʱ� ���·� ����Ÿ ����
		dataReset();
		count = 1;
		Difference.gamescore = 0;
		
		
	}
	
	public boolean correctPoint(int index, Point p){
		  int pointX = p.x;
		  int pointY = p.y;
		  
		  return (pointX >= member.getCorrectX()[index]-r) && (pointX <= member.getCorrectX()[index]+r)
		    && (pointY >= member.getCorrectY()[index]-r) && (pointY <=member.getCorrectY()[index]+r);
		 }
	
}

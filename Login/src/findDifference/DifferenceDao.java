package findDifference;

import java.awt.Point;
import javax.swing.JOptionPane;


public class DifferenceDao  {
	
	static DifferenceImage currentImg;
	static DifferenceMember member;
	static boolean allFoundCheck;
	static boolean gameClearCheck;
	static boolean gameOverCheck;
	static int count = 1;
	static final int r = 20;
	static int[] stagescore = {0,2000,4000,6000,8000};
	static int clear = 0; //0�� �� gameover, 1�� �� gameclear
	private static DifferenceDao dDao;
	static String id;
	
	public DifferenceDao(String id) {
		DifferenceDao.id = id;
	}
	
	public static void setId(String id) {
		DifferenceDao.id = id;
	}

	public static DifferenceDao getDifferenceDao() {
		if(dDao ==null)
			dDao = new DifferenceDao(id);
		return dDao;
	}
	
	
	
	public void imgChange() {

		if (count == 1) { // ù ��° ��������
			//���� ��ǥ
			int[] correctX1 = { 761, 840, 752, 898 };
			int[] correctY1 = { 89, 361, 422, 432 };
			//�̹��� ����(�̹��� ���, �̹��� ��ȣ)
			currentImg = new DifferenceImage("FDImage\\img.png", 1); 
			//�����ü ����. �����ڸ� ���� �ش� ���������� ���� ��ǥ�� �ش� �������� ���� �� ������ �Ǵ� �⺻ ������  �ʱ�ȭ
			member = new DifferenceMember(correctX1, correctY1,stagescore[0]);
		}
		if (count == 2) {// �� ��° ��������
			int[] correctX2 = { 626, 959, 934, 961 };
			int[] correctY2 = { 236, 127, 482, 300 };
			currentImg = new DifferenceImage("FDImage\\img22.png", 2);
			member = new DifferenceMember(correctX2, correctY2,stagescore[1]);
		}
		if (count == 3) {// �� ��° ��������
			int[] correctX3 = { 900, 855, 878, 573 };
			int[] correctY3 = { 159, 361, 495, 169 };
			currentImg = new DifferenceImage("FDImage\\img333.png", 3);
			member = new DifferenceMember(correctX3, correctY3,stagescore[2]);
		}
		if (count == 4) {// �� ��° ��������
			int[] correctX4 = { 541, 955, 671, 860 };
			int[] correctY4 = { 390, 315, 252, 503 };
			currentImg = new DifferenceImage("FDImage\\img44.png", 4);
			member = new DifferenceMember(correctX4, correctY4,stagescore[3]);
		}
		if (count == 5) {// ������ ��������
			int[] correctX5 = { 892, 884, 784, 708 };
			int[] correctY5 = { 108, 309, 546, 136 };
			currentImg = new DifferenceImage("FDImage\\img55.png", 5);
			member = new DifferenceMember(correctX5, correctY5,stagescore[4]);
		}
	}
	
	
	public void isAllFound() {
		//�ٸ� �κ� 4 �� ��� ã���� ��, if�� ����
		if (member.isFound1() == true && member.isFound2() == true && 
		    member.isFound3() == true && member.isFound4() == true) {
			clear = 1; //���α׷����ٰ�  ������ �ִ밪�� ������ ������ 
	                   //�ٸ� �κ� 4���� ��� ã�Ҵ��� Ȯ���Ͽ� ���ӿ��� â�� �����ϴ� ����  
			           
			JOptionPane.showMessageDialog(null, "stage clear!");
			dataReset();
			if(count ==5) {  //������ ��� ã�� ���������� ������ ���������� ��, if�� ���� 
				gameClearCheck = true;
				new GameClear(id);
				return;
			}else {   //������ ��� ã�� ���������� ������ ���������� �ƴ� ��, ���� 
		   //���� ���������� ���� ��, �������������� �ش��ϴ� �̹����� ������ǥ��  ����
			count++;  
			imgChange();  
		    clear =0; //�ʱⰪ���� �ʱ�ȭ
			new Difference();
			
			allFoundCheck = true;
		  }
			
	   }//if
	 }
	

	public  void dataReset() { //���� ���������� �Ѿ���� �����ؾ� �� ����Ÿ ����
		
		member.setFound1(false);
		member.setFound2(false);
		member.setFound3(false);
		member.setFound4(false);
		member.setOnce1(0);
		member.setOnce2(0);
		member.setOnce3(0);
		member.setOnce4(0);
		
	}

	public  void dataAllReset() {//�ʱ� ���·� ����Ÿ ����
		dataReset();
		//ù ��° ���������� ���� ������ �ʱ�ȭ
		count = 1; 
		Difference.gamescore = 0; 
		
		
	}
	
	public boolean correctPoint(int index, Point p){
		  int pointX = p.x;
		  int pointY = p.y;
		  //���콺�� Ŭ���� ����Ʈ��  ������ǥ�� x���� ��������  +20, -20, 
		  //������ǥ�� y���� ��������  +20, -20�� �����ȿ� ���� true��ȯ �ƴϸ� false��ȯ. 
		  return (pointX >= member.getCorrectX()[index]-r) && (pointX <= member.getCorrectX()[index]+r)
		    && (pointY >= member.getCorrectY()[index]-r) && (pointY <=member.getCorrectY()[index]+r);
		 }
	
}

package findDifference;

public class DifferenceMember {

	
	private int[] correctX = new int[4];
	private int[] correctY = new int[4];
	private boolean found1, found2, found3, found4;
	private int once1, once2, once3, once4;
	private int gamescore;
	private static int imgNumber;
	
	
	public DifferenceMember(int[] correctX, int[] correctY,int score) {
		super();
		this.correctX = correctX;
		this.correctY = correctY;
		this.gamescore = score;
	}
	
	
	
	public static int getImgNumber() {
		return imgNumber;
	}



	public static void setImgNumber(int imgNumber) {
		DifferenceMember.imgNumber = imgNumber;
	}


	public int[] getCorrectX() {
		return correctX;
	}
	public void setCorrectX(int[] correctX) {
		this.correctX = correctX;
	}
	public int[] getCorrectY() {
		return correctY;
	}
	public void setCorrectY(int[] correctY) {
		this.correctY = correctY;
	}
	public boolean isFound1() {
		return found1;
	}
	public void setFound1(boolean found1) {
		this.found1 = found1;
	}
	public boolean isFound2() {
		return found2;
	}
	public void setFound2(boolean found2) {
		this.found2 = found2;
	}
	public boolean isFound3() {
		return found3;
	}
	public void setFound3(boolean found3) {
		this.found3 = found3;
	}
	public boolean isFound4() {
		return found4;
	}
	public void setFound4(boolean found4) {
		this.found4 = found4;
	}
	public int getOnce1() {
		return once1;
	}
	public void setOnce1(int once1) {
		this.once1 = once1;
	}
	public int getOnce2() {
		return once2;
	}
	public void setOnce2(int once2) {
		this.once2 = once2;
	}
	public int getOnce3() {
		return once3;
	}
	public void setOnce3(int once3) {
		this.once3 = once3;
	}
	public int getOnce4() {
		return once4;
	}
	public void setOnce4(int once4) {
		this.once4 = once4;
	}
	public int getGamescore() {
		return gamescore;
	}
	public void setGamescore(int gamescore) {
		this.gamescore = gamescore;
	}
	

}

package findDifference;

public class GamePlay {
//���⼭ ���� !
	public GamePlay(String id) {
		DifferenceDao.setId(id);
		new GameStart();
	}
	
}

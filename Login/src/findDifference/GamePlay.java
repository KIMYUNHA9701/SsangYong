package findDifference;

public class GamePlay {
//여기서 시작 !
	public GamePlay(String id) {
		DifferenceDao.setId(id);
		new GameStart();
	}
	
}

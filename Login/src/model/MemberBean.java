package model;

public class MemberBean {
	private String id;
	private String pw;
	private int game1_score = 0;
	private int game2_score = 0;
	private int game3_score = 0;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getGame1_score() {
		return game1_score;
	}
	public void setGame1_score(int game1_score) {
		this.game1_score = game1_score;
	}
	public int getGame2_score() {
		return game2_score;
	}
	public void setGame2_score(int game2_score) {
		this.game2_score = game2_score;
	}
	public int getGame3_score() {
		return game3_score;
	}
	public void setGame3_score(int game3_score) {
		this.game3_score = game3_score;
	}
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", pw=" + pw + ", game1_score=" + game1_score + ", game2_score=" + game2_score
				+ ", game3_score=" + game3_score + "]";
	}
	
	
}

package json;



import com.fasterxml.jackson.annotation.JsonProperty;

public class LikeCount {
	

	// いいね数
	@JsonProperty("newLikeCount")
	private int newLikeCount;
	
	
	
	public int getNewLikeCount() {
		return newLikeCount;
	}

	public void setNewLikeCount(int newLikeCount) {
		this.newLikeCount = newLikeCount;
	}

	
	
}

package json;



import com.fasterxml.jackson.annotation.JsonProperty;

public class LikeInfo {
	
	

	// plus OR minus　か？
	@JsonProperty("like")
	private String like;
	
	//投稿ID
	@JsonProperty("postId")
	private String postId;
	
	//投稿した人のID
	@JsonProperty("postAccountId")
	private String postAccountId;
	
	
	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPostAccountId() {
		return postAccountId;
	}

	public void setPostAccountId(String postAccountId) {
		this.postAccountId = postAccountId;
	}
	
	
}

package dto;

import java.util.Set;

public class ChatRecordDTO {
	
	//フィールド宣言
	private String postId; //投稿ID
	private String accountId; //アカウントID
	private String accountName; //アカウント名
	private String icon; //アイコンのファイル名
	private String time; //投稿時間
	private String text; //チャット本文
	private String image; //投稿画像のファイル名
	private Integer goodCount; //いいねの数
	private Set<String> goodId; //いいねされた人のID
	
	//フィールド8つに対しての代入処理を行うコンストラクタ(仮引数)
	public ChatRecordDTO(String postId,String accountId,String accountName,
			String icon,String time,String text,String image,Integer goodCount) {
		this.postId = postId;
		this.accountId = accountId;
		this.accountName = accountName;
		this.icon = icon;
		this.time = time;
		this.text = text;
		this.image = image;
		this.goodCount = goodCount;
	}
	
	//getter＆setterの宣言
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getGoodCount() {
		return goodCount;
	}
	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}

	public Set<String> getGoodId() {
		return goodId;
	}

}

package dto;

public class UserProfileDTO implements java.io.Serializable {
	
	//privateでフィールドを作成してください。
	//格納用のアイコンファイル名、名前、ID、県名、自己紹介文、現在のいいねポイント数のフィールド
	private String accountIcon;
	private String accountName;
	private String accountId;
	private String accountKen;
	private String accountIntroduction;
	private String accountGoodPoint;
	
	
	/**
	 * プロフィール表示で必要な情報を格納するDTO
	 */
	//プロフィール画面にて、フィールドで作成した変数をインスタンス化に必要な情報として仮引数に指定
	public UserProfileDTO(String accountIcon,String accountName,String accountId,
							String accountKen,String accountIntroduction,String accountGoodPoint) {
		this.accountIcon = accountIcon;
		this.accountName = accountName;
		this.accountId = accountId;
		this.accountKen = accountKen;
		this.accountIntroduction = accountIntroduction;
		this.accountGoodPoint = accountGoodPoint;
	}
	//フィールドで作成した変数のgetterメソッド
	public String getAccountIcon() {
		return accountIcon;
	}
	
	
	public String getAccountName() {
		return accountName;
	}
	
	
	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	
	public String getAccountKen() {
		return accountKen;
	}
	
	
	public String getAccountIntroduction() {
		return accountIntroduction;
	}
	
	
	public String getAccountGoodPoint() {
		return accountGoodPoint;
	}
}
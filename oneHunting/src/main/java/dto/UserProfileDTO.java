package dto;

public class UserProfileDTO implements java.io.Serializable {
	
	//privateでフィールドを作成してください。
	private String accountIcon;
	private String accountName;
	private String accountId;
	private String accountKen;
	private String accountIntroduction;
	private String accountGoodPoint;
	
	
	/**
	 * プロフィール表示・編集で必要な情報を格納するDTO
	 */
	public UserProfileDTO(String accountIcon,String accountName,String accountId,
							String accountKen,String accountIntroduction,String accountGoodPoint) {
		this.accountId = accountId;
		this.accountName = accountName;
		this.accountKen = accountKen;
		this.accountIcon = accountIcon;
		this.accountIntroduction = accountIntroduction;
		this.accountGoodPoint = accountGoodPoint;
	}
}
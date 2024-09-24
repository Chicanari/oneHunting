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
	 * プロフィール表示で必要な情報を格納するDTO
	 */
	public UserProfileDTO(String accountIcon,String accountName,String accountId,
							String accountKen,String accountIntroduction,String accountGoodPoint) {
		this.accountIcon = accountIcon;
		this.accountName = accountName;
		this.accountId = accountId;
		this.accountKen = accountKen;
		this.accountIntroduction = accountIntroduction;
		this.accountGoodPoint = accountGoodPoint;
	}


	public String getAccountIcon() {
		return accountIcon;
	}


	public void setAccountIcon(String accountIcon) {
		this.accountIcon = accountIcon;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
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


	public void setAccountKen(String accountKen) {
		this.accountKen = accountKen;
	}


	public String getAccountIntroduction() {
		return accountIntroduction;
	}


	public void setAccountIntroduction(String accountIntroduction) {
		this.accountIntroduction = accountIntroduction;
	}


	public String getAccountGoodPoint() {
		return accountGoodPoint;
	}


	public void setAccountGoodPoint(String accountGoodPoint) {
		this.accountGoodPoint = accountGoodPoint;
	}
	
	
}
package dto;

public class UserProfileEditDTO implements java.io.Serializable{
	
	private String accountIcon;
	private String accountName;
	private String accountId;
	private String accountMail;
	private String accountKen;
	private String accountIntroduction;
	
	/**
	 * プロフィール編集で必要な情報を格納するDTO
	 */
	public UserProfileEditDTO(String accountIcon,String accountName,String accountId,
							String accountMail,String accountKen,String accountIntroduction) {
		this.accountIcon = accountIcon;
		this.accountName = accountName;
		this.accountId = accountId;
		this.accountMail = accountMail;
		this.accountKen = accountKen;
		this.accountIntroduction = accountIntroduction;
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

	public String getAccountMail() {
		return accountMail;
	}

	public void setAccountMail(String accountMail) {
		this.accountMail = accountMail;
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
	
	
}

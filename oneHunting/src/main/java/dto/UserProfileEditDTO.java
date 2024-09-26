package dto;

public class UserProfileEditDTO implements java.io.Serializable{
	//格納用のアイコンファイル名、名前、ID、メールアドレス、県名、自己紹介文の変数
	private String accountIcon;
	private String accountName;
	private String accountId;
	private String accountMail;
	private String accountKen;
	private String accountIntroduction;
	
	/**
	 * プロフィール編集で必要な情報を格納するDTO
	 */
	//プロフ編集にて、フィールドで作成した変数をインスタンス化に必要な情報として仮引数に指定
	public UserProfileEditDTO(String accountIcon,String accountName,String accountId,
							String accountMail,String accountKen,String accountIntroduction) {
		this.accountIcon = accountIcon;
		this.accountName = accountName;
		this.accountId = accountId;
		this.accountMail = accountMail;
		this.accountKen = accountKen;
		this.accountIntroduction = accountIntroduction;
	}
	
	//作成したフィールドの値を取得する用のgetter/書き換える用のsetterメソッドを作成
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

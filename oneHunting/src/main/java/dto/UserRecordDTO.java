package dto;

public class UserRecordDTO implements java.io.Serializable {
	
	//privateでフィールドを作成してください。
	private String accountId;
	private String accountPass;
	private String accountName;
	private String accountMail;
	private String accountIcon;
	
	
	
	/**
	 * 検索時に必要な情報を格納するDTO
	 */
	public UserRecordDTO(String accountId,String accountPass,String accountName,String accountMail,String accountIcon) {
		this.accountId = accountId;
		this.accountPass = accountPass;
		this.accountName = accountName;
		this.accountMail = accountMail;
		this.accountIcon = accountIcon;
	}


	//検索時に必要な各getter/setterメソッドの作成
	public String getAccountId() {
		return accountId;
	}
	
	
	public String getAccountPass() {
		return accountPass;
	}
	
	
	public String getAccountName() {
		return accountName;
	}
	
	
	public String getAccountMail() {
		return accountMail;
	}
	
	
	public String getAccountIcon() {
		return accountIcon;
	}
	
	
}
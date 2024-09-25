package dto;

public class UserRecordDTO implements java.io.Serializable {
	
	//privateでフィールドを作成してください。
	private String accountIcon;
	private String accountName;
	private String accountKen;
	//アイコン　名前　県を表示したい
	
	
	/**
	 * 検索時に必要な情報を格納するDTO
	 */
	//String accountId,String accountPass,String accountName,String accountMail,String accountIcon
	public UserRecordDTO(String accountIcon,String accountName,String accountKen) {
		this.accountIcon = accountIcon;
		this.accountName = accountName;
		this.accountKen = accountKen;
	}
	
	//検索時に必要な各getterメソッドの作成
	public String getAccountIcon() {
		return accountIcon;
	}
	
	
	public String getAccountName() {
		return accountName;
	}
	
	
	public String getAccountKen() {
		return accountKen;
	}
	
}
package dto;

public class UserRecordDTO implements java.io.Serializable {
	
	//privateでフィールドを作成してください。
	//格納用のアイコンファイル名、名前、県名のフィールド
	private String accountIcon;
	private String accountName;
	private String accountKen;
	
	
	/**
	 * 検索時に必要な情報を格納するDTO
	 */
	//検索結果画面にて、フィールドで作成した変数をインスタンス化に必要な情報として仮引数に指定
	public UserRecordDTO(String accountIcon,String accountName,String accountKen) {
		this.accountIcon = accountIcon;
		this.accountName = accountName;
		this.accountKen = accountKen;
	}
	
	//フィールドで作成した変数のgetterメソッドの作成
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
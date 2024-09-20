package model;

public class LoginErr {
	
	/**
	 * ログイン時の入力値がnullか空でないかのチェック
	 */
	public String login_InputValueCheck(String id, String pw) 
	{
		//ID,PWがnullまたは空白だったときはエラーメッセージを返す
		String errmessage = "";	
		if (id == null || id.equals("")) errmessage += "IDが入力されていません。<br>";
		if (pw == null || pw.equals("")) errmessage += "PWが入力されていません。<br>";
		
		return errmessage;
		
	}

}

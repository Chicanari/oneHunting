package model;


import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Part;


//プロフィールの登録や、編集に際して入力ミスがあったときにエラーメッセージを返すクラス
public class ProfileErr {
	
	/*
	 * アカウント登録時、入力値をチェックするためのメソッド
	 * 
	 * name:
	 *   文字数チェック（2-12）
	 * id:
	 *   文字数チェック（2-26）
	 *   半角英数字チェック	半角英数字（記号：@_-.）
	 *   重複チェック　※DAOで行う。
	 * パスワード:
	 *   文字数チェック（6-72）	
	 * メールアドレス:
	 *   入力規則チェック（6-319）
	 *   重複チェック　※DAOで行う。
	 * 県名:
	 *    未選択チェック							
	 * 
	 */
	public String account_InputValueCheck(String name, String id, String pw, String mail, String ken) {
		
		//画面上部にまとめて出すため、String＋<br>で作成し戻します
		String errmessage = "";	
		
		//nullチェック
		if (name == null) 	name="";
		if (id == null) 	id ="";
		if (pw == null) 	pw ="";
		if (mail == null) 	mail ="";
		if (ken == null) 	ken ="";
		
		/*
		 * 名前
		 */
		//文字数チェック（2-12）
		Pattern Pattern_nameMojisu = Pattern.compile("^.{2,12}$");
		Matcher nameMojisu = Pattern_nameMojisu.matcher(name);
		if(!nameMojisu.find()) errmessage += "名前は2～12文字で登録してください。<br>";
		
		/*
		 * ID
		 */
		//文字数チェック（2-26）
		Pattern Pattern_idMojisu = Pattern.compile("^.{2,26}$");
		Matcher idMojisu = Pattern_idMojisu.matcher(id);
		if(!idMojisu.find()) errmessage += "IDは2～26文字で登録してください。<br>";
		
		//半角チェック
		Pattern Pattern_idHankaku = Pattern.compile("^[a-zA-Z_0-9@_.-]{2,26}$");
		Matcher idHankaku = Pattern_idHankaku.matcher(id);
		if(!idHankaku.find()) errmessage += "IDは半角英数字（記号：@_-.）で入力してください。<br>";
		
		/*
		 * PW
		 */
		//文字数チェック（6-72）
		Pattern Pattern_pwMojisu = Pattern.compile("^[a-zA-Z_0-9@_.-]{6,72}$");
		Matcher pwMojisu = Pattern_pwMojisu.matcher(pw);
		if(!pwMojisu.find()) errmessage += "PWは6～72文字で入力してください。<br>";
		
		
		/*
		 * メールアドレス　
		 */
		//入力規則チェック（6-319）
		//ローカルパートが64文字、ドメインパートが254文字で、合計の長さは@を入れて319文字
		String local = "[a-z0-9]{1}[a-z0-9_.-]{0,63}"; //[a-z0-9]{1}→記号以外英数字　[a-z0-9_.-]{0,63}→記号含む英数字
		String domein = "[a-z0-9_.-]{4,254}";	// a.jp　ドメイン１文字、ドット、国ID（２文字）＝４文字が最小
		Pattern Pattern_mailKisoku = Pattern.compile("^"+local+"[@]"+domein+"$");
		Matcher mailKisoku = Pattern_mailKisoku.matcher(mail);
		if(!mailKisoku.find()) errmessage += "正しいメールアドレスを入力してください。<br>";
		
		/*
		 * 県名
		 */
		if(ken.equals("")) errmessage += "県名を設定してください。<br>";
		
		
		return errmessage;
		
	}
	
	
	/**
	 * アカウント編集時、入力値をチェックするためのメソッド
	 * 
	 * id
	 *	重複チェックDAOのアクセス用
	 * name:
	 *   文字数チェック（2-12）
	 * メールアドレス:
	 *   入力規則チェック（6-319）
	 *   重複チェック　※DAOで行う。
	 * 県名:
	 *    未選択チェック
	 * 自己紹介文
	 *	  文字数チェック (0～200)
	 * id,name,mail,ken						
	 * 
	 */
	public String account_EditValueCheck(String name, String mail,String ken,String text) {
		
		//画面上部にまとめて出すため、String＋<br>で作成し戻します
		String errmessage = "";	
		
		//nullチェック
		if (name == null) 	name="";
		if (mail == null) 	mail ="";
		if (ken == null) 	ken ="";
		if (text == null) 	text ="";
		
		/*
		 * 名前
		 */
		//文字数チェック（2-12）
		Pattern Pattern_nameMojisu = Pattern.compile("^.{2,12}$");
		Matcher nameMojisu = Pattern_nameMojisu.matcher(name);
		if(!nameMojisu.find()) errmessage += "名前は2～12文字で登録してください。<br>";
		
		//半角チェック
		Pattern Pattern_idHankaku = Pattern.compile("^[a-zA-Z_0-9@_.-]{2,26}$");
				
		/*
		 * メールアドレス　
		 */
		//入力規則チェック（6-319）
		//ローカルパートが64文字、ドメインパートが254文字で、合計の長さは@を入れて319文字
		String local = "[a-z0-9]{1}[a-z0-9_.-]{0,63}"; //[a-z0-9]{1}→記号以外英数字　[a-z0-9_.-]{0,63}→記号含む英数字
		String domein = "[a-z0-9_.-]{4,254}";	// a.jp　ドメイン１文字、ドット、国ID（２文字）＝４文字が最小
		Pattern Pattern_mailKisoku = Pattern.compile("^"+local+"[@]"+domein+"$");
		Matcher mailKisoku = Pattern_mailKisoku.matcher(mail);
		if(!mailKisoku.find()) errmessage += "正しいメールアドレスを入力してください。<br>";
		
		
		/*
		 * 県名
		 */
		if(ken.equals("")) errmessage += "県名を設定してください。<br>";
		
		
		/*
		 * /自己紹介文
		 */
		if(text.length() > 200) {
			errmessage += "200文字以内で設定してください。<br>";
		}
		
		return errmessage;
		
	}
	
	/**
	 * 
	 * ※画像ファイルの拡張子チェックメソッド
	 * 
	 */
	public static boolean isImageFile(Part part) {
	    // MIMEタイプを取得
	    String contentType = part.getContentType();
	    
	    // MIMEタイプが画像形式であるかを確認
	    if (contentType != null && (contentType.startsWith("image/"))) {
	        return true;
	    }
	    
	    // 拡張子を取得
	    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
	    String extension = "";

	    // ファイル名が存在する場合、拡張子を取得
	    if (fileName.lastIndexOf(".") != -1) {
	        extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	    }

	    // 許可された拡張子をチェック
	    switch (extension) {
	        case "jpg":
	        case "jpeg":
	        case "png":
	        case "gif":
	        case "bmp":
	        case "webp":
	        case "tiff":
	            return true;
	        default:
	            return false;
	    }
	}
}

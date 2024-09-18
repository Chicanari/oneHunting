package model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 【ハッシュ化について】
 * 
 * ①spring-security-crypto-5.5.0-M1.jar
 * ②spring-security-core-6.2.6.jar
 * ③commons-logging-1.2.jar
 * 
 * をDLし、libフォルダに保存→ビルドパスを通すと、
 * BCryptという安全性の高いハッシュ化のライブラリが使用できます。
 */

public class PwHash {

	/**
	 * 入力されたPWをハッシュ化するPW
	 */
	public String changePwHash(String pw) {
		
		//BCryptの準備
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    //ハッシュ化したPWの保存
	    String hashedPassword = encoder.encode(pw);
	    
		return hashedPassword;
		
	}
	
	
	/**
	 * 入力されたPWとDBのPWが一致するか調べるメソッド
	 */
	public boolean matchingPW(String input_pw,String db_pw) {
		
		//BCryptの準備
	    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
	    
	    //入力されたPWとDBのハッシュ化PWが一致するか
	    if (bcpe.matches(input_pw, db_pw)) {
			return true;
		}
		
		return false;
		
	}

}

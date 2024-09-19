package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dto.UserRecordDTO;

/*
 * 
 * 【　DAOについての覚書　】
 * 
 * ①JDBCドライバの読み込みを自動で行うように設定しています。
 * 　各クラスに Class.forName("org.postgresql.Driver");は書かなくて大丈夫です。
 * 
 * ②接続情報（url、user、password）をprivateフィールドにしています。
 * 
 * ③全てのクラスの戻り値をvoidにしているので、適宜変更をお願いします。
 * 
 * --------　↓豆知識的なところ↓　--------
 * 
 * ④sumpleLogin（）メソッドにtry-with-resourcesを使用し、
 * 　Connection、PreparedStatement、ResultSetをcloseするソースコードを書いています。
 * 　コードが短くなるので、もし分かれば使ってください。
 * 
 * ⑤/**　から始まるコメントについて
 * 　直下のメソッドにマウスオーバーすると、書き込んだコメントを表示してくれます。
 * 　HTML形式で書けるので、<br>で改行、<b>で太文字等可能です。
 * 
 * 　例えば、「アカウント登録機能　PWハッシュ化有」とコメントすると、signup()にマウスオーバーしたとき
 * 　「アカウント登録機能　PWハッシュ化有」と仮引数「id」「pw」が表示されます。
 * 
 */

public class AccountDAO {
	
	
	//接続情報
	private final String url = "jdbc:postgresql://localhost:5432/oneHunting";
	private final String user = "postgres";
	private final String password = "root";
		
	// JDBCドライバの読み込みを一括で行うことが可能
	// 各メソッドでJDBCドライバの読み込みが不要になります
	// 「静的イニシャライザ」という機能で、インスタンス化の前に１度のみ読み込まれます
    static {
    	
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした");
        }
        
        
    }
    
    
    //try-with-サンプル　※コードの参考にしてください
    public String sumpleLogin(String id,String pw) {
    	
    	
    	/* nullチェック */
    	//ID,PWがnullまたは空白だったときはエラーメッセージを返す
		String errmessage = "";	
		if (id == null || id.equals("")) errmessage += "IDが入力されていません<br>";
		if (pw == null || pw.equals("")) errmessage += "PWが入力されていません";
		if(!errmessage.isEmpty()) 	return errmessage;
		
		
		/* SQL文の作成 */
		//SQLでPWを取得
		String sql = "SELECT pw FROM account WHERE user_id = ?;";
		
		
		/* ResultSetで受け取るDB上のPWを格納する変数を宣言する */
		String db_pw = "";	//ResultSet
		
		
		/* SQL文の実行 */
		//try-with-resourcesで書くとスッキリします
		try(Connection con = DriverManager.getConnection(url,user,password);
			PreparedStatement ps = con.prepareStatement(sql);				
			ResultSet rs = ps.executeQuery()){
			
			//プレースホルダを設定
			ps.setString(1, id );
			
			//SELECT文の実行
			while(rs.next()) {
			db_pw = rs.getString("pw");
			}
			
			//ハッシュ化したPWと入力されたPWの一致を調べる
			//※モデルインポートが面倒なのでコメントアウト
			/*
			pwHash ph = new pwHash();
			if(ph.matchingPW(pw,db_pw)) {
				return "LOGIN OK";
			}else {
				return "PWが違います";
			}
			*/
			
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return errmessage;
    	
    	
    }
    
    
    /**
     * アカウントをDBに追加するDAOメソッド
     */
    public void userSignup() {
    	
    }
    
    /**
     * ログイン機能
     */
    public void userLogin() {
    	
    }
    
    /**
     * プロフィール編集機能
     */
    public void profileEdit() {
    	
    }
    
    /**
     * プロフィール表示機能
     */
    public void profileView() {
    	
    }
    
    /**
     * ユーザー検索機能
     */
    public List<UserRecordDTO> userSearch(String accountId,String accountName) {
    	Connection con = null;
    	PreparedStatement ps = null;
    	
    	//後ほど作成で
    	List<UserRecordDTO> userRecord = null;
    	
    	try {
    		con = DriverManager.getConnection(url,user,password);
    	}catch(Exception e) {
    		System.out.println("DBアクセスにエラーが発生しました。");
    		e.printStackTrace();
    	}finally {
    		
    		
    		return userRecord;
    	}
    	
    }
    
    //検索結果
    
    /**
     * 仮：いいねを追加するメソッド
     */
    public void like_add() {
    	
    }
    
    
    /**
     * 仮：いいねを削除するメソッド
     */
    public void like_delete() {
    	
    }
    

}
package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import json.GoodID;
import model.PwHash;


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
	private final String url = "jdbc:postgresql://localhost:5432/onehunting";
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
    public String userSignup(String name, String id, String pw, String mail, String ken) {
    	
    	//既に登録があるか判定するためのString
    	String isRegistered = "";
		
		//pwをハッシュ化
		PwHash ph = new PwHash();
		String hashPw = ph.changePwHash(pw);
		
		//sql文 項目7つ
		String sql = "INSERT INTO Account (";
		
		/*1*/ sql += "account_id";
		/*2*/ sql += ",account_password";
		/*3*/ sql += ",account_name";
		/*4*/ sql += ",account_mail";
		/*5*/ sql += ",account_ken";
		
		/*6*/ sql += ",account_good_point";
		/*7*/ sql += ",account_good_id";
		
		sql += ") VALUES ";
		sql += "(?,?,?,?,?,?,?);";
		
		
		//SQL文の実行
		try(Connection con = DriverManager.getConnection(url,user,password);
			PreparedStatement ps = con.prepareStatement(sql);				){
			
			//プレースホルダを設定
			ps.setString(1, id );
			ps.setString(2, hashPw );
			ps.setString(3, name );
			ps.setString(4, mail );
			ps.setString(5, ken );
			
			ps.setInt(6, 0 );
			ps.setString(7, null );
			
			//INSERT文の実行
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				return "";  // 追加できたら成功
	        }
			
		} catch (PSQLException e) {
			System.err.println("SQLエラー: " + e.getMessage()); // 詳細なエラー情報を表示
			isRegistered = "false";
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		//登録に失敗した場合、IDかメールアドレスが重複している
		if(isRegistered.equals("false")) {
			
			return mailID_tyoufukuCheck(id,mail);
			
		}
		
		return "";
    }
    
    private String mailID_tyoufukuCheck(String id, String mail) {
    	
    	//戻り値のメッセージ
    	String errMessage = "";
    	
    	/*
    	 * idの重複の確認 
    	 */
    	String idSQL = "SELECT account_id FROM Account WHERE account_id = ?;";
    	//SQL文の実行
		try(Connection con = DriverManager.getConnection(url,user,password);
			PreparedStatement ps = con.prepareStatement(idSQL);				){
			
			//プレースホルダを設定
			ps.setString(1, id );
			
			//SELECT文の実行
			try (ResultSet rs = ps.executeQuery()) {
				
				//リザルトセットの中にデータがある場合
		        if (rs.next()) {
		            // データが存在する場合の処理
		            errMessage += "IDが既に存在します。<br>";
		        }
				
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		
		/*
    	 * account_mailの重複の確認 
    	 */
    	String mailSQL = "SELECT account_id FROM Account WHERE account_mail = ?;";
    	//SQL文の実行
		try(Connection con = DriverManager.getConnection(url,user,password);
			PreparedStatement ps = con.prepareStatement(mailSQL);				){
			
			//プレースホルダを設定
			ps.setString(1, mail );
			
			//SELECT文の実行
			try (ResultSet rs = ps.executeQuery()) {
				
				//リザルトセットの中にデータがある場合
		        if (rs.next()) {
		            // データが存在する場合の処理
		            errMessage += "メールアドレスが既に存在します。<br>";
		        }
				
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return errMessage;
    	
    }
    
    /**
     * ログイン機能
     */
    public String userLogin(String id, String pw) {
    	
    	//エラーメッセージ（戻り値）を格納する変数
    	String errmessage = "";	
    	
		//SQLでPWを取得
		String sql = "SELECT account_password FROM account WHERE account_id = ?;";
		
		//戻り値のリストの宣言
		String db_pw = null;
		
		//SQL文の実行
		try(Connection con = DriverManager.getConnection(url,user,password);
			PreparedStatement ps = con.prepareStatement(sql);				){
			
			//プレースホルダを設定
			ps.setString(1, id );
			
			//SELECT文の実行
			try (ResultSet rs = ps.executeQuery()) {
				//ResultSetの結果のPWを取得する
				if(rs.next()) {
					//idで検索し、一致するPWがあった場合はdb_pwにPWを格納し、下記【PWの一致を調べる】でPWが合っているか調べる
					db_pw = rs.getString("account_password");
				}else {
					//一致するidがなかった場合はエラーメッセージを返す
					return "登録がありません。";
				}
            }
			
			//PWの一致を調べる
			PwHash ph = new PwHash();
			if(ph.matchingPW(pw,db_pw)) {
				System.out.println("LOGIN OK");
				return "LOGIN OK";
			}else {
				System.out.println("パスワードが違います。");
				return "パスワードが違います。";
			}
			
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		
		return errmessage;
    	
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
    public void userSearch() {
    	
    }
    
    
    /**
     * アカウントテーブルのいいねポイントを追加・削除、いいねした投稿の一覧を操作するメソッド
     * @throws JsonProcessingException 
     */
    public void like_update(String postId, String postAccountId, String updateType) throws JsonProcessingException {
    	
    	/**
    	 * このメソッドでは下記の順番・方法でいいね更新を実現しています。
    	 * 
    	 * ①チャットテーブルをpostId（投稿ID）で検索し、****_good_idに格納されているJSONファイルを取得する。
    	 * ②取得したJSONファイルがnullの場合は新規JSONファイルを作成する。
    	 * ③取得したJSONファイルがnullではない場合は、jsonパッケージのGoodIDクラスを使用する。
    	 * ④２、３、いずれかのＪＳＯＮファイルに、ログインＩＤを追加・減少する。
    	 * ⑤ＤＢにＪＳＯＮファイルを格納し、いいね数を増加・減少させる。
    	 * 
    	 */
    	
    	
    	/**
    	 * ①投稿からJSON形式のいいね一覧を取得する 例：{"goodID": ["id1", "id2", "id3"]}
    	 */
    	String JsonSql = "SELECT account_good_id FROM account WHERE account_id = ?;";
    	
    	//SQL文でJSONファイルをStringで受け取るための変数
    	String goodId_Json = "";
	    
	    // DBに格納されたJSON形式のデータを扱うためのJSONクラス
    	GoodID goodId = new GoodID();
    	
    	// ObjectMapperを初期化
	    ObjectMapper objectMapper = new ObjectMapper();
    	
    	
    	//SQL文の実行
		try(Connection con = DriverManager.getConnection(url,user,password);
			PreparedStatement ps = con.prepareStatement(JsonSql);			){
			
			//プレースホルダを設定
			ps.setString(1, postAccountId );
			
			//SELECT文の実行
			try (ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					//JSONファイルが存在しない場合はnull、存在する場合はStringを返す
					goodId_Json = rs.getString("account_good_id");
				}
            }
			
			
			/**
			 *  いいねボタンを押下をしたpostIdを、****_good_idに追加・削除する
			 */
			
			if(updateType.equals("plus")) {
				
				if(goodId_Json == null) {
					//②④goodIdsがnullの場合は、新しくJSONファイルを作成し、追加する
			        goodId.addIds(postId);
				}else {
					//③goodIdsがnullでない場合は、JSONファイルにuserIDを追加する
					
					//JSON形式のStringをGoodIDオブジェクトに変換
				    goodId = objectMapper.readValue(goodId_Json, GoodID.class);
				    
				    //④IDを追加する
				    goodId.addIds(postId);
				}
				
			} else if(updateType.equals("minus")) {
				
				//③JSONファイルのuserIDを削除する
				
				//JSON形式のStringをGoodIDオブジェクトに変換
			    goodId = objectMapper.readValue(goodId_Json, GoodID.class);
			    
			    //④IDを削除する
			    goodId.removeIds(postId);
				
			}

		} catch (SQLException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		
		
		/**
		 * 加工したJSONファイルを更新する
		 */
		
		//GoodIDクラスのオブジェクトを、String形式のJSONファイルに変換する
		String jsonString = objectMapper.writeValueAsString(goodId);
		
		//更新するためのSQLを格納する変数
		String UpdateSql ="";
		
		if(updateType.equals("plus")) {
	    	/**
	    	 *  ⑤一致するIDのいいねの数を１増やし、JSONファイルのいいねアカウント一覧を更新する
	    	 */
	    	UpdateSql = "UPDATE account SET account_good_point = ";
	    	/*1*/UpdateSql += "account_good_point+1 ,account_good_id = ?::json ";
	    	/*2*/UpdateSql += "WHERE account_id = ? ;";
		}else if(updateType.equals("minus")) {
			/**
	    	 *  ⑤一致するIDのいいねの数を１減らし、JSONファイルのいいねアカウント一覧を更新する
	    	 */
			UpdateSql = "UPDATE account SET account_good_point = ";
	    	/*1*/UpdateSql += "account_good_point-1 ,account_good_id = ?::json ";
	    	/*2*/UpdateSql += "WHERE account_id = ? ;";
		}
		
		
		//SQL文の実行
		try(Connection con = DriverManager.getConnection(url,user,password);
			PreparedStatement ps = con.prepareStatement(UpdateSql);			){
			
			//プレースホルダを設定
			ps.setString(1, jsonString );
			ps.setString(2, postAccountId );
			
			//INSERT文の実行
			ps.executeUpdate();
			
		} catch (PSQLException e) {
			System.err.println("SQLエラー: " + e.getMessage()); // 詳細なエラー情報を表示
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    	
    	
    }
    
    


}
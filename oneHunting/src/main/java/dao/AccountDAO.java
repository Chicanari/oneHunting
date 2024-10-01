package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PSQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.UserProfileDTO;
import dto.UserProfileEditDTO;
import dto.UserRecordDTO;
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
    
    
    /**
     * アカウントをDBに追加するDAOメソッド
     */
    public String userSignup(String name, String id, String pw, String mail, String ken, String icon) {
    	
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
		/*7*/ sql += ",account_icon";
		
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
			ps.setString(7, icon );
			
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
     * mail重複確認用のメソッド
     * 
     */
    public String mail_tyoufukuCheck(String id, String mail) {
    	
    	//戻り値のメッセージ
    	String errMessage = "";
    	
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
    //仮引数はid,name,mail,ken,icon,introduction
    public boolean profileEdit(String Id,String name,
    		String mail,String ken,
    		String icon,String introduction) {
    
    	//データベース操作に必要なオブジェクト3の実装
    	Connection con = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	//結果を返す変数
    	UserProfileEditDTO profileEdit = null;
    	
    	//
    	try {
    		con = DriverManager.getConnection(url,user,password);
    		
    		String sql = "UPDATE account ";
    		sql += "SET account_name = ?, "; //1
    		sql += "account_mail = ?, "; //2
    		sql += "account_ken = ?, "; //3
    		sql += "account_icon = ?, "; //4
    		sql += "account_introduction = ? "; //5
    		sql += "WHERE account_id = ? ;"; //6
    		ps = con.prepareStatement(sql);
    		
    		//パラメータの設定
    		ps.setString(1, name);          // account_name
    		ps.setString(2, mail);          // account_mail
    		ps.setString(3, ken);           // account_ken
    		ps.setString(4, icon);          // account_icon
    		ps.setString(5, introduction);   // account_introduction
    		ps.setString(6, Id);            // WHERE条件の account_id
    		
            // 更新を実行
            ps.executeUpdate();
    		
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		if(con != null) {
    			try {
    				con.close();
    			}catch(Exception e) {
    				;
    			}
    		}
    		
    		if(ps != null) {
    			try {
    				ps.close();
    			}catch(Exception e) {
    				;
    			}
    		}
    		if(rs != null) {
    			try {
    				rs.close();
    			}catch(Exception e){
    				;
    			}
    		}
    	} 	
    	return true;
    }
    
    /**
     * プロフィール表示機能
     */
    public UserProfileDTO profileView(String accountId) {
    	
    	/*
    	 * Q:コメントをつけてください
    	 */
    	
    	//以下3つDB関連の変数
    	//データベースへの接続や操作するオブジェクト
    	Connection con = null;
    	//プレースホルダーを含むsql文を実行するオブジェクト
    	PreparedStatement ps = null;
    	//実行結果を表すオブジェクト
    	ResultSet rs = null;
    	//結果を返す用の変数
    	UserProfileDTO userProfile = null;
    	
    	//SQL文の操作
    	try {
    		con = DriverManager.getConnection(url,user,password);
    		//sql文の操作
    		String sql = "SELECT account_icon, account_name, account_id, account_mail, account_ken, account_introduction, account_good_point ";
    		sql += "FROM account ";
    		//セキュリティ対策のためaccountIdをプレースホルダー化
    		sql += "WHERE account_id = ? ;";
    		//実行前にコンパイル処理
    		ps = con.prepareStatement(sql);
    		//プレースホルダーへ仮引数accountIdを格納
    		ps.setString(1,accountId);
    		//結果を実行
    		rs = ps.executeQuery();
    		
    		/*
    		 * Q:なぜ１行のみだったらifが適切なのでしょうか？
    		 */
    		//→whileだと複数行あれば全て取得。ifだと複数行でも最初の行のみ取得する・・・とのこと
    		
    		//プロフィール表示に必要な情報を変数へrs格納
    		//結果が1行のみならif文が適切とのことなので
    		if(rs.next()) {
    			//以下変数へ各カラム名を格納
    			//account_iconを格納
    			String accountIcon = rs.getString("account_icon");
    			//account_nameを格納
    			String accountName = rs.getString("account_name");
    			//account_mailを格納
    			String accountMail = rs.getString("account_mail");
    			//AccountIdは重複のためresultを追加、account_idを格納
    			String resultAccountId = rs.getString("account_id");
    			//account_kenを格納
    			String accountKen = rs.getString("account_ken");
    			//account_introductionを格納
    			String accountIntroduction = rs.getString("account_introduction");
    			//account_good_pointを格納
    			String accountGoodPoint = rs.getString("account_good_point");
    			//上記変数を、DTOをインスタンス化する際に代入
    			userProfile = new UserProfileDTO(accountIcon,accountName,accountMail,resultAccountId,
    											accountKen,accountIntroduction,accountGoodPoint);
    		}
    		
    		
    	//エラーメッセージを表示
    	}catch(Exception e) {
    		e.printStackTrace();
    		//後ほどtry-with-resourcesで省略すること
    		//Connection、PreparedStatement、ResultSetの変数３つを閉じる(切断する)処理
    	}finally {
    		if(con != null) {
    			try {
    				con.close();
    			}catch(Exception e) {
    				;
    			}
    		}
    		
    		if(ps != null) {
    			try {
    				ps.close();
    			}catch(Exception e) {
    				;
    			}
    		}
    		if(rs != null) {
    			try {
    				rs.close();
    			}catch(Exception e){
    				;
    			}
    		}
    	}
    	
    	//UserProfileDTOのインスタンス化を、代入したuserProfileを結果として返す
    	return userProfile;
    }
    
    /**
     * ユーザー検索機能
     */
    //あいまい検索で必要なaccountIdとaccountNameを仮引数searchQueryに指定
    public List<UserRecordDTO> userSearch(String searchQuery) {
    	Connection con = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	List<UserRecordDTO> userRecords = new ArrayList<>();
    	
    	//tryでSQL文で操作
    	try {
    		con = DriverManager.getConnection(url,user,password);
    		//SQL文で表示結果を操作
    		String sql = "SELECT account_id,account_icon, account_name, account_ken ";
    		sql += "FROM account ";
    		sql += "WHERE account_id LIKE ? ";//1で格納
    		sql += "OR account_name LIKE ? ";//2で格納
    		sql += "ORDER BY account_name, account_id";
    		
    		ps = con.prepareStatement(sql);
    		//ID・名前であいまい検索し実行
    		String ambiguousQuery = "%" + searchQuery + "%";
    		ps.setString(1, ambiguousQuery);
    		ps.setString(2, ambiguousQuery);
    		
    		rs = ps.executeQuery();
    		userRecords = searchResults(rs);
    		
    	}catch(Exception e) {
    		System.out.println("DBアクセスにエラーが発生しました。");
    		e.printStackTrace();
    	}finally {
    		//DB切断用if文
    		if(con != null) {
    			try {
    				con.close();
    			}catch(Exception e) {
    				;
    			}
    		}
    		
    		if(ps != null) {
    			try {
    				ps.close();
    			}catch(Exception e) {
    				;
    			}
    		}
    		if(rs != null) {
    			try {
    				rs.close();
    			}catch(Exception e){
    				;
    			}
    		}
    		
    		
    	}
    	return userRecords;
    }
    
    //検索結果をリスト化するArrayList<>のメソッドを作成
    public ArrayList<UserRecordDTO> searchResults(ResultSet rs) throws Exception{
    	ArrayList<UserRecordDTO> userRecords = new ArrayList<UserRecordDTO>();
    	//検索結果を取得しUserRecordDTOへ格納し、ArrayListへ格納する
    	while(rs.next()) {
    		//結果表示
    		String accountId = rs.getString("account_id");
    		String accountIcon = rs.getString("account_icon");
    		String accountName = rs.getString("account_name");
    		String accountKen = rs.getString("account_ken");
    		UserRecordDTO userRecord = 
    				new UserRecordDTO(accountId,accountIcon,accountName,accountKen);
    		userRecords.add(userRecord);
    	}
    	//
    	return userRecords;
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
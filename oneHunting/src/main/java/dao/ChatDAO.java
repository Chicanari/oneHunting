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
 * AccountDAOに豆知識書いてるので、コード短くしたい等あればご確認お願いします。
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Set;

import org.postgresql.util.PSQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.ChatRecordDTO;
import json.GoodID;

public class ChatDAO {
	
	
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
     * コメントをDBに追加するDAOメソッド
     * chatType,accountId,accountNme,icon,text,image
     */
    public int comment_insert(String  chatType,
    		String accountId,String accountName,
    		String icon,String text, String image) {
		ArrayList<ChatRecordDTO> chatRecords = new ArrayList<ChatRecordDTO>();
    	
		Connection con = null;
		PreparedStatement ps = null;
		
		//更新数
		int inCunt = 0;
		
    	try {
    		
    		//チャットタイプ確認用
    		System.out.println("comment_view chatType:"+chatType);
    		
    		//PostgresSQLへの接続
    		con = DriverManager.getConnection(url,user,password);
    		
    		//SQL文(チャットテーブルの呼び出し)
    		/**
    		 * ※いいね周りはnullを指定
    		 */
    		String sql = "";
			sql = ("INSERT INTO " + chatType 
					+ "(" + chatType  + "_post_id,"
					+ chatType  + "_account_id,"
					+ chatType  + "_account_name,"
					+ chatType  + "_icon,"
					+ chatType  + "_time."
					+ chatType  + "_text,"
					+ chatType  + "image,"
					+ chatType  + "_good_count,"
					+ chatType  + "_good_id) ");
			
			sql += "VALUES(?, ?, ?, ?, ?, ?, ?, null, null);";	
			
			
			//※postIdとtimeはDAO内で定義して処理する…予定
			
			//postIdの自動決定
			//※後で自動決定するメソッドを持ってくる
			String postId = "";
			
			//現在日時取得
			Timestamp time = new Timestamp(System.currentTimeMillis());
			
			ps = con.prepareStatement(sql);
			ps.setString(1,postId);
			ps.setString(2,accountId);
			ps.setString(3,accountName);
			ps.setString(4,icon);
			ps.setTimestamp(5,time);
			ps.setString(6,text);
			ps.setString(7,image);
    		
    		
    		//呼び出したチャットテーブルの格納
    		ps = con.prepareStatement(sql);
    		
    		//INSERTの実行
    		inCunt = ps.executeUpdate();
    		
		}catch(Exception e) {
			System.out.println("DBアクセスにエラーが発生しました");
			e.printStackTrace();
		}finally {
			//PostgreSQLの切断
			if(ps != null) {
				try {
					ps.close();
				}catch(Exception e) {
					;
				}
			}
			
			if(con != null) {
				try{
					con.close();
				}catch(Exception e) {
					;
				}
			}
		}		
    	return inCunt;
    }
    
    /**
     * 表示するコメントを取得するDAOメソッド
     * chatTypeで呼び出すチャットを判別する
     * 
     */
    public ArrayList<ChatRecordDTO> comment_view(String chatType) throws Exception {
    	
		ArrayList<ChatRecordDTO> chatRecords = new ArrayList<ChatRecordDTO>();
    	
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
    	try {
    		
    		//チャットタイプ確認用
    		System.out.println("comment_view chatType:"+chatType);
    		
    		//PostgresSQLへの接続
    		con = DriverManager.getConnection(url,user,password);
    		
    		//SQL文(チャットテーブルの呼び出し)
    		String sql = "SELECT * FROM " + chatType + " ORDER BY "+ chatType + "_time;";
    		
    		//呼び出したチャットテーブルの格納
    		ps = con.prepareStatement(sql);
    		
    		//SELECTの実行
    		rs = ps.executeQuery();
    		
    		
    		while(rs.next()) {	
    			
    			//1レコード分のデータを取得
    			String postId = rs.getString(chatType+ "_post_id");
    			String accountId = rs.getString(chatType+ "_account_id");
    			String accountName = rs.getString(chatType + "_account_name");
				String icon = rs.getString(chatType + "_icon");
				String time = rs.getString(chatType + "_time");
				String text = rs.getString(chatType + "_text");
				String image = rs.getString(chatType + "_image");
				Integer goodCount = ((Integer)rs.getInt(chatType + "_good_count")).equals(null) ? 0 : rs.getInt(chatType + "_good_count") ;
				String goodIdList = rs.getString(chatType + "_good_id");
				
				/*
				 * goodIdListをsetにする
				 */
				// DBに格納されたJSON形式のデータを扱うためのJSONクラス
		    	GoodID goodId = new GoodID();
		    	// ObjectMapperを初期化
			    ObjectMapper objectMapper = new ObjectMapper();
			    //StringのJSON形式のファイルを、goodIdクラスの構造体に格納する
			    if(goodIdList != null) 	goodId = objectMapper.readValue(goodIdList, GoodID.class);
			    //goodIdのオブジェクトからアカウントリスト（Set）を取得
				Set<String> goodIdSet = goodId.getIds();
				
				//1レコード分のデータを格納するインスタンスの生成
				ChatRecordDTO cRecord = new ChatRecordDTO(postId,accountId,accountName,icon,time,text,image,goodCount,goodIdSet);
				
				//取得したデータを格納
				chatRecords.add(cRecord);
    		}
    	}catch(Exception e) {
			System.out.println("DBアクセスにエラーが発生しました");
			e.printStackTrace();
    	}finally{
			//PostgreSQLの切断
			if(rs != null) {
				try {
					rs.close();
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
			if(con != null) {
				try{
					con.close();
				}catch(Exception e) {
					;
				}
			}
    	}
    	return chatRecords;    	  
    }
    
    /**
     * チャットテーブルのいいね数を１増加させ、いいねしたアカウントを追加するメソッド
     */
    public void like_add(String chatType,String postId, String loginID) throws JsonProcessingException {
    	
    	/**
    	 * このメソッドでは下記の順番・方法でいいね増加を実現しています。
    	 * 
    	 * ①チャットテーブルをpostId（投稿ID）で検索し、****_good_idに格納されているJSONファイルを取得する。
    	 * ②取得したJSONファイルがnullの場合は新規JSONファイルを作成する。
    	 * ③取得したJSONファイルがnullではない場合は、jsonパッケージのGoodIDクラスを使用する。
    	 * ④２、３、いずれかのＪＳＯＮファイルに、ログインＩＤを追加する。
    	 * ⑤ＤＢにＪＳＯＮファイルを格納し、いいね数を増加させる。
    	 * 
    	 */
    	
    	
    	/**
    	 * ①投稿からJSON形式のいいね一覧を取得する 例：{"goodID": ["id1", "id2", "id3"]}
    	 */
    	String JsonSql = "SELECT " +chatType+ "_good_id  FROM "+ chatType + " WHERE " +chatType+ "_post_id = ?;";
    	
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
			ps.setString(1, postId );
			
			//SELECT文の実行
			try (ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					//JSONファイルが存在しない場合はnull、存在する場合はStringを返す
					goodId_Json = rs.getString(chatType+"_good_id");
				}
            }
			
			
			/**
			 *  いいねをしたaccount_idを、****_good_idに追加する
			 */
	    	
			if(goodId_Json == null) {
				//②④goodIdsがnullの場合は、新しくJSONファイルを作成し、追加する
		        goodId.addIds(loginID);
			}else {
				//③goodIdsがnullでない場合は、JSONファイルにuserIDを追加する
				
				//JSON形式のStringをGoodIDオブジェクトに変換
			    goodId = objectMapper.readValue(goodId_Json, GoodID.class);
			    
			    //④IDを追加する
			    goodId.addIds(loginID);
			}
	    	

		} catch (SQLException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		
		//StringのJSONファイルに変換する
		String jsonString = objectMapper.writeValueAsString(goodId);

		
    	/**
    	 *  ⑤一致するIDのいいねの数を１増やし、JSONファイルのいいねアカウント一覧を更新する
    	 */
    	String UpdateSql = "UPDATE " +chatType+ " SET "+ chatType + "_good_count = ";
    	/*1*/UpdateSql += chatType + "_good_count+1 ,"+ chatType + "_good_id = ?::json ";
    	/*2*/UpdateSql += "WHERE " + chatType + "_post_id = ? ;";
		
		
		//SQL文の実行
		try(Connection con = DriverManager.getConnection(url,user,password);
			PreparedStatement ps = con.prepareStatement(UpdateSql);			){
			
			//プレースホルダを設定
			ps.setString(1, jsonString );
			ps.setString(2, postId );
			
			//INSERT文の実行
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
	        }
			
		} catch (PSQLException e) {
			System.err.println("SQLエラー: " + e.getMessage()); // 詳細なエラー情報を表示
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    	
    }
    
    
    /**
     * いいねを削除するメソッド
     * @param chatType 
     * @param postId 
     * @param loginID 
     */
    public void like_delete(String chatType, String postId, String loginID) {
    	
    }
    
    
    

}

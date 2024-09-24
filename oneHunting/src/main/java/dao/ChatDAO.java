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
import java.util.ArrayList;

import dto.ChatRecordDTO;

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
     */
    public void comment_insert() {
    	
    	
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
    		
    		//PostgresSQLへの接続
    		con = DriverManager.getConnection(url,user,password);
    		
    		//SQL文(チャットテーブルの呼び出し)
    		String sql = "SELECT * FROM " + chatType + " ORDER BY "+ chatType + "_time;";
    		
    		//呼び出したチャットテーブルの格納
    		ps = con.prepareStatement(sql);
    		
    		//SELECTの実行
    		rs = ps.executeQuery();
    		
    		
    		while(rs.next()) {	
    			/**
    			 * ※メモ用
    			 * chat_『main』_account_id
    			 * この部分に引数を渡して読み込み先を変える？
    			 * 
    			 * 一旦、mainで実装
    			 */
    			//1レコード分のデータを取得
    			String postId = rs.getString(chatType+ "_post_id");
    			String accountId = rs.getString(chatType+ "_account_id");
    			String accountName = rs.getString(chatType + "_account_name");
				String icon = rs.getString(chatType + "_icon");
				String time = rs.getString(chatType + "_time");
				String text = rs.getString(chatType + "_text");
				String image = rs.getString(chatType + "_image");
				Integer goodCount = ((Integer)rs.getInt(chatType + "_good_count")).equals(null) ? 0 : rs.getInt(chatType + "_good_count") ;
				
				//1レコード分のデータを格納するインスタンスの生成
				ChatRecordDTO cRecord = new ChatRecordDTO(postId,accountId,accountName,icon,time,text,image,goodCount);
				
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

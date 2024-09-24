package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatUtility {
	
	/**
	 * チャットテーブルに保存するPostIDを生成するメソッド
	 */
	public static String createPostID(String userID) {
		
		/**
		 * ＊postIDの仕様＊
		 * 投稿日時をミリ秒2桁まで(16桁) ＋ ユーザID(2～26桁)で登録
		 */
		
		/*
		 * 投稿日時をミリ秒2桁まで(16桁) の作成
		 */
		//現在の日時を取得する
		Date nowDate = new Date();
		//nowDateの情報を整形する：例20240924170525878
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		//String形式に変換(17桁)
		String date_17digit = df.format(nowDate);
		//１６桁に整形する
		String date_16digit = date_17digit.substring(0, 16);
		
		/*
		 * ユーザID(2～26桁)を足す
		 */
		String postID = date_16digit + userID;
		
		return postID;
		
	}

}

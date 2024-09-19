package model;

import java.nio.file.Paths;

import javax.servlet.http.Part;

public class ProfileErr {

	//プロフィールの登録や、編集に際して入力ミスがあったときにエラーメッセージを返すクラス
	public ProfileErr() {
		// TODO 自動生成されたコンストラクター・スタブ
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

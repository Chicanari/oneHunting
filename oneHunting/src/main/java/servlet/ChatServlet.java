package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/*
 * 
 * request.setCharacterEncoding("UTF-8");
 * 
 * フィルターという機能で一括設定しましたので、上記の記述は全てのサーブレットで不要です。
 * ※filterパッケージのSetEncodingFilterで設定しています。教科書参照
 * 
 */

/**
 * サーブレットをmultipart/form-data形式のリクエストに対応させる
 */
@MultipartConfig
@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		 * imageで取得する画像のファイル名を取得するための変数宣言
		 */
		String imageName = "default_image.png";
		
		/**
		 * imageで取得する画像ファイルをセット(image.pngを表示)
		 */
    	request.setAttribute("imageName", imageName);
    	
		/**
		* エラーメッセージ用の変数宣言
		*/
		String msg = "";
		
		/**
		* エラーメッセージのセット
		*/
		request.setAttribute("msg", msg);
        
		//チャット画面にフォワードさせる
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/chat.jsp");
		dispatcher.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		/**
		* name属性がimageのファイルをPartオブジェクトとして取得
		*/
		Part part = request.getPart("image");
		
		/**
		* エラーメッセージ用の変数宣言
		*/
		String msg = "";
		
		/**
		 * imageで取得する画像のファイル名を取得するための変数宣言
		 */
		String imageName = "default_image.png";
				
	    /**
	     *  画像ファイルかどうかをチェック
	     */
	    if (!model.ProfileErr.isImageFile(part)) {
	    	msg += "不正なファイルです。";
	        request.setAttribute("msg",msg);
	        request.setAttribute("imageName", imageName);//テスト用ファイルネーム
	        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/chat.jsp");
	        dispatcher.forward(request, response);
	        return;
	    }
        
		/**
		 * imageで取得する画像のファイル名を取得
		 */
		imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		
		/**
		* アップロードするフォルダを指定
		*/
		String path = getServletContext().getRealPath("/image");
				
		/**
		 * ファイルを指定されたフォルダに保存する
		 */
		part.write(path + File.separator + imageName);
		
		
		/**
		 * アプロード確認用のファイル名セット ※テスト用
		 */
		request.setAttribute("imageName", imageName);
			
		/**
		* エラーメッセージのセット
		*/
		request.setAttribute("msg", msg);
		
		//チャット画面にフォワードさせる
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/chat.jsp");
		dispatcher.forward(request, response);
		
	}
	
}




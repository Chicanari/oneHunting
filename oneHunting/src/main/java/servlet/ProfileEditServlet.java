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
@WebServlet("/profile_edit")
public class ProfileEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//user_profile.jspからだとこちらに飛ぶ
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		* エラーメッセージ用の変数宣言
		*/
		String msg = "";
		
		/**
		* エラーメッセージのセット
		*/
		request.setAttribute("msg", msg);
		
		//プロフィール編集画面にフォワードさせる
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_edit.jsp");
		dispatcher.forward(request, response);
	
	}
	
	//user_edit.jspで変更を行うとこちらに飛ぶ
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		* エラーメッセージ用の変数宣言
		*/
		String msg = "";
		
		/**
		* name属性がiconのファイルをPartオブジェクトとして取得
		*/
		Part part = request.getPart("icon");
		
		
	    /**
	     *  画像ファイルかどうかをチェック
	     */
	    if (!model.ProfileErr.isImageFile(part)) {
	    	msg += "不正なファイルです。";
	        request.setAttribute("msg",msg);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_edit.jsp");
	        dispatcher.forward(request, response);
	        return;
	    }
	    
		/**
		 * iconで取得するアイコンのファイル名を取得
		 */
		String iconName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		
		/**
		* アップロードするフォルダを指定
		*/
		String path = getServletContext().getRealPath("/icon");
		
		
		/**
		* ファイルを指定されたフォルダに保存する
		*/
		part.write(path + File.separator + iconName);
		
		/**
		* エラーメッセージのセット
		*/
		request.setAttribute("msg", msg);
		
		//プロフィール編集画面にフォワードさせる
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_profile.jsp");
		dispatcher.forward(request, response);
		
	}

}

package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.ChatDAO;
import dto.ChatRecordDTO;

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
		 * 
		 * 自動遷移時、メインチャットを表示する
		 * 
		 */
		
		String chatType = request.getParameter("chatType");
		if(chatType == null)  chatType = "chat_main";
		
		System.out.println("ChatServlet chatType:"+chatType);
		
		
		/**
		 * 
		 * 左カラムチャットより遷移するチャットを取得し返却する
		 * 
		 */
		
		/**
		* エラーメッセージ用の変数宣言
		*/
		String msg = "";
		
        /**
         * chatDAOのインスタンス生成
         */
		ChatDAO cDAO = new ChatDAO();
				
        /**
         * chatDAOから表示用のメソッド呼び出し
         */
		
		/**
		 * チャットリストを取得
		 */
		List<ChatRecordDTO> chatList;
		try {
			chatList = cDAO.comment_view(chatType);
		
			/**
			 * チャットリストをリクエストスコープに保存
			 */
			request.setAttribute("chatList", chatList); 
			
			/**
			* エラーメッセージをリクエストスコープに保存
			*/
			request.setAttribute("msg", msg);
	        
			//チャット画面にフォワードさせる
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/chat.jsp");
			dispatcher.forward(request, response);
		
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/**
		 * imageで取得する画像のファイル名を取得するための変数宣言
		 */
		String imageName = null;
		
		
		/**
		* エラーメッセージ用の変数宣言
		*/
		String msg = "";
		
		/**
		* name属性がimageのファイルをPartオブジェクトとして取得
		*/
		Part part = request.getPart("image");
		
        /**
         * chatDAOのインスタンス生成
         */
		ChatDAO cDAO = new ChatDAO();
				
        /**
         * chatDAOから表示用のメソッド呼び出し
         * 
         * ※テスト用に引数に"main"を挿入
         */
		String chatType = "main";
		List<ChatRecordDTO> chatList;
		try {
			/**
			 * チャットリストを取得
			 */
			chatList = cDAO.comment_view(chatType); 
			
			/**
			 * チャットリストをリクエストスコープに保存
			 */
			request.setAttribute("chatList", chatList); 
					
		    /**
		     *  画像ファイルかどうかをチェック
		     */
		    if (!model.ProfileErr.isImageFile(part)) {
		    	msg += "不正なファイルです。";
		        request.setAttribute("msg",msg);
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
			* エラーメッセージをリクエストスコープに保存
			*/
			request.setAttribute("msg", msg);
			
			//チャット画面にフォワードさせる
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/chat.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}




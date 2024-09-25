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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.AccountDAO;
import dao.ChatDAO;
import dto.ChatRecordDTO;
import dto.UserProfileDTO;

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
		 * HttpSessionのインスタンスの取得
		 */
		HttpSession session = request.getSession();
		
		/**
		 * 
		 * 最後に見ていたチャットを表示する
		 * 自動遷移時、メインチャットを表示する
		 * 
		 */	
		//左カラムから送られてきたチャット名を取得する
		String chatType = request.getParameter("chatType");

		System.out.println("ChatServlet chatType:"+chatType);
				 
		//上記がnullだった場合
		if(chatType == null) {
			
			//セッションにチャットタイプが保存されている（既に遷移済みでないか）確認する
			chatType = (String)session.getAttribute("chatType");
			
			//入っていない場合はメインチャットへ遷移させる
			if(chatType == null) {
				chatType = "chat_main";
			}
			
		}
	
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
		 * チャットリストを取得
		 */
		List<ChatRecordDTO> chatList;
		try {
	        /**
	         * chatDAOから表示用のメソッド呼び出し
	         */
			chatList = cDAO.comment_view(chatType);
			
			/**
			 * セッションスコープにインスタンスを保存
			 */
			session.setAttribute("chatType",chatType);
		
			/**
			 * チャットのコメント一覧と名前をセッションスコープに保存
			 */	
			session.setAttribute("chatType", chatType);
			session.setAttribute("chatList", chatList); 
			
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
		 * HttpSessionのインスタンスの取得
		 */
		HttpSession session = request.getSession();
		
		
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
         * 現在の情報を取得したいのでセッションスコープから取り出す
         */
		String chatType = (String)session.getAttribute("chatType");
		List<ChatRecordDTO> chatList;
		if(chatType == null) chatType = "chat_main";

		
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
			 * imageで取得する画像のファイル名を取得
			 */
			imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
			
			/**
			 * リクエストパラメータからチャット投稿情報の取得
			 * accountid,accounName,icon,text
			 * 
			 */
			//sessionスコープ内のログインIDからアカウントIDを取得
			String accountId = (String)session.getAttribute("loginID");
			//アカウントIDから他の情報を取得するためのaccountDAOへの接続
			AccountDAO aDAO = new AccountDAO();
			UserProfileDTO upDTO = aDAO.profileView(accountId);
			//アカウント情報を取得
			String accountName = upDTO.getAccountName();
			//アイコン情報を取得
			String icon = upDTO.getAccountIcon();
			//リクエストスコープから本文を取得
			String text = request.getParameter("comment");
			
			/**
			 * textが200字より多いの場合と0の場合にエラーを返す
			 */
			if(text == null || text.trim().isEmpty()) {
				/**
				 * チャットのコメント一覧と名前をセッションスコープに保存
				 */	
				session.setAttribute("chatType", chatType);
				session.setAttribute("chatList", chatList); 
				
				//エラーメッセージの追加
		    	msg += "文字が入力されていません。";
		    	
		    	//エラーメッセージをリクエストスコープに保存
		        request.setAttribute("msg",msg);
		        
		        //チャット画面にフォワード
		        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/chat.jsp");
		        dispatcher.forward(request, response);
		        return;
			}else if(text.length() > 200) {
				/**
				 * チャットのコメント一覧と名前をセッションスコープに保存
				 */	
				session.setAttribute("chatType", chatType);
				session.setAttribute("chatList", chatList); 
				
				//エラーメッセージの追加
		    	msg += "200字以内で入力してください。";
		    	
		    	//エラーメッセージをリクエストスコープに保存
		        request.setAttribute("msg",msg);
		        
		        //チャット画面にフォワード
		        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/chat.jsp");
		        dispatcher.forward(request, response);
		        return;
			}
			
	        /**
	         * chatDAOからチャット投稿用のメソッド呼び出し
	         * 入力された情報をDBに書き込む。
	         */
			int inCunt = cDAO.comment_insert(chatType,accountId,accountName,icon,text,imageName);			
			
			/**
			* アップロードするフォルダを指定
			*/
			String path = getServletContext().getRealPath("/image");
					
			/**
			 * ファイルを指定されたフォルダに保存する
			 */
			part.write(path + File.separator + imageName);
			
			/**
			 * チャットのコメント一覧と名前をセッションスコープに保存
			 */	
			session.setAttribute("chatType", chatType);
			session.setAttribute("chatList", chatList); 
			
			/**
			* エラーメッセージをリクエストスコープに保存
			*/
			request.setAttribute("msg", msg);
			
			//確認用
			System.out.println("kakikomi:"+chatType);
			
			//チャット画面にフォワードさせる
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/chat.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}




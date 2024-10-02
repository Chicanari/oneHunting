package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import dao.ChatDAO;
import dto.ChatRecordDTO;
import dto.UserProfileDTO;

/**
 * Servlet implementation class ChatBranchServlet
 */
@WebServlet("/chat-branch")
public class ChatBranchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("chat");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		 * HttpSessionのインスタンスの取得
		 */
		HttpSession session = request.getSession();
		
		/**
		 * sessionスコープ内のログインIDからアカウントIDを取得
		 */
		String accountId = (String)session.getAttribute("loginID");
		
		//アカウントIDから他の情報を取得するためのaccountDAOへの接続
		AccountDAO aDAO = new AccountDAO();
		UserProfileDTO upDTO = aDAO.profileView(accountId);
		//県情報を取得
		String ken = upDTO.getAccountKen();
		
		/**
		 * 
		 * 最後に見ていたチャットを表示する
		 * 自動遷移時、メインチャットを表示する
		 * 
		 */	
		//左カラムから送られてきたチャット名を取得する
		String chatType = request.getParameter("chatType");

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
			 * チャットのコメント一覧・名前・県情報をセッションスコープに保存
			 */	
			session.setAttribute("chatType", chatType);
			session.setAttribute("chatList", chatList); 
			session.setAttribute("ken", ken); 
	        
			//チャット画面にリダイレクトさせる
			response.sendRedirect("chat");
			
		
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}	
	
	}

}

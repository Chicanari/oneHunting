package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import dao.ChatDAO;

/*
 * 
 * request.setCharacterEncoding("UTF-8");
 * 
 * フィルターという機能で一括設定しましたので、上記の記述は全てのサーブレットで不要です。
 * ※filterパッケージのSetEncodingFilterで設定しています。教科書参照
 * 
 */

//あとで編集要
@WebServlet("/like")
public class LikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//パラメータの取得
		String like = request.getParameter("like");
		String postId = request.getParameter("postId");
		System.out.println(like + " " + postId);
		
		//現在のチャットタイプの取得
		HttpSession session = request.getSession();
		String chatType =  (String)session.getAttribute("chatType");
		String loginID =  (String)session.getAttribute("loginID");
		
		//DAOの接続
		ChatDAO chatDAO = new ChatDAO();
		AccountDAO accountDAO = new AccountDAO();
		
		//plus　→いいねの増加　minus　→いいねの減少
		if(like.equals("plus")) {
			
			//チャットのいいね数・いいねしたアカウント一覧を増やす
			chatDAO.like_add(chatType, postId,loginID);
			accountDAO.like_add();
			
		}else if(like.equals("minus")) {
			
			chatDAO.like_delete(chatType, postId,loginID);
			accountDAO.like_delete();
			
		}
		
		//チャット画面に戻る
		response.sendRedirect("chat");
		
	}

}

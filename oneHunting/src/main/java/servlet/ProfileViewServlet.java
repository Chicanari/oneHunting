package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import dto.UserProfileDTO;

/*
 * 
 * request.setCharacterEncoding("UTF-8");
 * 
 * フィルターという機能で一括設定しましたので、上記の記述は全てのサーブレットで不要です。
 * ※filterパッケージのSetEncodingFilterで設定しています。教科書参照
 */

@WebServlet("/profile_view")
public class ProfileViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		 * プロフィール表示の検索（SQL）に使う要素はなに？　１つ「　　」
		 */
		
		//アカウントDAOの接続
		AccountDAO accountDAO = new AccountDAO();
		//セッションスコープの接続
		HttpSession session = request.getSession();
		
		/*
		 * Q：これはどこからなにを取るためのgetParameter？？
		 */
		//name属性記述
		String accountId = request.getParameter("");
		
		/*
		 * Q：なにをしてるかコメント書いてみて
		 */
		UserProfileDTO userProfile = accountDAO.profileView(accountId);
		session.setAttribute("profile", userProfile);
		
		//ログイン画面にフォワードさせる
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_profile.jsp");
		dispatcher.forward(request, response);
		
	}

}

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
import model.LoginErr;

/*
 * 
 * request.setCharacterEncoding("UTF-8");
 * 
 * フィルターという機能で一括設定しましたので、上記の記述は全てのサーブレットで不要です。
 * ※filterパッケージのSetEncodingFilterで設定しています。教科書参照
 * 
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//直接アクセスされたときにログイン済か、セッションの中身を調べる
		HttpSession session = request.getSession();
		String loginID = (String)session.getAttribute("loginID");
		Boolean login = (Boolean)session.getAttribute("login");
		
		//ログインIDが入っているか、ログインがtrueの時ログインしていると判断する
		if( loginID != null || login == true ) {
			//ログイン状態の時は、全体チャットに移動する
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/chat.jsp");
			dispatcher.forward(request, response);
		} else {
			//ログアウト状態の時は、ログイン画面にリダイレクトさせる
			response.sendRedirect("/oneHunting");
			
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//パラメータの取得
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		//入力値チェック
		LoginErr loginErr = new LoginErr();
		String errMessage = loginErr.account_InputValueCheck(id, pw);
		
		//DAOの接続
		AccountDAO accountDAO = new AccountDAO();
		
		//ログイン完了・失敗メッセージの表示
		String message = accountDAO.userLogin(id,pw);
		request.setAttribute("message", message);
		
		if(message.equals("LOGIN OK")) {
			
			//セッションにログイン状態であることを保存する
			HttpSession session = request.getSession();
			session.setAttribute("loginID", id);
			session.setAttribute("login", true);
			
			//登録完了にフォワードさせる
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/generalChat.jsp");
			dispatcher.forward(request, response);
			
		}else {
			//ログイン画面にフォワードさせる
			RequestDispatcher dispatcher = request.getRequestDispatcher("");
			dispatcher.forward(request, response);
		}
		
		//全体チャットにフォワードさせる
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/chat.jsp");
		dispatcher.forward(request, response);
		
	}

}

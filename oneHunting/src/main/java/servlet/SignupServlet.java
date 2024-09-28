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
import model.ProfileErr;

/*
 * 
 * request.setCharacterEncoding("UTF-8");
 * 
 * フィルターという機能で一括設定しましたので、上記の記述は全てのサーブレットで不要です。
 * ※filterパッケージのSetEncodingFilterで設定しています。教科書参照
 * 
 */

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//新規登録画面にフォワードさせる
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/signup.jsp");
		dispatcher.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//ログイン処理も一緒に行う
		
		//パラメータの取得
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String mail = request.getParameter("mail");
		String ken = request.getParameter("ken");
		String icon = request.getParameter("icon");
		
		//入力値チェック
		ProfileErr profileErr = new ProfileErr();
		String message = profileErr.account_InputValueCheck(name,id,pw,mail,ken);
		
		if(!message.equals("")) {
			//リクエストスコープに格納する
			request.setAttribute("message", message);
			//エラーがあった場合は登録画面にフォワードさせる
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/signup.jsp");
			dispatcher.forward(request, response);
		}else {
			/**
			 * エラーがない場合だけ登録に進む
			 */
			//DAOの接続
			AccountDAO accountDAO = new AccountDAO();
			//登録完了・失敗メッセージの表示
			message += accountDAO.userSignup(name,id,pw,mail,ken,icon);
			
			//リクエストスコープに格納する
			request.setAttribute("message", message);
			
			if(message.equals("")) {
				
				//ログインさせる
				HttpSession session = request.getSession();
				session.setAttribute("loginID", id);
				session.setAttribute("login", true);
				
				String loginID = (String)session.getAttribute("loginID");
				Boolean login = (Boolean)session.getAttribute("login");
				
				//登録完了にフォワードさせる
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/search_result.jsp");
				dispatcher.forward(request, response);
				
			}else {
				//新規登録画面にフォワードさせる
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/signup.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		
		
		
	}

}

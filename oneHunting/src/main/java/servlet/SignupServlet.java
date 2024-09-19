package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//ログイン処理も一緒に行う
		
		//TODO: ユーザーのPWの入力値を７２文字までとする
		//TODO: DBに登録するのは60文字　CHAR(60)
		
		
		//パラメータの取得
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String mail = request.getParameter("mail");
		String ken = request.getParameter("ken");
		
		//入力値チェック
		ProfileErr profileErr = new ProfileErr();
		profileErr.account_InputValueCheck(name,id,pw,mail,ken);
		
		//DAOの接続
		AccountDAO accountDAO = new AccountDAO();
		//登録完了・失敗メッセージの表示
		String message = accountDAO.userSignup(name,id,pw,mail,ken);
		
		//リクエストスコープに格納する
		request.setAttribute("message", message);
		
		if(message.equals("")) {
			//登録完了にフォワードさせる
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/signup-complete.jsp");
			dispatcher.forward(request, response);
		}else {
			//新規登録画面にフォワードさせる
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/signup.jsp");
			dispatcher.forward(request, response);
		}
		
		
	}

}

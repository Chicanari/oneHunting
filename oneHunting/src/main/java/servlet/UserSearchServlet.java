package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import dto.UserRecordDTO;

/*
 * 
 * request.setCharacterEncoding("UTF-8");
 * 
 * フィルターという機能で一括設定しましたので、上記の記述は全てのサーブレットで不要です。
 * ※filterパッケージのSetEncodingFilterで設定しています。教科書参照
 * 
 */

@WebServlet("/search")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AccountDAO accountDAO = new AccountDAO();
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		
		//仮入力/jspのname属性に合わせること
		String account_id = request.getParameter("accountid");
		String account_name = request.getParameter("accountmei");
		
		
		List<UserRecordDTO> sRecord = accountDAO.userSearch(account_id,account_name);
		
		
		//セッションスコープへ格納
		session.setAttribute("shohin_t", sRecord);
		
		//全体チャットにフォワードさせる ※search_resultに飛ばすように後々変更要
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/chat.jsp");
		dispatcher.forward(request, response);
		
	}

}

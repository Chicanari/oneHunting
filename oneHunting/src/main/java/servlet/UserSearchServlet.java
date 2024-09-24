package servlet;

import java.io.IOException;
import java.util.ArrayList;
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
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AccountDAO accountDAO = new AccountDAO();
		HttpSession session = request.getSession();
		
		//パラメータはname属性とあわせること
		String searchQuery = request.getParameter("kensaku");
		//空白検索時、表示なしにするためif文を追加
		if (searchQuery == null || searchQuery.trim().isEmpty()) {
			//空白時、結果を非表示
			session.setAttribute("search_result", new ArrayList<UserRecordDTO>());
		}else {
			//通常検索を表示
		List<UserRecordDTO> sRecord = accountDAO.userSearch(searchQuery);
		session.setAttribute("search_result", sRecord);
		}
		
		//全体チャットにフォワードさせる ※search_result.jspに飛ばすように後々変更要
		//仮作成のWEB-INF直下search.jspで動作確認済。jsp作成時確認要。
		//caht.jspから"WEB-INF/jsp/search_result.jsp"へ画面遷移は確認済
		RequestDispatcher dispatcher = request.getRequestDispatcher("/search.jsp");
		dispatcher.forward(request, response);
	}

}

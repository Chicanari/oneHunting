package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import mail.PresentEmail;

/*
 * 
 * request.setCharacterEncoding("UTF-8");
 * 
 * フィルターという機能で一括設定しましたので、上記の記述は全てのサーブレットで不要です。
 * ※filterパッケージのSetEncodingFilterで設定しています。教科書参照
 * 
 */

@WebServlet("/coupon")
public class CouponServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//idを取得する
		HttpSession session = request.getSession();
		String accountId = (String) session.getAttribute("loginID");
		
		//メールアドレスを取得する
		AccountDAO ad = new AccountDAO();
		String mailaddress = ad.searchMail(accountId);
		System.out.println(mailaddress);
		
		//クーポンメールを送信する
		PresentEmail pe = new PresentEmail();
		try {
			pe.sendCouponMail(accountId,mailaddress);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
			
		//プロフィール画面にリダイレクトする
		response.sendRedirect("profile_view");
		
	}

}

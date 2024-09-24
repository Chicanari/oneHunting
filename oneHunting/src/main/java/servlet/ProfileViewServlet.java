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

		
		
		// Q:プロフィール表示の検索（SQL）に使う要素はなに？　１つ「　　」　
		// テーブルに登録しているaccount_id
		/**
		 * Q:account_idはどうやって取ってくる？
		 */
		
		//AccountDAOの実装とセッションスコープの作成
		AccountDAO accountDAO = new AccountDAO();
		HttpSession session = request.getSession();
		
		
		//Q：これはどこからなにを取るためのgetParameter？？
		//HTTPリクエストから送られてくるパラメータ(formの入力フィールドやURLのクエリパラメータ)を取得する
		//jspで記述したname属性を記述すること。jsp作成時に記述。
		
		/*
		 * Q：↑どこから何を取ってくるから書いたの？
		 */
		String accountId = request.getParameter("");
		
		
		//Q：なにをしてるかコメント書いてみて
		
		
		//request.getParameterを格納しているaccountIdを、profileViewの引数として使用。
		//スコープへ格納し、送り先に転送する
		//→アカウントIDを使用してプロフィールを表示
		
		/*
		 * >>request.getParameterを格納しているaccountIdを、profileViewの引数として使用。
		 * Q:ここでなにを取得しているから、profileViewの引数にしているの？
		 * 　上記の「テーブルに登録しているaccount_id」と矛盾していない？
		 * 
		 * >>スコープへ格納
		 * Q:何スコープに格納？また、そのスコープに格納する理由はなぜ？
		 * 
		 * >>送り先に転送する
		 * Q:送り先ってなに？もっと詳しくコメントしてください。
		 */
		
		UserProfileDTO userProfile = accountDAO.profileView(accountId);
		//上記変数をセッションスコープへ格納
		session.setAttribute("profile", userProfile);
		
		//ログイン画面にフォワードさせる
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_profile.jsp");
		dispatcher.forward(request, response);
		
	}

}

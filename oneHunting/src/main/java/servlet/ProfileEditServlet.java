package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.AccountDAO;
import dto.UserProfileDTO;

/*
 * 
 * request.setCharacterEncoding("UTF-8");
 * 
 * フィルターという機能で一括設定しましたので、上記の記述は全てのサーブレットで不要です。
 * ※filterパッケージのSetEncodingFilterで設定しています。教科書参照
 * 
 */



/**
 * サーブレットをmultipart/form-data形式のリクエストに対応させる
 */
@MultipartConfig
@WebServlet("/profile_edit")
public class ProfileEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//user_profile.jspからだとこちらに飛ぶ
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		 * HttpSessionのインスタンスの取得
		 */
		HttpSession session = request.getSession();
		
		/**
		* エラーメッセージ用の変数宣言
		*/
		String msg = "";
		
		/**
		* エラーメッセージのセット
		*/
		request.setAttribute("msg", msg);
		
		/**
		 * sessionスコープ内のログインIDからアカウントIDを取得
		 */
		String accountId = (String)session.getAttribute("loginID");
		
		//アカウントIDから他の情報を取得するためのaccountDAOへの接続
		AccountDAO aDAO = new AccountDAO();
		UserProfileDTO upDTO = aDAO.profileView(accountId);
		
		//プロフィール編集画面にフォワードさせる
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_edit.jsp");
		dispatcher.forward(request, response);
	
	}
	
	//user_edit.jspで変更を行うとこちらに飛ぶ
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		 * HttpSessionのインスタンスの取得
		 */
		HttpSession session = request.getSession();
		
		
		/**
		* エラーメッセージ用の変数宣言
		*/
		String msg = "";
		
		/**
		 * sessionスコープ内のログインIDからアカウントIDを取得
		 */
		String accountId = (String)session.getAttribute("loginID");
		
		/**
		 * AccountDAOのインスタンス化
		 */
		AccountDAO aDAO = new AccountDAO();
		
		/**
		 * プロフィール情報をリクエストスコープから取得
		 *  名前・メール・県・自己紹介
		 */
		String name = request.getParameter("name");
		String mail = request.getParameter("mail");
		String ken = request.getParameter("ken");
		String introduction = request.getParameter("introduction");
		
		//確認用
		System.out.println("Name: " + name);
		System.out.println("Mail: " + mail);
		System.out.println("Ken: " + ken);
		System.out.println("Introduction: " + introduction);
		
		//名前に関するエラーチェック
		if(name.length() == 0) {
			msg += "名前を入力してください。<br>";
		}else if(name.length() > 200) {
			msg += "文字数が200字を超えています。<br>";
		}
		
		//mailに関するエラーチェック
		if(mail.length() == 0) {
			msg += "メールアドレスを入力してください。<br>";
		}else if(name.contains("@")) {
			msg += "入力された文字列がメールアドレスではありません。<br>";
		}
		
		//県に関するエラーチェック
		if(ken.length() == 0) {
			msg += "県を選択してください。<br>";
		}
		
		//自己紹介文に関するエラーチェック
		if(introduction.length() > 200) {
			msg += "自己紹介の文字数が200字を超えています。<br>";
		}
		
		/**
		 * iconで取得する画像のファイル名を取得するための変数宣言
		 * originalIconNameは取り込む画像の元画像名を参照する為の変数
		 */
		String iconName;
		String originalIconName;
		
		/**
		* name属性がiconのファイルをPartオブジェクトとして取得
		*/
		Part part = request.getPart("icon");
		
		/**
		 * imageで取得する画像のファイル名を取得
		 */
		originalIconName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

	    /**
	     *  画像ファイルチェック
	     *  1:画像ファイル名が空であるかチェックし、空の場合はデフォルト名に設定
	     *  2:空でない場合、ファイルの拡張子をチェック
	     *  3:不正な場合はエラーメッセージを返す
	     *  4:適正な場合、ファイル名に固有名を追加する
	     */
        if (originalIconName.isEmpty()) {
        	// デフォルト画像を使用
            iconName = "default_icon.png";
            // 画像ファイルかどうかのチェック
        } else if (part != null && !model.ProfileErr.isImageFile(part)) {

            msg += "不正なファイルです。<br>";
 
            request.setAttribute("msg", msg);
            
    		//プロフィール編集画面にフォワードさせる
    		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_edit.jsp");
    		dispatcher.forward(request, response);
            return;
        }else {
            // UUIDを利用してユニークなファイル名を生成
            String uniqueID = UUID.randomUUID().toString();
            String fileExtension = originalIconName.substring(originalIconName.lastIndexOf('.'));
            
            // 新しいファイル名
            iconName = uniqueID + fileExtension;
        }		
		
		/**
		* アップロードするフォルダを指定
		*/
		String path = getServletContext().getRealPath("/icon");
		
	    // パスの存在を確認
	    File uploadDir = new File(path);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdirs();  // ディレクトリが存在しない場合は作成
	    }
	    
		//画像の中身が在る場合にのみ保存する
		if(!(part == null)) {
			/**
			 * ファイルを指定されたフォルダに保存する
			 */
			part.write(path + File.separator + iconName);
		}
		
		
		// プロフィールを更新するDAOメソッドの呼び出し
		if (!msg.isEmpty()) {
			// エラーメッセージがある場合は何もしない
		} else {
			//仮引数はid,name,mail,ken,icon,introduction
			boolean updateSuccess = aDAO.profileEdit(
					accountId, 
					name,
					mail, 
					ken,
					iconName, 
					introduction);
			if (updateSuccess) {
				msg = "プロフィールが更新されました。";
			} else {
				msg = "プロフィールの更新に失敗しました。";
				
	            request.setAttribute("msg", msg);
	            
	    		//プロフィール編集画面にフォワードさせる
	    		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_edit.jsp");
	    		dispatcher.forward(request, response);
	    		return;
			}
		}
		
		//アカウント情報表示のためのviewを取得
		UserProfileDTO userProfile = aDAO.profileView(accountId);
				
		//上記変数をリクエストスコープへ格納
		session.setAttribute("profile", userProfile);
		
		/**
		* エラーメッセージのセット
		*/
		request.setAttribute("msg", msg);
		
		//確認用
		String realPath = getServletContext().getRealPath("/");
		System.out.println("リアルパス: " + realPath);
		String actualPath = path + File.separator + iconName;
		System.out.println("保存先パス: " + actualPath);
		
		//プロフィール編集画面にフォワードさせる
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_profile.jsp");
		dispatcher.forward(request, response);
		
	}

}

package servlet;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.AccountDAO;
import dao.ChatDAO;
import json.LikeCount;
import json.LikeInfo;

/*
 * 
 * request.setCharacterEncoding("UTF-8");
 * 
 * フィルターという機能で一括設定しましたので、上記の記述は全てのサーブレットで不要です。
 * ※filterパッケージのSetEncodingFilterで設定しています。教科書参照
 * 
 */

@WebServlet("/like")
public class LikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//JSから送られてきたいいねの情報
		String likeInfoJSON = request.getReader().lines().collect(Collectors.joining());
		
		// JSON形式のデータを扱うためのJSONクラス
		LikeInfo likeInfo = new LikeInfo();
    	// ObjectMapperを初期化
	    ObjectMapper objectMapperInfo = new ObjectMapper();
	    
	    //JSON形式のStringをGoodIDオブジェクトに変換
	    likeInfo = objectMapperInfo.readValue(likeInfoJSON, LikeInfo.class);
		
		//パラメータの取得
		String like = likeInfo.getLike();
		String postId = likeInfo.getPostId();
		String postAccountId = likeInfo.getPostAccountId();
		
		//現在のチャットタイプの取得
		HttpSession session = request.getSession();
		String chatType =  (String)session.getAttribute("chatType");
		String loginID =  (String)session.getAttribute("loginID");
		
		//nullの場合
		if(chatType == null) chatType = "chat_main";
		
		//DAOの接続
		ChatDAO chatDAO = new ChatDAO();
		AccountDAO accountDAO = new AccountDAO();
		
		//plus　→いいねの増加　minus　→いいねの減少
		
		//チャットのいいね数・いいねしたアカウント一覧を増減する
		chatDAO.like_update(chatType, postId,loginID,like);
		accountDAO.like_update(postId,postAccountId,like);
		
		// 新しいいいね数を取得する
        int newLikeCount = chatDAO.getLikeCount(postId,chatType);
		
		/**/
		
		// JSONレスポンスを返す
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        //JSONクラスの初期化
        LikeCount likeCount = new LikeCount();
        //ObjectMapperを初期化
	    ObjectMapper objectMapperCount = new ObjectMapper();
	    //JSONに値を格納する
	    likeCount.setNewLikeCount(newLikeCount);
	    //GoodIDクラスのオブジェクトを、String形式のJSONファイルに変換する
  		String jsonString = objectMapperCount.writeValueAsString(likeCount);
  		
        response.getWriter().write(jsonString);
		
		/**/
		
		
		//チャット画面に戻る
		//response.sendRedirect("chat");
		
	}

}

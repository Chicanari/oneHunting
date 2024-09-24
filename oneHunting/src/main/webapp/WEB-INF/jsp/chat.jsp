<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- ChatRecordDTO,Listの呼び出し --%>
<%@
page import="dto.ChatRecordDTO,java.util.List"
%>

<%-- チャット画面に関する情報の取得 --%>
<% 

//チャット画面に表示する情報（ChatRecordDTO）をリクエストスコープから取得
List<ChatRecordDTO> chatList = (List<ChatRecordDTO>)request.getAttribute("chatList");

//エラーメッセージ用変数読み込み
String msg = (String)request.getAttribute("msg");

//チャットタイプを判別するためのチャットタイプ変数呼び出し
String chatType = (String)request.getAttribute("chatType");

//nullチェックしてデフォルト値を設定
if (chatType == null) {
 chatType = "chat_main"; // デフォルトのチャットタイプを設定
}
%>

<%-- ログイン情報の取得　※ログインしてない場合はログイン画面へ移動する --%>
<%

String loginID = (String)session.getAttribute("loginID");
Boolean login = (Boolean)session.getAttribute("login");

//nullチェック
if(login == null)	login = false;

//ログインIDが入っているか、ログインがtrueの時ログインしていると判断する
if( loginID == null || login == false ) {
	//ログアウト状態の時は、ログイン画面に移動する
	response.sendRedirect("/oneHunting");
}

%>


<!DOCTYPE html>

<%-- 言語を日本語に指定 --%>
<html lang="ja">
<head>
<meta charset="UTF-8">
<%-- javascript使用の為のmetaタグ --%>
<meta name="viewport" content = "width=device-width, initial-scale=1.0">
<!-- CSSファイル  -->
<link rel="stylesheet" type="text/css" href="css/chat.css"
	<title>
	<!-- TODO アイコン設置 -->
	</title>
</head>
<body>	
<%-- 山﨑画面レイアウトマークアップ --%>
	<div class="chat-container">
	
		<!-- ヘッダー-->
		<form action="nav" method="get">
			<nav>
				<img class="logo" src="image/logo_white.png" alt="oneHuntingのロゴ">
				<div class="wrapper">
					<input type="text" name="kensaku"> <input type="submit"
						value="検索">
					<!-- ハンバーガーメニュー -->
					<img class="header__btn" src="image/hamburgermenu.png" alt="">
				</div>

				<nav class="nav">
					<div class="nav__header">
						<img class="nav__btn" src="image/batten-close.png" alt="">
						<ul class="nav__list">
							<li class="nav__item"><a href="/oneHunting/profile_view">プロフィール</a></li>
							<li class="nav__item"><a href="/oneHunting/logout">ログアウト</a></li>
						</ul>
					</div>
				</nav>
			</nav>
		</form>
		<div class="maindisplay">
		<!-- 左カラム -->
		<form action="chat" method="get">
			<div class="side-column">
			<button type="submit" name="chatType" value="chat_fukuoka">福岡</button><br/>
			<button type="submit" name="chatType" value="chat_saga">佐賀</button><br/>
			<button type="submit" name="chatType" value="chat_oita">大分</button><br/>
			<button type="submit" name="chatType" value="chat_nagasaki">長崎</button><br/>
			<button type="submit" name="chatType" value="chat_kumamoto">熊本</button><br/>
			<button type="submit" name="chatType" value="chat_miyazaki">宮崎</button><br/>
			<button type="submit" name="chatType" value="chat_kagoshima">鹿児島</button><br/>
			<button type="submit" name="chatType" value="chat_okinawa">沖縄</button><br/>
			<button type="submit" name="chatType" value="chat_main">雑談</button><br/>
			<button type="submit" name="chatType" value="chat_shikaku">狩猟資格</button><br/>
			<button type="submit" name="chatType" value="chat_seika">成果報告</button><br/>
			<button type="submit" name="chatType" value="chat_item">おすすめアイテム</button><br/>	
			</div>
		</form>	
		
		<main>
		<!-- チャット本体部分 -->
		<section id="main">
			<div class="wrapper">
				<p>
				
				<% for(ChatRecordDTO record :chatList){ %>
				
				投稿ID:<%= record.getPostId() %><br>
				アカウントID：<%= record.getAccountId() %><br>
				アカウント名：<%= record.getAccountName() %><br>
				投稿日時：<%= record.getTime() %><br>
				投稿内容：<%= record.getText() %><br>
				いいね数：<%= record.getGoodCount() %><br>
				<br>
				
				<% } %>
				
				</p>
			</div>
		</section>
		</main>
		
		</div>
		<!-- フッター -->
		<footer>
			<div class="footer">
			<%-- チャット投稿form --%>
				<form action="chat" method="post" enctype="multipart/form-data">
					<input type="text" name="comment">
					
					<%-- ファイルをアップロード為、enctype="multipart/form-data"を指定 --%>
					<%-- ファイルをアップロードする --%>
					<%-- onchangeタグによりファイルアップロードされた場合にプレビューを表示する --%>
					<%-- acceptタグによりアップロードできるファイルを指定 --%>
					<%-- (※ただし、アップロード時に表示されるファイルを指定するだけであり、指定外のファイルアップロードは可能) --%>
					
					<input type="file" name="image" id="fileElem" multiple accept="image/*" style="display:none" />
					<button id="fileSelect" type="button">画像</button>
					
					<%-- 現在のチャットタイプから書き込むチャットタイプを分岐させる予定 --%>
					<button type="submit" name="chatType" value="chat_main">送信</button>
				</form>
				<br/>
			</div>
		 </footer>
	</div>
	
	<!-- body直前でjQueryと自作のJSファイルの読み込み  -->
	<script src="https://code.jquery.com/jquery-3.7.0.min.js"
        integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <script src="js/chat.js"></script>
	

	<%-- エラーの表示 --%>
	<%=msg%>
	
	<%-- ファイルが選択されていませんを見た目上消し去るスクリプト構文 --%>
	<script>
	const fileSelect = document.getElementById("fileSelect");
	const fileElem = document.getElementById("fileElem");

	fileSelect.addEventListener("click", (e) => {
	  if (fileElem) {
	    fileElem.click();
	  }
	}, false);
	</script>

</body>
</html>
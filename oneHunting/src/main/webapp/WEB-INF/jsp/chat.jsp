<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- ChatRecordDTO,Listの呼び出し --%>
<%@
page import="dto.ChatRecordDTO,dto.UserRecordDTO,java.util.List,java.util.ArrayList"
%>

<%-- チャット画面に関する情報の取得 --%>
<% 

//チャット画面に表示する情報（ChatRecordDTO）をリクエストスコープから取得
List<ChatRecordDTO> chatList = (List<ChatRecordDTO>)session.getAttribute("chatList");

//エラーメッセージ用変数読み込み
String msg = (String)request.getAttribute("msg");

//チャットタイプを判別するためのチャットタイプ変数呼び出し
String chatType = (String)session.getAttribute("chatType");

//所在県を判別するためのken変数呼び出し
String ken = (String)session.getAttribute("ken");

//nullチェックしてデフォルト値を設定
if (chatType == null)  chatType = "chat_main"; // デフォルトのチャットタイプを設定
%>

<%-- 検索機能に関する情報の取得 --%>
<%

//アカウント検索をしているか判別する
Boolean searchType = (Boolean)request.getAttribute("searchType");

//検索結果の表示
ArrayList<UserRecordDTO> searchResults = (ArrayList<UserRecordDTO>) session.getAttribute("search_result");

//nullチェックしてデフォルト値を設定
if (searchType == null) searchType = false;
if (searchResults == null) searchResults = new ArrayList<UserRecordDTO>();

%>

<%-- ログイン情報の取得　※ログインしてない場合はログイン画面へ移動する --%>
<%

String loginID = (String)session.getAttribute("loginID");
Boolean login = (Boolean)session.getAttribute("login");

//nullチェック
if(login == null)	login = false;

//ログインIDが入っているか、ログインがtrueの時ログインしていると判断する
//ログアウト状態の時は、ログイン画面に移動する
if( loginID == null || login == false ) response.sendRedirect("/oneHunting");

%>

<!DOCTYPE html>

<%-- 言語を日本語に指定 --%>
<html lang="ja">
<title>チャット画面</title>
<head>
<meta charset="UTF-8">
<%-- javascript使用の為のmetaタグ --%>
<meta name="viewport" content = "width=device-width, initial-scale=1.0">
<%-- webフォント --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Zen+Maru+Gothic:wght@300;400;500;700;900&display=swap" rel="stylesheet">
<!-- CSSファイル  -->
<link rel="stylesheet" type="text/css" href="css/chat.css"
</head>

<body>	
	<div class="chat-container">
		<!-- ヘッダー-->
		<header-menu>
			<div class="header_container">
				<form action="search" method="post">
				<img class="logo" src="image/logo_white.png" alt="oneHuntingのロゴ">
				<div class="search">
					<input type="text" name="kensaku" class="kensaku" placeholder="Search">
					<!-- <input type="submit" value="検索" class="kensaku_btn"> -->
					<button type="submit"><img src="image/serch.png" alt=""></button>
				</div>
				<!-- .header__btn -->
            	<img class="header__btn" src="image/hamburgermenu.png" alt="">
            	</form>
			</div>
			
			<nav class="nav">
                <div class="nav__header">
                    <img class="nav__btn" src="image/batten-close.png" alt="">
                    <div class="nav_item">
	                	<form action="profile_view" method="post">
							<input type="submit" value="プロフィール" class="profile_view">
						</form>
						<form action="logout" method="post">
							<input type="submit" value="ログアウト" class="logout">
						</form>
						<img class="risu" src="image/risu.png" alt="">
					</div>
                </div>
            </nav>
		</header-menu>
	<div class="maindisplay">
		<!-- 左カラム -->
		<form action="chat" method="get">
			<div class="side-column">
			<% if(ken.equals("福岡県")){ %>
			<button type="submit" name="chatType" value="chat_fukuoka">福岡</button><br/>
			<% }else if(ken.equals("佐賀県")){ %>
			<button type="submit" name="chatType" value="chat_saga">佐賀</button><br/>
			<% }else if(ken.equals("大分県")){ %>
			<button type="submit" name="chatType" value="chat_oita">大分</button><br/>
			<% }else if(ken.equals("長崎県")){ %>
			<button type="submit" name="chatType" value="chat_nagasaki">長崎</button><br/>
			<% }else if(ken.equals("熊本県")){ %>
			<button type="submit" name="chatType" value="chat_kumamoto">熊本</button><br/>
			<% }else if(ken.equals("宮崎県")){ %>
			<button type="submit" name="chatType" value="chat_miyazaki">宮崎</button><br/>
			<% }else if(ken.equals("鹿児島県")){ %>
			<button type="submit" name="chatType" value="chat_kagoshima">鹿児島</button><br/>
			<% }else if(ken.equals("沖縄県")){ %>
			<button type="submit" name="chatType" value="chat_okinawa">沖縄</button><br/>
			<% } %>	
			<button type="submit" name="chatType" value="chat_main">全体チャット</button><br/>
			<button type="submit" name="chatType" value="chat_shikaku">狩猟資格</button><br/>
			<button type="submit" name="chatType" value="chat_seika">成果報告</button><br/>
			<button type="submit" name="chatType" value="chat_item">おすすめアイテム</button><br/>
			</div>		
		</form>	
			
		<div class="main-container">
		
			<% if(searchType){ %>
			
			<%-- 検索結果を表示する --%>
            <% for (UserRecordDTO user : searchResults) { %>
            
            	<div class="get-account">
		            	<p class="get-icon"><img src="/oneHunting/icon/<%= user.getAccountIcon() %>"></p>
		            	<div class="get-account-item">
			            	<p class="get-name"><%= user.getAccountName() %></p>
			            	<p class="get-ken"><%= user.getAccountKen() %></p>
			            </div>
            	</div>
	            
            <% } %>
		
			
			<% } else { %>
			
			<%--　チャットの表示 --%>
			<div class="container-head">
				<%-- エラーの表示 --%>
				<%=msg %>
			</div>
			
			<div class="main-container-item">
				<% for(ChatRecordDTO record :chatList){ %>
				<form action="like" method="post">
				
				<%-- アイコン --%><img src="/oneHunting/icon/<%= record.getIcon() %>" width="60" height="60"><br>
				<%-- 投稿ID:<%= record.getPostId() %><br> --%>
				<%-- アカウントID：<%= record.getAccountId() %><br> --%>
				
				<input type ="hidden" vale="record.getPostId()">
				<input type ="hidden" vale="record.getAccountId()">
				
				<%-- アカウント名 --%>
				<span class="get-name"><%= record.getAccountName() %></span>
				<%-- 投稿日時--%>
				<span class="get-time"><%= record.getTime() %></span><br>
				<%-- 投稿画像 --%>
				<img src="/oneHunting/chat_image/<%= record.getImage() %>"><br>
				<%-- 投稿内容 --%>
				<%= record.getText() %><br>
				
				<%-- 投稿ID・投稿者アカウントIDを渡す --%>
				<input type="hidden" id="postId" name="postId" value="<%= record.getPostId() %>" />
				<input type="hidden" id="postAccountId" name="postAccountId" value="<%= record.getAccountId() %>" />
				
				<%-- TODO:いいねしてるかしていないか分岐を実装する --%>
	
					<%
					//この投稿IDのいいねアカウント一覧に、ログインアカウントが含まれているか確認する
					boolean isLike = false;
					if(record.getGoodId() != null){
						isLike = record.getGoodId().contains(loginID);
					}
					
					if(isLike){ %>
						<button type="submit" name="like" value="minus" class="good-on">♥</button>
					<% } else { %>
						<button type="submit" name="like" value="plus" class="good-off">♡</button>
					<% } %>
				
				<%-- いいね数 --%>
				<span class="get-good"><%= record.getGoodCount() %></span>
					
				</form>
				<% } %>
			</div>
			
			<% } %>
			
	</div>

		<!-- フッター -->
		<footer>
			<div class="footer">
			<%-- チャット投稿form --%>
				<form action="chat" method="post" enctype="multipart/form-data">
					<input type="text" name="comment" class="comment-box">
					
					<%-- ファイルをアップロード為、enctype="multipart/form-data"を指定 --%>
					<%-- ファイルをアップロードする --%>
					<%-- onchangeタグによりファイルアップロードされた場合にプレビューを表示する --%>
					<%-- acceptタグによりアップロードできるファイルを指定 --%>
					<%-- (※ただし、アップロード時に表示されるファイルを指定するだけであり、指定外のファイルアップロードは可能) --%>
					<input type="file" name="image" id="fileElem" multiple accept="image/*" style="display:none" />
					<button id="fileSelect" type="button" class="picture"><img src="image/picture.png" alt=""></button>
					
					<%-- 現在のチャットタイプから書き込むチャットタイプを分岐させる予定 --%>
					<button type="submit" name="chatType" value="chat_main" class="post"><img src="image/post.png" alt=""></button>
				</form>
			</div>
		 </footer>

	
	<!-- body直前でjQueryと自作のJSファイルの読み込み  -->
	<script src="https://code.jquery.com/jquery-3.7.0.min.js"
        integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <script src="js/chat.js"></script>
	
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
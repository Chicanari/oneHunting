<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- ChatRecordDTO,Listの呼び出し --%>
<%@
page import="dto.ChatRecordDTO,dto.UserRecordDTO,java.util.List,java.util.ArrayList"
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
<link rel="stylesheet" type="text/css" href="css/chat.css">
</head>

<body>	
	<div class="chat-container">
		<!-- ヘッダー-->
		
		<!-- <header-menu> -->
		
		
		<div class="header_container">
			<form action="search" method="post">
			<img class="logo" src="image/logo_white.png" alt="oneHuntingのロゴ">
			<div class="search">
				<input type="text" name="kensaku" class="kensaku" placeholder="Search">
				<!-- <input type="submit" value="検索" class="kensaku_btn"> -->
				<button type="submit"><img src="image/serch.png" alt=""></button>
			</div><!-- search -->
				<!-- .header__btn -->
	            <img class="header__btn" src="image/hamburgermenu.png" alt="">
	            </form>
		</div><!-- header_container -->
	
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
			</div><!-- nav_item -->
	     </div><!-- nav__header -->
	     </nav>
	     
		<!-- </header-menu> -->
		
		
	<div class="maindisplay">
			<!-- 左カラム -->
			<form action="chat" method="get">
			<div class="side-column">
				
				<img class="oniku" src="image/oniku.png" alt="">
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
				
				
				<img class="oniku" src="image/oniku.png" alt="">
				<button type="submit" name="chatType" value="chat_main">全体チャット</button><br/>
				
				<button type="button" class="toggle-btn"><span style="font-size:1rem;">▼　</span>話題別チャット</button>
				<div class="hidden-content">
    				<div class="chat-buttons">
    				<img class="donguri" src="image/donguri.png" alt="">
					<button type="submit" name="chatType" value="chat_shikaku">　狩猟資格</button><br/>
					<img class="donguri" src="image/donguri.png" alt="">
					<button type="submit" name="chatType" value="chat_seika">　成果報告</button><br/>
					<img class="donguri" src="image/donguri.png" alt="">
					<button type="submit" name="chatType" value="chat_item">　おすすめアイテム</button><br/>
					</div><!-- chat-buttons -->
				</div><!-- hidden-content -->
				
				<img class="kuma" src="image/kuma.png" alt="">
				
			</div><!-- side-column -->
			</form>	
	
		<div class="main-container">
	
			<% if(searchType){ %>
	
			<%-- 検索結果を表示する --%>
			<% for (UserRecordDTO user : searchResults) { %>
	
		     <div class="get-account">
		     <form action="profile_view" method="post">
		         <div class="get-account-item">
					<%-- プロフィール	IDを取得し送信する --%>
					<input type="hidden" id="postAccountId" name="postAccountId" value="<%= user.getAccountId() %>" />
					<%-- アイコン --%>
					<button type="submit" class="line-none"><p class="get-icon"><img src="/oneHunting/icon/<%= user.getAccountIcon() %>"  class="circle-image"></p><br> </button>
					<br>
	
					 <div class="get-account-infomation">
						<%-- アカウント名 --%>
						<p class="get-name">
							<button type="submit" class="line-none"><%= user.getAccountName() %></button>
						</p>
						<%-- 県名 --%>
						<p class="get-ken"><%= user.getAccountKen() %></p>
					</div><!-- get-account-infomation -->
					
				</div><!-- get-account-item -->
			</div><!-- get-account -->
			</form>
		    <% } %>
		    <% } else { %>
	
			<%--　チャットの表示 --%>
			<div class="container-head">
				<%-- チャット名の表示 --%>
				<% String chatName = "全体チャット"; //初期値状態
				if(chatType.equals("chat_fukuoka")){
						chatName = "福岡県";
				}else if(chatType.equals("chat_saga")){
						chatName = "佐賀県";
				}else if(chatType.equals("chat_oita")){
						chatName = "大分県";
				}else if(chatType.equals("chat_nagasaki")){
						chatName = "長崎県";
				}else if(chatType.equals("chat_kumamoto")){
						chatName = "熊本県";
				}else if(chatType.equals("chat_miyazaki")){
						chatName = "宮崎県";
				}else if(chatType.equals("chat_kagoshima")){
						chatName = "鹿児島県";
				}else if(chatType.equals("chat_okinawa")){
						chatName = "沖縄県";
				}else if(chatType.equals("chat_shikaku")){
						chatName = "狩猟資格";
				}else if(chatType.equals("chat_seika")){
						chatName = "成果報告";
				}else if(chatType.equals("chat_item")){
						chatName = "おすすめアイテム";
				}else{
						chatName = "全体チャット";
				}
				%>
				<%=chatName %>
			
				<%-- エラーの表示 --%>
				<%=msg %>
			</div><!-- container-head -->
	
			<div class="main-container-item">
				
					<% for(ChatRecordDTO record :chatList){ %>
					
					<%--　プロフィールを表示させるためのフォーム① --%>
					<form action="profile_view" method="post">
					
					<%-- 投稿者のIDを取得し送信する --%>
					<input type="hidden" id="postAccountId" name="postAccountId" value="<%= record.getAccountId() %>" />
									
									
				<div class="post-box">
						<%-- アイコン --%>
						<div class="icon-container">
							<button type="submit" class="line-none"><img src="/oneHunting/icon/<%= record.getIcon() %>" class="circle-image"><br></button>
							<br>
						</div><!-- icon-container -->
								
						<%-- アカウント名 --%>
					<div class="text-container">
							<span class="get-name">
								<button type="submit" class="line-none"><%= record.getAccountName() %></button>
							</span>
					</form><%--　プロフィールを表示させるためのフォーム② --%>
				
				
								
						<%--　ライクを表示させるためのフォーム①--%>
						<form id="like-form-<%= record.getPostId() %>" action="like" method="post">
						
						<%-- 投稿日時--%>
						<span class="get-time"><%= record.getTime() %></span><br>
						<%-- 投稿画像 --%>
						<% if(!record.getImage().equals("default_image.png")) {%>
						<img src="chat_image/<%= record.getImage() %>" height="150"><br>
						<% }  %>
						<%-- 投稿内容 --%>
						<%= record.getText() %><br>
									
						<%-- 投稿ID・投稿者アカウントIDを渡す --%>
						<input type="hidden" class="postId" value="<%= record.getPostId() %>" />
						<input type="hidden" class="postAccountId" value="<%= record.getAccountId() %>" />		
						<input type="hidden" class="goodCount" value="<%= record.getGoodCount() %>" />
							
						<%
						//この投稿IDのいいねアカウント一覧に、ログインアカウントが含まれているか確認する
						boolean isLike = false;
						if(record.getGoodId() != null){
							isLike = record.getGoodId().contains(loginID);
						}
							
						if(isLike){ %>
							<button type="button" name="like" value="minus" class="good-on like-button">♥</button>
						<% } else { %>
							<button type="button" name="like" value="plus" class="good-off like-button">♡</button>
						<% } %>
			
			
						<%-- いいね数 --%>
						<span  id="good-count-<%= record.getPostId() %>" class="get-good" ><%= record.getGoodCount() %></span>
					</div><!-- text-container -->
					
					</form><%--　ライクを表示させるためのフォーム② --%>
				</div><!-- post-box -->
					
					
					<% } %>
					<% } %>
					
			</div><!-- main-container-item -->		
		</div><!-- main-container -->
	</div><!-- maindisplay -->
	
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
		</div><!-- footer -->
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
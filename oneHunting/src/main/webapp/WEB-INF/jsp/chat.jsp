<%-- チャットレコードの呼び出し --%>
<%@page import="model.ChatRecord"%>

<%-- リストの使用定義 --%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- チャット画面をリクエストスコープから取得 --%>
<%
	List<ChatRecord> chatList = (List<ChatRecord>)request.getAttribute("chatList");
%>

<%-- エラーメッセージ用変数読み込み --%>
<%
	String msg = (String)request.getAttribute("msg");
%>

<!DOCTYPE html>

<%-- 言語を日本語に指定 --%>
<html lang="ja">

<meta charset="UTF-8">

<%-- javascript使用の為のmetaタグ --%>
<meta name="viewport" content = "width=device-width, initial-scale=1.0">


<!DOCTYPE html>
<html lang="ja">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width">
<!-- CSSファイル  -->
<link rel="stylesheet" type="text/css" href="css/chat.css"
	<title>
	<!-- TODO アイコン設置 -->
	</title>
</head>

<body>
	<div class="chat-container">

		<form action="search" method="post">
		<!-- ヘッダー-->
		<nav>
			<div class="wrapper">
				<input type="text" name="kensaku">
				<input type="submit" value="検索">
				<!-- .header__btn -->
            	<img class="header__btn" src="image/hamburgermenu.png" alt="">
			</div>
		</form> <!-- form action="search"の終了  -->
			
			<nav class="nav">
                <div class="nav__header">
                    <img class="nav__btn" src="image/batten-close.png" alt="">
                <ul class="nav__list">
                    <li class="nav__item"><form action="profile_view" method="post"><input type="submit" value="プロフィール"></form></li>
                    <li class="nav__item"><form action="logout" method="post"><input type="submit" value="ログアウト"></form></li>
                </ul>
                </div>
            </nav>
		</nav>
		
		<!-- 左カラム -->
		<div class="maindisplay">
		<section id="side-column">
			<div class="side-column">
			<form action="chat" method="post">
				<button type='submit' name="chatType" value="chat_fukuoka">福岡</button><br/>
				<a href="#">佐賀</a><br/>
				<a href="#">大分</a><br/>
				<a href="#">長崎</a><br/>
				<a href="#">熊本</a><br/>
				<a href="#">鹿児島</a><br/>
				<a href="#">沖縄</a><br/>
				<a href="#">雑談</a><br/>
				<a href="#">狩猟資格</a><br/>
				<a href="#">成果報告</a><br/>
				<a href="#">おすすめアイテム</a><br/>	
			</form>
			</div>
		</section>	
		<main>
		
		<!-- チャット本体部分 -->
		<section id="main">
		
		<div class="wrapper">
		
			<%-- ※チャット本体 --%>
			
				<%-- chatListとChatRecordを使用予定 --%>
				<%-- ※視認性の為にtable仮にtable内に表示 --%>
				 <table> 
					<% if(chatList != null){ %>
						<% for(ChatRecord chat : chatList) {%>
						<tr>
							<td><%= chat.getPostId() %></td>  <%-- アカウントID --%> 
							<td><%= chat.getAccountId() %></td>  <%-- 名前 --%>
							<td><%= chat.getAccountName() %></td>  <%-- アイコンファイル名 --%>
							<td><%= chat.getIcon() %></td>  <%-- チャット本文 --%>
							<td><%= chat.getTime() %></td>  <%-- 投稿日時--%>
							<td><%= chat.getText() %></td>  <%-- いいねの総数 --%>
							<td><%= chat.getImage() %></td>  <%-- 投稿画像のファイル名 --%>
						</tr>
						<% } %>
					<% } %>
				 </table>
				
				<%-- 画像投稿用の仮説form ※書き込みformと統合予定 --%>
				
				
					<%-- ファイルをアップロード為、enctype="multipart/form-data"を指定 --%>
					<%-- ファイルをアップロードする --%>
					<%-- onchangeタグによりファイルアップロードされた場合にプレビューを表示する --%>
					<%-- acceptタグによりアップロードできるファイルを指定 --%>
					<%-- (※ただし、アップロード時に表示されるファイルを指定するだけであり、指定外のファイルアップロードは可能) --%>
					<p>アイコン　<input type="file" name="image"  accept="image/*" onchange="previewFile(this);"></p>
				
					<input type="submit" value="投稿">
				
					<%-- 画像プレビューの呼び出し --%>
					<img id="preview">
				</form>
				
				<%= msg %>
				
				<%-- 画像プレビューを表示するためのスクリプト構文 --%>
				<script>
				function previewFile(hoge){
					var fileData = new FileReader();
					fileData.onload = (function() {
					document.getElementById('preview').src = fileData.result;
					});
					fileData.readAsDataURL(hoge.files[0]);}
				</script>
		
				
		</div>
		</section>
		</main>
		</div>
		
		<!-- フッター -->
		<footer>
			<div class="footer">
				<form action="chat" method="post" enctype="multipart/form-data">
				<input type="text" name="comment">	
				<input type="submit" value="送信">
			</div>
		 </footer>
	</div>
	
	<!-- body直前でjQueryと自作のJSファイルの読み込み  -->
	<script src="https://code.jquery.com/jquery-3.7.0.min.js"
        integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <script src="js/chat.js"></script>
    <p>テスト</p>
</body>
</html>
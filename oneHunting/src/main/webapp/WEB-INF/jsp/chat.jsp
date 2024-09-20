<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- イメージファイル名の受け取り ※テスト投稿用 --%>
<%
	String imageName = (String)request.getAttribute("imageName");
%>

<%-- エラーメッセージ用変数読み込み --%>
<%
	String msg = (String)request.getAttribute("msg");
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

<%-- CSSファイル --%>
<link rel="stylesheet" type="text/css" href="css/chat.css">

<title>Insert title here</title>
</head>
<body>
<h1>チャット画面</h1>

	<form action="search" method="post">
	
	
<%-- 山﨑画面レイアウトマークアップ --%>
	<div class="chat-container">
		<!-- ヘッダー-->
		<nav>
			<div class="wrapper">
				<input type="text" name="kensaku">
				<input type="submit" value="検索">
				<!-- .header__btn -->
            	<img class="header__btn" src="image/hamburgermenu.png" alt="">
			</div>
			
			<nav class="nav">
                <div class="nav__header">
                    <img class="nav__btn" src="image/batten-close.png" alt="">
                <ul class="nav__list">
                    <li class="nav__item"><a href="#">プロフィール</a></li>
                    <li class="nav__item"><a href="index.jsp">ログアウト</a></li>
                </ul>
                </div>
            </nav>
		</nav>
		<div class="maindisplay">
		<!-- 左カラム -->
		<section id="side-column">
			<div class="side-column">
			<a href="#">福岡</a><br/>
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
			</div>
		</section>	
		<main>
		<!-- チャット本体部分 -->
		<section id="main">
			<div class="wrapper">
				<p>ここにコメント入るはずここにコメント入るはずここにコメント入るはずここにコメント入るはず</p>
			</div>
		</section>
		</main>
		</div>
		<!-- フッター -->
		<footer>
			<div class="footer">
				<input type="text" name="comment">	
				<input type="submit" value="送信">
			</div>
		 </footer>
	</div>
	<!-- body直前でjQueryと自作のJSファイルの読み込み  -->
	<script src="https://code.jquery.com/jquery-3.7.0.min.js"
        integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <script src="js/chat.js"></script>
	

<%-- 沼田さん画像機能設定　--%>
		<%-- name適宜変更お願いします。 --%>
		<input type="text" name="kensaku"> <input type="submit"
			value="検索">
	</form>

	<%-- ※テスト表示用 --%>
	<img src="/oneHunting/image/<%=imageName%>">

	<%-- 画像投稿用の仮説form ※書き込みformと統合予定 --%>
	<form action="chat" method="post" enctype="multipart/form-data">

		<%-- ファイルをアップロード為、enctype="multipart/form-data"を指定 --%>
		<%-- ファイルをアップロードする --%>
		<%-- onchangeタグによりファイルアップロードされた場合にプレビューを表示する --%>
		<%-- acceptタグによりアップロードできるファイルを指定 --%>
		<%-- (※ただし、アップロード時に表示されるファイルを指定するだけであり、指定外のファイルアップロードは可能) --%>
		<p>
			アイコン <input type="file" name="image" accept="image/*"
				onchange="previewFile(this);">
		</p>

		<input type="submit" value="投稿">

		<%-- 画像プレビューの呼び出し --%>
		<img id="preview">
	</form>

	<%=msg%>


	<form action="profile_view" method="post">
		<input type="submit" value="プロフィール">
	</form>
	<form action="logout" method="post">
		<input type="submit" value="ログアウト">
	</form>


	<%-- 画像プレビューを表示するためのスクリプト構文 --%>
	<script>
		function previewFile(hoge) {
			var fileData = new FileReader();
			fileData.onload = (function() {
				document.getElementById('preview').src = fileData.result;
			});
			fileData.readAsDataURL(hoge.files[0]);
		}
	</script>
	
	

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
<html lang="ja">
<head>
<meta charset="UTF-8">

<%-- webフォント --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Zen+Maru+Gothic:wght@300;400;500;700;900&display=swap" rel="stylesheet">
<!-- CSSファイル  -->
<link rel="stylesheet" type="text/css" href="css/register_view.css">

<title>Insert title here</title>
</head>
<body>
<img class="register_frame" src="image/register.jpg" alt="">

<h1>登録完了</h1>

<form action="chat" method="get">
<input type="submit" value="チャットをはじめる" class="chat-start"></form>
<div class="sendmail">メールが送信されました！</div>

<!-- Canvas Confettiを実行するためのスクリプト -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/canvas-confetti@1.3.2/dist/confetti.browser.min.js"></script>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.7.0.min.js"
	integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
 <script src="js/register_view.js"></script>
</body>
</html>
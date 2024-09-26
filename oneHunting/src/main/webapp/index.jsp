<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- エラーメッセージの取得 --%>
<%

request.setCharacterEncoding("UTF-8");
String message = (String)request.getAttribute("message");
//エラーメッセージのnullチェック
if(message == null)	message = "";

%>

<%-- ログイン情報の取得　※ログイン状態のときはチャットへ移動する --%>
<%
String loginID = (String)session.getAttribute("loginID");
Boolean login = (Boolean)session.getAttribute("login");

//nullチェック
if(login == null)	login = false;

//ログインIDが入っているか、ログインがtrueの時ログインしていると判断する
if( loginID != null || login == true ) {
	//ログイン状態の時は、全体チャットに移動する
	response.sendRedirect("chat");
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
<%-- CSSファイル --%>
<link rel="stylesheet" type="text/css" href="css/index.css">

<title>ログイン</title>
</head>
<body>
<h1><img class="logo" src="image/logo_brown.png" alt="oneHuntingのロゴ"></h1>
<img class="login_frame" src="image/login_frame.png" alt="">
<div class="item">
	<div class="err"><%= message %></div>
	<form action="login" method="post">
	
	<p class="id">ID</p>
	<input type="text" name="id" class="id_box">
	<p class="ps">パスワード</p>
	<input type="password" name="pw" class="ps_box"></br>
	<input type="submit" value="ログイン" class="login_btn"></br>	
	</form>

	<form action="signup" method="get">
	<input type="submit" value="新規登録" class="register_btn">
	</form>
</div>

<!-- body直前でjQueryと自作のJSファイルの読み込み  -->
<script src="https://code.jquery.com/jquery-3.7.0.min.js"
 integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script src="js/index.js"></script>
</body>
</html>
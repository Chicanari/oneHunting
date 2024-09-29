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

<%-- 言語を日本語に指定 --%>
<html lang="ja">
<html>
<head>
<meta charset="UTF-8">

<%-- webフォント --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Zen+Maru+Gothic:wght@300;400;500;700;900&display=swap" rel="stylesheet">
<!-- CSSファイル  -->
<link rel="stylesheet" type="text/css" href="css/register_view.css">

<%-- javascript使用の為のmetaタグ --%>
<meta name="viewport" content = "width=device-width, initial-scale=1.0">
<title>登録完了</title>
</head>
<div class="confetti"><!--  -->
<body>

<canvas></canvas><!--  -->


<form action="chat" method="post"><input type="submit" value="メインチャットに戻る"></form>

</div><!--  -->

<!-- body直前でjQueryと自作のJSファイルの読み込み  -->
<script src="https://code.jquery.com/jquery-3.7.0.min.js"
	integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script src="js/register_view.js"></script>
</body>
</html>
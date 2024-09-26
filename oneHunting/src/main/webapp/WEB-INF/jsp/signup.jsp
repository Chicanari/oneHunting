<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

request.setCharacterEncoding("UTF-8");
String message = (String)request.getAttribute("message");

if(message == null){
	message = "";
}

%>
<!DOCTYPE html>
<%-- 言語を日本語に指定 --%>
<html lang="ja">
<head>
<meta charset="UTF-8">
<%-- webフォント --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Zen+Maru+Gothic:wght@300;400;500;700;900&display=swap" rel="stylesheet">
<!-- CSSファイル  -->
<link rel="stylesheet" type="text/css" href="css/signup.css">
<title>新規登録</title>
</head>
<body>
<img class="signup_frame" src="image/signup_frame.png" alt="">

<%= message %>
<div class="signup-container">
	<form action="signup" method="post">
	
		<img src="icon/default_icon.png" width="100">
	
		<p class="name">名前<br>
		<input type="text" name="name"></p>
		<p class="id">ID<br>
		<input type="text" name="id"></p>
		<p class="ps">パスワード<br>
		<input type="password" name="pw">
		<p class="mail">メールアドレス<br>
		<input type="text" name="mail"></p>
		
		<p class="ken">県名</p>
		<div class="ken-radio">
		<input type="radio" name="ken" value="福岡県">福岡県
		<input type="radio" name="ken" value="大分県">大分県
		<input type="radio" name="ken" value="佐賀県">佐賀県
		<input type="radio" name="ken" value="長崎県">長崎県<br>
		<input type="radio" name="ken" value="熊本県">熊本県
		<input type="radio" name="ken" value="宮崎県">宮崎県
		<input type="radio" name="ken" value="鹿児島県">鹿児島県
		<input type="radio" name="ken" value="沖縄県">沖縄県
		</div>
		<input type="submit" value="登録">
		
	</form>
	
	<a href="/oneHunting">戻る</a>
</div>

</body>
</html>
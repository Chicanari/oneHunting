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
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>新規登録</h1>
<%= message %>
<form action="signup" method="post">

	<img src="icon/default_icon.png" width="100">

	<p>名前<br><input type="text" name="name"></p>
	<p>ID<br><input type="text" name="id"></p>
	<p>PW<br><input type="password" name="pw"></p>
	<p>メールアドレス<br><input type="text" name="mail"></p>
	
	<p>
	県名<br>
	<input type="radio" name="ken" value="福岡県">福岡県
	<input type="radio" name="ken" value="大分県">大分県
	<input type="radio" name="ken" value="佐賀県">佐賀県
	<input type="radio" name="ken" value="長崎県">長崎県<br>
	<input type="radio" name="ken" value="熊本県">熊本県　
	<input type="radio" name="ken" value="宮崎県">宮崎県　
	<input type="radio" name="ken" value="鹿児島県">鹿児島県　
	<input type="radio" name="ken" value="沖縄県">沖縄県　
	</p>
	
	<p><input type="submit" value="登録"></p>
	
</form>

<a href="/oneHunting">戻る</a>

</body>
</html>
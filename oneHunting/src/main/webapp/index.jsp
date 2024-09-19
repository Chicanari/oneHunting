<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>ログイン</h1>
<p>ログインを押したらチャット画面に飛びます</p>

<form action="login" method="post">
	
	<p>ID:<input type="text" name="id"></p>
	<p>PW:<input type="password" name="pw"></p>
	
	<p><input type="submit" value="ログイン"></p>
	
</form>

<form action="signup" method="get"><input type="submit" value="新規登録"></form>


</body>
</html>
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

<h1>ログイン</h1>
<%= message %>
<form action="login" method="post">
	
	<p>ID:<input type="text" name="id"></p>
	<p>PW:<input type="password" name="pw"></p>
	
	<p><input type="submit" value="ログイン"></p>
	
</form>

<form action="signup" method="get"><input type="submit" value="新規登録"></form>


</body>
</html>
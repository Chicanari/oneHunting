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
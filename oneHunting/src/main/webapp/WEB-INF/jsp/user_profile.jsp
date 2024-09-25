<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- 副島さんの書くところ サーブレットから値を受け取ってください --%>
<%@ page import="dto.UserProfileDTO" %>
<%
UserProfileDTO profile = (UserProfileDTO) request.getAttribute("profile");


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
    
<%-- エラーメッセージ用変数読み込み --%>
<%
	String msg = (String)request.getAttribute("msg");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>ユーザープロフィールの表示</h1>

<%-- Q:<%= %>←これで値を取ってください --%>
<form action="profile_edit" method="get"><input type="submit" value="編集"></form>

<p>名前</p>
<%= profile.getAccountName() %>
<p>ID</p>
<%= profile.getAccountId() %>>
<p>県名</p>
<%= profile.getAccountKen() %>>
<p>自己紹介</p>
<%= profile.getAccountIntroduction() %>
<p>獲得ポイント</p>
<%= profile.getAccountGoodPoint() %>

<form action="chat" method="get"><input type="submit" value="戻る"></form>

</body>
</html>
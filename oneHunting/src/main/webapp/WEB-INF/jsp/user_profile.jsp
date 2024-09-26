<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- UserProfileDTOの実装 --%>
<%@ page import="dto.UserProfileDTO" %>
<%--  --%>
<%
UserProfileDTO profile = (UserProfileDTO) session.getAttribute("profile");


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

<%-- 自身のプロフィールであれば編集ボタンを表示 --%>
<% if(loginID != null && loginID.equals(profile.getAccountId())){ %>
<form action="profile_edit" method="get"><input type="submit" value="編集"></form>
<% } %>

<%-- 実装したUserProfileDTOのメソッドで表示するアイコンファイル名、名前、ID、県名、自己紹介文、現在のポイント数を表示 --%>
<%-- アイコンの画像を表示・画像幅の調整 --%>
<img src ="/oneHunting/icon/<%= profile.getAccountIcon() %>" width="50">

<p>名前</p>
<%= profile.getAccountName() %>
<p>ID</p>
<%= profile.getAccountId() %>
<p>県名</p>
<%= profile.getAccountKen() %>
<p>自己紹介</p>
<%= profile.getAccountIntroduction() %>
<p>獲得ポイント</p>
<%= profile.getAccountGoodPoint() %>

<form action="chat" method="get"><input type="submit" value="戻る"></form>

</body>
</html>
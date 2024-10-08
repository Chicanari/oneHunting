<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- UserProfileDTOの実装 --%>
<%@ page import="dto.UserProfileDTO" %>
<%--  --%>
<%
UserProfileDTO profile = (UserProfileDTO) session.getAttribute("profile");
int pointNum = Integer.parseInt(profile.getAccountGoodPoint());

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
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザープロフィールの表示</title>
<%-- webフォント --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Zen+Maru+Gothic:wght@300;400;500;700;900&display=swap" rel="stylesheet">
<!-- CSSファイル  -->
<link rel="stylesheet" type="text/css" href="css/user_profile.css">
</head>
<body>

<img class="signup_frame" src="image/signup_frame.png" alt="">

<div class="user_profile-container">
	<%-- 自身のプロフィールであれば編集ボタンを表示 --%>
	<% if(loginID.equals(profile.getAccountId())){ %>
	<div class="edit">
		<form action="profile_edit" method="get"><input type="submit" value="編集" class="edit_button"></form>
	</div>
	<% } %>
	
	<%-- 実装したUserProfileDTOのメソッドで表示するアイコンファイル名、名前、ID、県名、自己紹介文、現在のポイント数を表示 --%>
	<%-- アイコンの画像を表示・画像幅の調整 --%>
	<div class="icon"><img src ="/oneHunting/icon/<%= profile.getAccountIcon() %>" class="circle-image"></div>
	
	<p class="name">名前</p>
	<%= profile.getAccountName() %>
	<p>ID</p>
	<%= profile.getAccountId() %>
	<p>県名</p>
	<%= profile.getAccountKen() %>
	<p>自己紹介</p>
	<% if(profile.getAccountIntroduction().length() > 84){ %>
	<p class="box"><%= profile.getAccountIntroduction() %></p>
	<% }else{ %>
	<p class="box-84"><%= profile.getAccountIntroduction() %></p>
	<% } %>
	<p>獲得ポイント</p>
	<%= profile.getAccountGoodPoint() %>
	<div class="return"><form action="chat-branch" method="get"><input type="submit" value="戻る" class="return_button"></form></div>

	<%-- デモのため１ポイント以上でプレゼントボックスが表示 --%>
	<%  if(loginID.equals(profile.getAccountId()) && pointNum>=0 ){ %>
	<button type="button" id="present" class="present shake"><img src="image/present.png" width="150" class="shake-animation"></button>
	
	<!-- ポップアップの内容 -->
    <div id="popup" class="popup hidden">
        <div class="popup-content">
        	<form action="coupon" method="">
            <p><button id="send-coupon" class="coupon"><img src="image/coupon.png"></button></p>
            </form>

            <p>クーポンをクリック！<br><br>
            ※登録メールあてに<br>クーポンコードが届きます。</p>
            <p><button id="close-popup">×</button></p>
        </div>
    </div>
	
	<% } %>
	
</div>

<!-- body直前でJSの取り込み -->
<script src="js/user_profile.js"></script>
</body>
</html>
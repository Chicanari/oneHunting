<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>チャット画面</h1>

<form action="search" method="post">
<%-- name適宜変更お願いします。 --%>
<input type="text" name="kensaku">
<input type="submit" value="検索">
</form>

<form action="profile_view" method="post"><input type="submit" value="プロフィール"></form>
<form action="logout" method="post"><input type="submit" value="ログアウト"></form>

</body>
</html>
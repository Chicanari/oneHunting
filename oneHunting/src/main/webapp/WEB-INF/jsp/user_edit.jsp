<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>プロフィール編集画面</h1>

<form action="profile_edit" method="post">

<p>名前<br><input type="text" name="id"></p>
<p>ID<br></p>
<p>メールアドレス<br><input type="password" name="pw"></p>

<p>
県名<br>
<input type="radio" name="ken" value="chat_fukuoka">福岡県　
<input type="radio" name="ken" value="chat_oita">大分県　
<input type="radio" name="ken" value="chat_saga">佐賀県　
<input type="radio" name="ken" value="chat_nagasaki">長崎県　<br>
<input type="radio" name="ken" value="chat_kumamoto">熊本県　
<input type="radio" name="ken" value="chat_miyazaki">宮崎県　
<input type="radio" name="ken" value="chat_kagoshima">鹿児島県　
<input type="radio" name="ken" value="chat_okinawa">沖縄県　

</p>

<p>自己紹介<br><textarea rows="10" cols="50"></textarea></p>

<p><input type="submit" value="登録"></p>

</form>


</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>新規登録</h1>
<form action="signup" method="post">

	<p>登録を押すと登録完了画面に飛びます</p>
	
	<p>名前<br><input type="text" name="name"></p>
	<p>ID<br><input type="text" name="id"></p>
	<p>PW<br><input type="password" name="pw"></p>
	<p>メールアドレス<br><input type="text" name="mail"></p>
	
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
	
	<p><input type="submit" value="登録"></p>
	
</form>

<a href="">戻る</a>

</body>
</html>
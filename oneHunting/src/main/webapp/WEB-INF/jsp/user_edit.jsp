<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- エラーメッセージ用変数読み込み --%>
<%
	String msg = (String)request.getAttribute("msg");
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

<!DOCTYPE html>

<%-- 言語を日本語に指定 --%>
<html lang="ja">

<head>
<meta charset="UTF-8">

<%-- javascript使用の為のmetaタグ --%>
<meta name="viewport" content = "width=device-width, initial-scale=1.0">

<title>Insert title here</title>
</head>
<body>
<h1>プロフィール編集画面</h1>

<%-- ファイルをアップロード為、enctype="multipart/form-data"を指定 --%>
<form action="profile_edit" method="post" enctype="multipart/form-data">

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
	
	<%-- ファイルをアップロードする --%>
	<%-- onchangeタグによりファイルアップロードされた場合にプレビューを表示する --%>
	<%-- acceptタグによりアップロードできるファイルを指定 --%>
	<%-- (※ただし、アップロード時に表示されるファイルを指定するだけであり、指定外のファイルアップロードは可能) --%>
	<p>アイコン　<input type="file" name="icon"  accept="image/*" onchange="previewFile(this);"></p>
	<%-- 画像プレビューの呼び出し --%>
	<img id="preview">
	<%= msg %>
	
	<p>自己紹介<br><textarea rows="10" cols="50"></textarea></p>
	
	<p><input type="submit" value="登録"></p>
</form>

<%-- 画像プレビューを表示するためのスクリプト構文 --%>
<script>
function previewFile(hoge){
	var fileData = new FileReader();
	fileData.onload = (function() {
	document.getElementById('preview').src = fileData.result;
	});
	fileData.readAsDataURL(hoge.files[0]);}
</script>

</body>
</html>
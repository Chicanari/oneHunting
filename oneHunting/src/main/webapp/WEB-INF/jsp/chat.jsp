<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- イメージファイル名の受け取り ※テスト投稿用 --%>
<%
	String imageName = (String)request.getAttribute("imageName");
%>

<%-- エラーメッセージ用変数読み込み --%>
<%
	String msg = (String)request.getAttribute("msg");
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
<h1>チャット画面</h1>

<form action="search" method="post">
<%-- name適宜変更お願いします。 --%>
<input type="text" name="kensaku">
<input type="submit" value="検索">
</form>

<%-- ※テスト表示用 --%>
<img src="/oneHunting/image/<%= imageName %>">

<%-- 画像投稿用の仮説form ※書き込みformと統合予定 --%>
<form action="chat" method="post" enctype="multipart/form-data">

	<%-- ファイルをアップロード為、enctype="multipart/form-data"を指定 --%>
	<%-- ファイルをアップロードする --%>
	<%-- onchangeタグによりファイルアップロードされた場合にプレビューを表示する --%>
	<%-- acceptタグによりアップロードできるファイルを指定 --%>
	<%-- (※ただし、アップロード時に表示されるファイルを指定するだけであり、指定外のファイルアップロードは可能) --%>
	<p>アイコン　<input type="file" name="image"  accept="image/*" onchange="previewFile(this);"></p>

	<input type="submit" value="投稿">

	<%-- 画像プレビューの呼び出し --%>
	<img id="preview">
</form>

<%= msg %>


<form action="profile_view" method="post"><input type="submit" value="プロフィール"></form>
<form action="logout" method="post"><input type="submit" value="ログアウト"></form>


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
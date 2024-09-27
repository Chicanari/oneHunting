<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- エラーメッセージ用変数読み込み --%>
<%
	String msg = (String)request.getAttribute("msg");
%>

<%-- 初期値入力用の変数 --%>
<%
	String initialName = (String)request.getAttribute("initialName");
	String initialIcon = (String)request.getAttribute("initialIcon");
	String initialMail = (String)request.getAttribute("initialMail");
	String initialKen = (String)request.getAttribute("initialKen");
	String initialText = (String)request.getAttribute("initialText");
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
	
	<%-- エラーメッセージ表示 --%>
	<%=msg %>
	
	<%-- アイコンの画像を表示・画像幅の調整 --%>
	<img src ="/oneHunting/icon/<%=initialIcon %>" width="50">
	
	<p>
	<b>アイコン　</b>
	<input type="file" name="icon"  id="fileElem" multiple accept="image/*" onchange="previewFile(this);"  style="display:none" />
	<button id="fileSelect" type="button" class="picture"><img src="image/picture.png" alt=""></button>
	</p>
	
	<%-- ファイルをアップロード為、enctype="multipart/form-data"を指定 --%>
	<%-- ファイルをアップロードする --%>
	<%-- onchangeタグによりファイルアップロードされた場合にプレビューを表示する --%>
	<%-- acceptタグによりアップロードできるファイルを指定 --%>
	<%-- (※ただし、アップロード時に表示されるファイルを指定するだけであり、指定外のファイルアップロードは可能) --%>
	<input type="file" name="image" id="fileElem" multiple accept="image/*" style="display:none" />
					
	<%-- 画像プレビューの呼び出し --%>
	<img id="preview">

	<p><b>名前</b><br><input type="text" name="name" value="<%=initialName %>"></p>
	<p><b>ID</b><br><%=loginID %></p>
	<p><b>メールアドレス</b><br><input type="text" name="mail" value="<%=initialMail %>"></p>
	
	<p>
	<b>県名</b><br>
	<input type="radio" name="ken" value="福岡県" <%= "福岡県".equals(initialKen) ? "checked" : "" %>>福岡県　
	<input type="radio" name="ken" value="大分県" <%= "大分県".equals(initialKen) ? "checked" : "" %>>大分県　
	<input type="radio" name="ken" value="佐賀県" <%= "佐賀県".equals(initialKen) ? "checked" : "" %>>佐賀県　
	<input type="radio" name="ken" value="長崎県" <%= "長崎県".equals(initialKen) ? "checked" : "" %>>長崎県　<br>
	<input type="radio" name="ken" value="熊本県" <%= "熊本県".equals(initialKen) ? "checked" : "" %>>熊本県　
	<input type="radio" name="ken" value="宮崎県" <%= "宮崎県".equals(initialKen) ? "checked" : "" %>>宮崎県　
	<input type="radio" name="ken" value="鹿児島県" <%= "鹿児島県".equals(initialKen) ? "checked" : "" %>>鹿児島県
	<input type="radio" name="ken" value="沖縄県" <%= "沖縄県".equals(initialKen) ? "checked" : "" %>>沖縄県　	
	</p>
	
	
	<p><b>自己紹介</b><br><textarea name="introduction" style="width:400px; height:100px;"><%=initialText %></textarea></p>
	
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

<%-- ファイルが選択されていませんを見た目上消し去るスクリプト構文 --%>
<script>
const fileSelect = document.getElementById("fileSelect");
const fileElem = document.getElementById("fileElem");

fileSelect.addEventListener("click", (e) => {
	if (fileElem) {
	fileElem.click();
	}
}, false);
</script>

</body>
</html>
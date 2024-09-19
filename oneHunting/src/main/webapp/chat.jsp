<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width">
<!-- CSSファイル  -->
<link rel="stylesheet" type="text/css" href="css/chat.css"
	<title>
	<!-- TODO アイコン設置 -->
	</title>
</head>
<body>
	<div class="chat-container">

		<form action="search" method="post">
		<!-- ヘッダー-->
		<nav>
			<div class="wrapper">
				<input type="text" name="kensaku">
				<input type="submit" value="検索">
				<!-- .header__btn -->
            	<img class="header__btn" src="image/hamburgermenu.png" alt="">
			</div>
			
			<nav class="nav">
                <div class="nav__header">
                    <img class="nav__btn" src="image/batten-close.png" alt="">
                </div>
                <ul class="nav__list">
                    <li class="nav__item"><a href="#">プロフィール</a></li>
                    <li class="nav__item"><a href="#">ログアウト</a></li>
                </ul>
            </nav>
		</nav>
		<div class="maindisplay">
		<!-- 左カラム -->
		<section id="side-column">
			<div class="side-column">
			<a href="#">福岡</a><br/>
			<a href="#">佐賀</a><br/>
			<a href="#">大分</a><br/>
			<a href="#">長崎</a><br/>
			<a href="#">熊本</a><br/>
			<a href="#">鹿児島</a><br/>
			<a href="#">沖縄</a><br/>
			<a href="#">雑談</a><br/>
			<a href="#">狩猟資格</a><br/>
			<a href="#">成果報告</a><br/>
			<a href="#">おすすめアイテム</a><br/>	
			</div>
		</section>	
		<main>
		<!-- チャット本体部分 -->
		<section id="main">
			<div class="wrapper">
				<p>ここにコメント入るはずここにコメント入るはずここにコメント入るはずここにコメント入るはず</p>
			</div>
		</section>
		</main>
		</div>
		<!-- フッター -->
		<footer>
			<div class="footer">
				<input type="text" name="comment">	
				<input type="submit" value="送信">
			</div>
		 </footer>
	
		<!-- body直前でjQueryと自作のJSファイルの読み込み  -->
		<script>src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
		<!-- JSファイル  -->
		<script src="js/chat.js"></script>
	</div>
</body>
</html>
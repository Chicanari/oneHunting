

/* チャットの表示を最新のもの（下）から表示させる */
window.onload = function() {
  var container = document.querySelector('.main-container');
  container.scrollTop = container.scrollHeight;
};

/* いいねボタンを監視するメソッド　※DOM（Document Object Model）　指定したCSSセレクタに一致するすべての要素を取得する */
document.querySelectorAll('.like-button').forEach(button => {
	
    button.addEventListener('click', function (event) {
		
        event.preventDefault(); // ページ遷移を防ぐ
        
        // ボタンを非活性化
        this.disabled = true;
        
        // ボタンが押された時に、フォームデータを取得する
        const form = this.closest('form'); // ボタンの親フォームを取得
        //投稿コメントのIDを取得する
        const postId = form.querySelector('.postId').value;
        console.log("Post ID:", postId);
        //ポストしたアカウントのIDを取得する
        const postAccountId = form.querySelector('.postAccountId').value;
        
        // いいねの状態を取得
        const like = this.value;
        
         // いいね数を表示する要素を取得
        const goodCountElement = document.getElementById(`good-count-${postId}`);

        // Fetch APIを使って非同期リクエストを送信
        fetch('/oneHunting/like', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ 
				like: like, 
				postId: postId , 
				postAccountId: postAccountId 
				}),
        })
        .then(response => {
			console.log("Received response:");
			return response.json();
			})
        .then(data => {
			
			console.log("Response data:", data);
            // サーバーからのレスポンスで、いいねのカウントを更新
            goodCountElement.textContent = data.newLikeCount;
            
             // ボタンの状態を更新
            if (like === "plus") {
                this.value = "minus"; // ボタンの値を更新
                this.classList.remove('good-off'); // クラスを更新
                this.classList.add('good-on');
                this.textContent = '♥'; // ボタンのテキストを更新
            } else {
                this.value = "plus"; // ボタンの値を更新
                this.classList.remove('good-on'); // クラスを更新
                this.classList.add('good-off');
                this.textContent = '♡'; // ボタンのテキストを更新
            }
            
        })
        .finally(() => {
            // 処理が完了したらボタンを再び活性化
            this.disabled = false;
        })
        .catch(error => {
            console.error('Error:', error);
        });
    });
    
});

/* 「話題別チャット」をクリックしたときの動作 */
const toggleButton = document.querySelector('.toggle-btn');
const hiddenContent = document.querySelector('.hidden-content');

// ページが読み込まれた時に、localStorageから状態を取得して表示を設定
document.addEventListener('DOMContentLoaded', function() {
    const isVisible = localStorage.getItem('hiddenContentVisible');
    
    if (isVisible === 'true') {
        hiddenContent.style.display = 'block'; // 表示
    } else {
        hiddenContent.style.display = 'none'; // 非表示
    }
});

// ボタンクリック時に表示・非表示を切り替え、状態をlocalStorageに保存
toggleButton.addEventListener('click', function() {
    if (hiddenContent.style.display === "none" || hiddenContent.style.display === "") {
        hiddenContent.style.display = "block"; // 表示
        localStorage.setItem('hiddenContentVisible', 'true'); // 状態を保存
    } else {
        hiddenContent.style.display = "none"; // 非表示
        localStorage.setItem('hiddenContentVisible', 'false'); // 状態を保存
    }
});



/* hamburger */
{
    $(function(){
        $('.header__btn').on('click', function(){
            $('.nav').toggleClass('active');
        });

        $('.nav__btn').on('click', function(){
            $('.nav').removeClass('active');
        });
    });
    
}
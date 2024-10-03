// プレゼントボックスとポップアップの要素を取得
const presentBox = document.getElementById('present');
const popup = document.getElementById('popup');
const closePopupButton = document.getElementById('close-popup');

// プレゼントボックスをクリックしたときの処理
presentBox.addEventListener('click', function() {
    popup.style.display = 'block'; // ポップアップを表示
});

// 「閉じる」ボタンをクリックしたときの処理
closePopupButton.addEventListener('click', function() {
    popup.style.display = 'none'; // ポップアップを非表示
});
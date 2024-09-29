document.addEventListener("DOMContentLoaded", function() {
	// Canvas Confettiを実行する関数
	confetti({
		       particleCount: 100, // コンフェティの粒子数
		       spread: 70, // コンフェティが広がる範囲（度数）
		       origin: { y: 0.4 } // コンフェティが発生するY座標の位置（0が上、1が下）
		     });
});	

	 


/* チャットの表示を最新のもの（下）から表示させる */
window.onload = function() {
  var container = document.querySelector('.main-container');
  container.scrollTop = container.scrollHeight;
};

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
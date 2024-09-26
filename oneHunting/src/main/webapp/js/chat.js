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
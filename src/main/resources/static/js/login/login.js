$('.message a').click(function(){
    $('.transform-form').animate({height: "toggle", opacity: "toggle"}, "slow");
});

function login(){
    console.log($("#login-email").val());
    console.log($("#login-password").val());




    $.ajax({
        type: 'POST',
        url: "/notice/" + id,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8'
    }).done(function() {
        alert('공지사항이 삭제되었습니다.');
        window.location.href = '/notice';
    }).fail(function (error) {
        alert('공지사항 삭제를 실패하였습니다.\n관리자에게 문의해 주세요.');
    });
}
$('.message a').click(function () {
    $('.transform-form').animate({height: "toggle", opacity: "toggle"}, "slow");
    let loginTitle = $(this).text();
    $("#login-title").text(loginTitle);
});

function login() {
    let data = {
        "email": $('#login-email').val(),
        "password": $('#login-password').val()
    };
    console.log(data);

    $.ajax({
        type: 'POST',
        url: "/login/authenticate",
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        location.href = "/";
    }).fail(function (error) {
        alert('이메일 또는 비밀번호를 확인해 주세요.');
    });
}
ㅇ
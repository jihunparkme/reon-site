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

    $.ajax({
        type: 'POST',
        url: "/login/authenticate",
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        location.href = "/";
    }).fail(function () {
        alert('이메일 또는 비밀번호를 확인해 주세요.');
    });
}

function signUp() {
    let data = {
        "firstName": $('#sign-up-first-name').val(),
        "lastName": $('#sign-up-last-name').val(),
        "email": $('#sign-up-email').val(),
        "password": $('#sign-up-password').val()
    };

    $.ajax({
        type: 'POST',
        url: "/member/sign-up",
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        alert('회원가입이 완료되었습니다. 다시 로그인해 주세요.');
        location.href = "/";
    }).fail(function (error) {
        let errorMessage = error.responseText;
        alert(errorMessage);
    });
}
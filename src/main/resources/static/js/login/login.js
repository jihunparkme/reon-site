$(function() {
    $('.kakao').click(function () {
        loginKakao();
    });

    $('.google').click(function () {
        loginGoogle();
    });

    $('.apple').click(function () {
        loginApple();
    });

    $('.email').click(function () {
        loginEmail();
    });
});

function loginKakao() {
    location.href = "/oauth2/authorization/kakao"
}

function loginGoogle() {
    location.href = "/oauth2/authorization/google"
}

function loginApple() {
    alert('준비중입니다.');
    // location.href = "/oauth2/authorization/apple"
}

function loginEmail() {
    location.href = "login/email"
}

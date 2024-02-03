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
        url: "/login/email/authenticate",
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

    if (data.email.length == 0) {
        alert("email is required.");
        return;
    }

    if (data.firstName.length == 0) {
        alert("firstName is required.");
        return;

    }
    if (data.lastName.length == 0) {
        alert("lastName is required.");
        return;
    }

    if (data.password.length == 0) {
        alert("password is required.");
        return;
    }

    $.ajax({
        type: 'POST',
        url: "/login/email/sign-up",
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        alert('회원가입이 완료되었습니다. 다시 로그인해 주세요.');
        location.href = "/";
    }).fail(function (error) {
        let responseJson = error.responseJSON;
        alert(responseJson.message);
    });
}

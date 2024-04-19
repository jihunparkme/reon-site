document.addEventListener("DOMContentLoaded", function(event) {
    let form = document.getElementById('sign-in-form');
    form.addEventListener('keydown', function(event) {
        if (event.key === "Enter") {
            event.preventDefault();
            login();
        }
    });
});

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
        alert('Please check your email or password.');
    });
}

function signUp() {
    let data = {
        "firstName": $('#sign-up-first-name').val(),
        "lastName": $('#sign-up-last-name').val(),
        "email": $('#sign-up-email').val(),
        "password": $('#sign-up-password').val(),
        "confirmPassword": $('#sign-up-confirm-password').val()
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

    const password = data.password;
    if (password.length == 0) {
        alert("password is required.");
        return;
    }

    const confirmPassword = data.confirmPassword;
    if (confirmPassword.length == 0) {
        alert("confirm password is required.");
        return;
    }

    if (password !== confirmPassword) {
        alert("password and confirm-password are different.");
        return;
    }

    $.ajax({
        type: 'POST',
        url: "/login/email/sign-up",
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        alert('Your registration has been completed. Please login again.');
        location.href = "/login/email";
    }).fail(function (error) {
        let responseJson = error.responseJSON;
        alert("Failed to sign up. Please contact the administrator.\n(" + responseJson.message + ")");
    });
}

function sendAuthCode() {
    let data = {
        "email": $('#sign-up-email').val()
    };
    alert('The authentication code has been sent.');

    $.ajax({
        type: 'POST',
        url: "/login/email/auth-code",
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        if (response.success) {
            return;
        }
        alert('The authentication code sending failed. Please try again.');
    }).fail(function (error) {
        let responseJson = error.responseJSON;
        alert("Failed to send authentication code. Please contact the administrator.\n(" + responseJson.message + ")");
    });
}

function verifyAuthCode() {
    let data = {
        "email": $('#sign-up-email').val(),
        "authCode": $('#email-auth-code').val()
    };

    $.ajax({
        type: 'POST',
        url: "/login/email/auth-code/verify",
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        if (response.success) {
            alert('Your email has been verified.');
            return;
        }
        alert('Your email verification has failed. Please try again.');
    }).fail(function (error) {
        let responseJson = error.responseJSON;
        alert("Failed to verify authentication code. Please contact the administrator.\n(" + responseJson.message + ")");
    });
}
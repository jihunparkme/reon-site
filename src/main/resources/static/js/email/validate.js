function sendMessage() {
  document.querySelector('.loading').classList.add('d-block');
  document.querySelector('.error-message').classList.remove('d-block');
  document.querySelector('.sent-message').classList.remove('d-block');
  document.getElementById('send-message').style.display = 'none';
}

function successSendMessage() {
  document.querySelector('.loading').classList.remove('d-block');
  document.querySelector('.error-message').classList.remove('d-block');
  document.querySelector('.sent-message').classList.add('d-block');
}

function failSendMessage(errorMessage) {
  document.querySelector('.loading').classList.remove('d-block');
  document.querySelector('.error-message').innerHTML = errorMessage;
  document.querySelector('.error-message').classList.add('d-block');
  document.querySelector('.sent-message').classList.remove('d-block');
  document.getElementById('send-message').style.display = 'block';
}

function send() {
  sendMessage();

  let data = {
    "name": $('#name').val(),
    "email": $('#email').val(),
    "subject": $('#subject').val(),
    "message": $('#message').val(),
  };

  $.ajax({
    type: 'POST',
    url: "/contact/email",
    dataType: 'json',
    data: JSON.stringify(data),
    contentType: 'application/json; charset=utf-8'
  }).done(function (response) {
    if (response.success) {
      successSendMessage();
      return;
    }
    failSendMessage();
  }).fail(function (error) {
    let responseJson = error.responseJSON;
    failSendMessage(responseJson.message);
  });
}



$('#password, #repeatPassword').on('keyup', function () {
  if ($('#password').val().length < 6) {
    $('#message').html('Password length must be >= 6').addClass('alert alert-danger text-center');
    return;
  }

  var regex = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
  if (regex.test($('#password').val())) {
    $('#message').html('Password must not containt special character').addClass('alert alert-danger text-center');
    return;
  }
  var regex = /\d/;
  if (!regex.test($('#password').val())) {
    $('#message').html('Password must containt at least 1 digit character').addClass('alert alert-danger text-center');
    return;
  }
  var hasUppercase = /[A-Z]/.test($('#password').val());
  if (!hasUppercase) {
    $('#message').html('Password must containt at least 1 capital letters').addClass('alert alert-danger text-center');
    return;
  }
  if ($('#password').val() == $('#repeatPassword').val()) {
    $('#message').html('Re-password is matching').addClass('alert alert-success text-center');
    $('#message').html('Re-password is matching').removeClass('alert-danger');

  } else
    $('#message').html('Re-password is not Matching').addClass('alert alert-danger text-center');
});





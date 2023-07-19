



function checkEmail(email) {
  var emailUser = email.value;
  $.ajax({
    url: "/check/email",
    type: "GET",
    data: {
      userEmail: emailUser
    },
    success: function (data) {
      var mess = document.getElementById("emailMess");
      if (data !== null && data !== "") {
        mess.innerHTML = data;
      }
    },

  });
}


function report(data) {
  if (data != null && data !== "") {
    $('#report').html(data);
    $('.alert').addClass("show");
    $('.alert').removeClass("hide");
    $('.alert').addClass("showAlert");
    setTimeout(function () {
      $('.alert').removeClass("show");
      $('.alert').addClass("hide");
    }, 5000);

    $('.close-btn').click(function () {
      $('.alert').removeClass("show");
      $('.alert').addClass("hide");
    });
  }
}


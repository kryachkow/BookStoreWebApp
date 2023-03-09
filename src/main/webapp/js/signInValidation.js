const emailPattern = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;




$(document).ready(function () {
  $('#form').submit(function (e) {
    let incorrectForm = false;
    let email = $('#email');
    let errorSpan = $('.error-span');
    errorSpan.remove();

    if(!emailPattern.test(email.val().trim())){
      email.after('<span class="error-span">Incorrect email!</span>');
      email.val('');
      incorrectForm = true;
    }

    if(incorrectForm){
      e.preventDefault();
      $('.error-span').css('color', 'red');
    }

  })
})
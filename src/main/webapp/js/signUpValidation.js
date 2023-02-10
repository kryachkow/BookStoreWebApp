const nickNamePattern = /^\D[^@#!]{3,23}/;
const emailPattern = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
const namePattern = /^[A-Z][a-z]{3,23}$/;



$(document).ready(function () {
  $('#form').submit(function (e) {
    let incorrectForm = false;
    let email = $('#email');
    let name = $('#name');
    let surname = $('#surname');
    let nickname = $('#nickname');
    let password = $('#password');
    let repeatPassword = $('#repeatPassword');
    let errorSpan = $('.error-span');

    errorSpan.remove();

    if(!emailPattern.test(email.val().trim())){
      email.after('<span class="error-span">Incorrect email!</span>');
      email.val('');
      incorrectForm = true;
    }
    if(!namePattern.test(name.val().trim())){
      name.after('<span class="error-span">Incorrect Name!</span>');
      name.val('');
      incorrectForm = true;
    }
    if(!namePattern.test(name.val().trim())){
      surname.after('<span class="error-span">Incorrect Surname!</span>');
      surname.val('');
      incorrectForm = true;
    }

    if(!nickNamePattern.test(nickname.val().trim())){
      nickname.after('<span class="error-span">Incorrect nickname!</span>');
      nickname.val('');
      incorrectForm = true;
    }

    if(password.val() !== repeatPassword.val()){
      $('#table').after('<span class="error-span">Passwords don`t match!</span>');
      password.val('');
      repeatPassword.val('');
      incorrectForm = true;
    }

    if(incorrectForm){
      e.preventDefault();
      $('.error-span').css('color', 'red');
    }

  })
})
package com.task.bookstorewebbapp.service.validation;

import static org.mockito.Mockito.mock;

import com.task.bookstorewebbapp.entity.UserEntity;
import com.task.bookstorewebbapp.model.RegistrationForm;
import com.task.bookstorewebbapp.service.captcha.CaptchaServiceImpl;
import com.task.bookstorewebbapp.service.user.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

class ValidationServiceImplTest {



  private final HttpServletRequest request = mock(HttpServletRequest.class);
  private final UserServiceImpl userService = mock(UserServiceImpl.class);
  private final CaptchaServiceImpl captchaService = mock(CaptchaServiceImpl.class);
  private final ValidationServiceImpl validationService = new ValidationServiceImpl();

  {
    Whitebox.setInternalState(validationService, "captchaService", captchaService);
    Whitebox.setInternalState(validationService, "userService", userService);
  }

  @BeforeEach
  void setUp() {
    Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(null);
    Mockito.when(userService.getUserByEmail("example@email.com")).thenReturn(new UserEntity());
    Mockito.when(userService.getUserByNickname(Mockito.anyString())).thenReturn(null);
    Mockito.when(userService.getUserByNickname("Nick1")).thenReturn(new UserEntity());
    Mockito.when(captchaService.validateCaptcha(request)).thenReturn("");
  }

  @ParameterizedTest
  @MethodSource("getValidationParams")
  void validateRegForm(RegistrationForm registrationForm, String error,
      RegistrationForm registrationFormAfter) {
    Assertions.assertEquals(error, validationService.validate(request, registrationForm));
    Assertions.assertEquals(registrationFormAfter, registrationForm);
  }

  @ParameterizedTest
  @MethodSource("getCredentialCheckParams")
  void validateCredentialExist(RegistrationForm registrationForm, String error,
      RegistrationForm registrationFormAfter) {
    Assertions.assertEquals(error, validationService.validate(request, registrationForm));
    Assertions.assertEquals(registrationFormAfter, registrationForm);
  }

  static Stream<Arguments> getValidationParams() {
    return Stream.of(
        Arguments.of(
            new RegistrationForm("milo", "incorrerct name", "incorrectSurname", "1ncorrect nick",
                "incorrectPass", "incorrectRepeatPass", false),
            "Email isn`t valid Name isn`t valid Surname isn`t valid Nickname isn`t valid Password isn`t valid",
            new RegistrationForm("", "", "",
                "", "", "", false)),
        Arguments.of(
            new RegistrationForm("example12@email.com", "incorrerct name", "incorrectSurname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Name isn`t valid Surname isn`t valid Nickname isn`t valid Password isn`t valid",
            new RegistrationForm("example12@email.com", "", "",
                "", "", "", false)),
        Arguments.of(
            new RegistrationForm("example12@email.com", "incorrerct name", "Surname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Name isn`t valid Nickname isn`t valid Password isn`t valid",
            new RegistrationForm("example12@email.com", "", "Surname",
                "", "", "", false)),
        Arguments.of(
            new RegistrationForm("example12@email.com", "Name", "Surname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Nickname isn`t valid Password isn`t valid",
            new RegistrationForm("example12@email.com", "Name", "Surname",
                "", "", "", false)),
        Arguments.of(
            new RegistrationForm("example12@email.com", "Name", "Surname",
                "Nick", "12345678", "incorrectRepeatPass", false),
            "Password isn`t valid",
            new RegistrationForm("example12@email.com", "Name", "Surname",
                "Nick", "", "", false)),
        Arguments.of(
            new RegistrationForm("example12@email.com", "Name", "Surname",
                "Nick", "12345678", "12345678", false),
            "",
            new RegistrationForm("example12@email.com", "Name", "Surname",
                "Nick", "12345678", "12345678", false))
    );
  }

  static Stream<Arguments> getCredentialCheckParams() {
    return Stream.of(
        Arguments.of(
            new RegistrationForm("example@email.com", "Name", "Surname", "Nick1",
                "12345678", "12345678", false),
            "Such email exists Such nickname exists",
            new RegistrationForm("", "Name", "Surname", "",
                "", "", false)),
        Arguments.of(
            new RegistrationForm("example12@email.com", "Name", "Surname", "Nick1",
                "12345678", "12345678", false),
            "Such nickname exists",
            new RegistrationForm("example12@email.com", "Name", "Surname", "",
                "", "", false)),
        Arguments.of(
            new RegistrationForm("example@email.com", "Name", "Surname", "Nick",
                "12345678", "12345678", false),
            "Such email exists",
            new RegistrationForm("", "Name", "Surname", "Nick",
                "", "", false)),
        Arguments.of(
            new RegistrationForm("example123@email.com", "Name", "Surname", "Nick123",
                "12345678", "12345678", false),
            "",
            new RegistrationForm("example123@email.com", "Name", "Surname", "Nick123",
                "12345678", "12345678", false))
    );
  }

}
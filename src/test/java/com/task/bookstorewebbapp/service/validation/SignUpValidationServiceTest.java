package com.task.bookstorewebbapp.service.validation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.db.exception.DAOException;
import com.task.bookstorewebbapp.model.ValidationForm;
import com.task.bookstorewebbapp.service.captcha.CaptchaServiceImpl;
import com.task.bookstorewebbapp.service.user.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

class SignUpValidationServiceTest {

  private final HttpServletRequest request = mock(HttpServletRequest.class);
  private final UserServiceImpl userService = mock(UserServiceImpl.class);
  private final CaptchaServiceImpl captchaService = mock(CaptchaServiceImpl.class);
  private static final MockedStatic<DBUtils> dbUtilsMockedStatic = Mockito.mockStatic(DBUtils.class);
  private static final DBUtils dbutils = mock(DBUtils.class);
  static {
    dbUtilsMockedStatic.when(DBUtils::getInstance).thenReturn(dbutils);
  }
  private final SignUpValidationService validationService = new SignUpValidationService();



  {
    Whitebox.setInternalState(validationService, "captchaService", captchaService);
    Whitebox.setInternalState(validationService, "userService", userService);
  }

  @BeforeEach
  void setUp() throws DAOException {
    Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(null);
    Mockito.when(userService.getUserByEmail("example@email.com")).thenReturn(new UserEntity());
    Mockito.when(userService.getUserByNickname(Mockito.anyString())).thenReturn(null);
    Mockito.when(userService.getUserByNickname("Nick1")).thenReturn(new UserEntity());
    Mockito.when(captchaService.validateCaptcha(request)).thenReturn("");
  }




  @ParameterizedTest
  @MethodSource("getValidationParams")
  void validateRegForm(ValidationForm validationForm, String error,
      ValidationForm validationFormAfter) {
    Assertions.assertEquals(error, validationService.validate(request, validationForm));
    Assertions.assertEquals(validationFormAfter, validationForm);
  }

  @ParameterizedTest
  @MethodSource("getCredentialCheckParams")
  void validateCredentialExist(ValidationForm validationForm, String error,
      ValidationForm validationFormAfter) {
    Assertions.assertEquals(error, validationService.validate(request, validationForm));
    Assertions.assertEquals(validationFormAfter, validationForm);
  }

  static Stream<Arguments> getValidationParams() {
    return Stream.of(
        Arguments.of(
            new ValidationForm("milo", "incorrerct name", "incorrectSurname", "1ncorrect nick",
                "incorrectPass", "incorrectRepeatPass", false),
            "Email isn`t valid Name isn`t valid Surname isn`t valid Nickname isn`t valid Password isn`t valid",
            new ValidationForm("", "", "",
                "", "", "", false)),
        Arguments.of(
            new ValidationForm("example12@email.com", "incorrerct name", "incorrectSurname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Name isn`t valid Surname isn`t valid Nickname isn`t valid Password isn`t valid",
            new ValidationForm("example12@email.com", "", "",
                "", "", "", false)),
        Arguments.of(
            new ValidationForm("example12@email.com", "incorrerct name", "Surname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Name isn`t valid Nickname isn`t valid Password isn`t valid",
            new ValidationForm("example12@email.com", "", "Surname",
                "", "", "", false)),
        Arguments.of(
            new ValidationForm("example12@email.com", "Name", "Surname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Nickname isn`t valid Password isn`t valid",
            new ValidationForm("example12@email.com", "Name", "Surname",
                "", "", "", false)),
        Arguments.of(
            new ValidationForm("example12@email.com", "Name", "Surname",
                "Nick", "12345678", "incorrectRepeatPass", false),
            "Password isn`t valid",
            new ValidationForm("example12@email.com", "Name", "Surname",
                "Nick", "", "", false)),
        Arguments.of(
            new ValidationForm("example12@email.com", "Name", "Surname",
                "Nick", "12345678", "12345678", false),
            "",
            new ValidationForm("example12@email.com", "Name", "Surname",
                "Nick", "12345678", "12345678", false))
    );
  }

  static Stream<Arguments> getCredentialCheckParams() {
    return Stream.of(
        Arguments.of(
            new ValidationForm("example@email.com", "Name", "Surname", "Nick1",
                "12345678", "12345678", false),
            "Such email exists Such nickname exists",
            new ValidationForm("", "Name", "Surname", "",
                "", "", false)),
        Arguments.of(
            new ValidationForm("example12@email.com", "Name", "Surname", "Nick1",
                "12345678", "12345678", false),
            "Such nickname exists",
            new ValidationForm("example12@email.com", "Name", "Surname", "",
                "", "", false)),
        Arguments.of(
            new ValidationForm("example@email.com", "Name", "Surname", "Nick",
                "12345678", "12345678", false),
            "Such email exists",
            new ValidationForm("", "Name", "Surname", "Nick",
                "", "", false)),
        Arguments.of(
            new ValidationForm("example123@email.com", "Name", "Surname", "Nick123",
                "12345678", "12345678", false),
            "",
            new ValidationForm("example123@email.com", "Name", "Surname", "Nick123",
                "12345678", "12345678", false))
    );
  }

}
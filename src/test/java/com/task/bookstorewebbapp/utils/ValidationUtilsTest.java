package com.task.bookstorewebbapp.utils;

import static org.mockito.Mockito.mockStatic;

import com.task.bookstorewebbapp.entity.UserEntity;
import com.task.bookstorewebbapp.model.RegistrationForm;
import com.task.bookstorewebbapp.service.UserService;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class ValidationUtilsTest {
  static MockedStatic<UserService> userServiceMockedStatic = mockStatic(UserService.class, Mockito.CALLS_REAL_METHODS);
  static {
    userServiceMockedStatic.when(() -> UserService.getUserByEmail(Mockito.anyString())).then(InvocationOnMock -> null);
    userServiceMockedStatic.when(() -> UserService.getUserByEmail("example@email.com")).then(InvocationOnMock -> new UserEntity());
    userServiceMockedStatic.when(() -> UserService.getUserByNickname(Mockito.anyString())).then(InvocationOnMock -> null);
    userServiceMockedStatic.when(() -> UserService.getUserByNickname("Nick1")).then(InvocationOnMock -> new UserEntity());
  }

  @ParameterizedTest
  @MethodSource("getValidationParams")
  void validateRegForm(RegistrationForm registrationForm, String error, RegistrationForm registrationFormAfter) {
    Assertions.assertEquals(error, ValidationUtils.validateRegForm(registrationForm));
    Assertions.assertEquals(registrationFormAfter, registrationForm);
  }

  @ParameterizedTest
  @MethodSource("getCredentialCheckParams")
  void validateCredentialExist(RegistrationForm registrationForm, String error, RegistrationForm registrationFormAfter) {
    Assertions.assertEquals(error, ValidationUtils.validateCredentialExist(registrationForm));
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
            new RegistrationForm("example@email.com", "incorrerct name", "incorrectSurname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Name isn`t valid Surname isn`t valid Nickname isn`t valid Password isn`t valid",
            new RegistrationForm("example@email.com", "", "",
                "", "", "", false)),
        Arguments.of(
            new RegistrationForm("example@email.com", "incorrerct name", "Surname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Name isn`t valid Nickname isn`t valid Password isn`t valid",
            new RegistrationForm("example@email.com", "", "Surname",
                "", "", "", false)),
        Arguments.of(
            new RegistrationForm("example@email.com", "Name", "Surname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Nickname isn`t valid Password isn`t valid",
            new RegistrationForm("example@email.com", "Name", "Surname",
                "", "", "", false)),
        Arguments.of(
            new RegistrationForm("example@email.com", "Name", "Surname",
                "Nick", "12345678", "incorrectRepeatPass", false),
            "Password isn`t valid",
            new RegistrationForm("example@email.com", "Name", "Surname",
                "Nick", "", "", false)),
        Arguments.of(
            new RegistrationForm("example@email.com", "Name", "Surname",
                "Nick", "12345678", "12345678", false),
            "",
            new RegistrationForm("example@email.com", "Name", "Surname",
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
package com.task.bookstorewebbapp.service.validation;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.task.bookstorewebbapp.db.exception.DataSourceException;
import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.UserFormDTO;
import com.task.bookstorewebbapp.model.ValidationDTO;
import com.task.bookstorewebbapp.service.captcha.impl.CaptchaServiceImpl;
import com.task.bookstorewebbapp.service.user.impl.UserServiceImpl;
import com.task.bookstorewebbapp.service.validation.impl.SignUpValidationService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;
import utils.DBUtilsStaticMock;

@ExtendWith(MockitoExtension.class)
class SignUpValidationServiceTest {

  @Mock
  private static HttpServletRequest request;
  @Mock
  private static UserServiceImpl userService;
  @Mock
  private static CaptchaServiceImpl captchaService;
  private final SignUpValidationService validationService = new SignUpValidationService();

  @BeforeAll
  static void init() {
    DBUtilsStaticMock.makeDBMock();
  }

  @BeforeEach
  void setUp() throws DataSourceException {
    Whitebox.setInternalState(validationService, "captchaService", captchaService);
    Whitebox.setInternalState(validationService, "userService", userService);
    when(userService.getUserByEmail(Mockito.anyString())).thenReturn(Optional.empty());
    lenient().when(userService.getUserByEmail("example@email.com")).thenReturn(Optional.of(new User()));
    when(userService.getUserByNickname(Mockito.anyString())).thenReturn(Optional.empty());
    lenient().when(userService.getUserByNickname("Nick1")).thenReturn(Optional.of(new User()));
    lenient().when(captchaService.validateCaptcha(request)).thenReturn("");
  }


  @ParameterizedTest
  @MethodSource("getValidationParams")
  void validateRegForm(UserFormDTO userFormDTO, String error,
      UserFormDTO userFormDTOAfter, boolean res) {
    ValidationDTO<User> objectValidationDTO = new ValidationDTO<>(request, userFormDTO);
    Assertions.assertEquals(res, validationService.checkErrors(objectValidationDTO));
    Assertions.assertEquals(error, objectValidationDTO.getErrorMessage().toString().trim());
    Assertions.assertEquals(userFormDTOAfter, userFormDTO);

  }

  @ParameterizedTest
  @MethodSource("getCredentialCheckParams")
  void validateCredentialExist(UserFormDTO userFormDTO, String error,
      UserFormDTO userFormDTOAfter, boolean res) {
    ValidationDTO<User> objectValidationDTO = new ValidationDTO<>(request, userFormDTO);
    Assertions.assertEquals(res, validationService.checkErrors(objectValidationDTO));
    Assertions.assertEquals(error, objectValidationDTO.getErrorMessage().toString().trim());
    Assertions.assertEquals(userFormDTOAfter, userFormDTO);
  }

  static Stream<Arguments> getValidationParams() {
    return Stream.of(
        Arguments.of(
            new UserFormDTO("milo", "incorrerct name", "incorrectSurname", "1ncorrect nick",
                "incorrectPass", "incorrectRepeatPass", false),
            "Email isn`t valid Name isn`t valid Surname isn`t valid Nickname isn`t valid Password isn`t valid",
            new UserFormDTO("", "", "",
                "", "", "", false), true),
        Arguments.of(
            new UserFormDTO("example12@email.com", "incorrerct name", "incorrectSurname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Name isn`t valid Surname isn`t valid Nickname isn`t valid Password isn`t valid",
            new UserFormDTO("example12@email.com", "", "",
                "", "", "", false), true),
        Arguments.of(
            new UserFormDTO("example12@email.com", "incorrerct name", "Surname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Name isn`t valid Nickname isn`t valid Password isn`t valid",
            new UserFormDTO("example12@email.com", "", "Surname",
                "", "", "", false), true),
        Arguments.of(
            new UserFormDTO("example12@email.com", "Name", "Surname",
                "1ncorrect nick", "incorrectPass", "incorrectRepeatPass", false),
            "Nickname isn`t valid Password isn`t valid",
            new UserFormDTO("example12@email.com", "Name", "Surname",
                "", "", "", false), true),
        Arguments.of(
            new UserFormDTO("example12@email.com", "Name", "Surname",
                "Nick", "12345678", "incorrectRepeatPass", false),
            "Password isn`t valid",
            new UserFormDTO("example12@email.com", "Name", "Surname",
                "Nick", "", "", false), true),
        Arguments.of(
            new UserFormDTO("example12@email.com", "Name", "Surname",
                "Nick", "12345678", "12345678", false),
            "",
            new UserFormDTO("example12@email.com", "Name", "Surname",
                "Nick", "12345678", "12345678", false), false)
    );
  }

  static Stream<Arguments> getCredentialCheckParams() {
    return Stream.of(
        Arguments.of(
            new UserFormDTO("example@email.com", "Name", "Surname", "Nick1",
                "12345678", "12345678", false),
            "Such email exists Such nickname exists",
            new UserFormDTO("", "Name", "Surname", "",
                "", "", false), true),
        Arguments.of(
            new UserFormDTO("example12@email.com", "Name", "Surname", "Nick1",
                "12345678", "12345678", false),
            "Such nickname exists",
            new UserFormDTO("example12@email.com", "Name", "Surname", "",
                "", "", false), true),
        Arguments.of(
            new UserFormDTO("example@email.com", "Name", "Surname", "Nick",
                "12345678", "12345678", false),
            "Such email exists",
            new UserFormDTO("", "Name", "Surname", "Nick",
                "", "", false), true),
        Arguments.of(
            new UserFormDTO("example123@email.com", "Name", "Surname", "Nick123",
                "12345678", "12345678", false),
            "",
            new UserFormDTO("example123@email.com", "Name", "Surname", "Nick123",
                "12345678", "12345678", false), false)
    );
  }

}
package com.task.bookstorewebbapp.service.validation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.db.exception.DAOException;
import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.ValidationForm;
import com.task.bookstorewebbapp.repository.avatar.AvatarRepository;
import com.task.bookstorewebbapp.repository.avatar.AvatarRepositoryImpl;
import com.task.bookstorewebbapp.service.captcha.CaptchaServiceImpl;
import com.task.bookstorewebbapp.service.user.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

class AuthenticationServiceTest {

  private final HttpServletRequest request = mock(HttpServletRequest.class);
  private final HttpSession session = mock(HttpSession.class);
  private final UserServiceImpl userService = mock(UserServiceImpl.class);
  private final AvatarRepositoryImpl avatarRepository = mock(AvatarRepositoryImpl.class);
  private static final MockedStatic<DBUtils> dbUtilsMockedStatic = Mockito.mockStatic(DBUtils.class);
  private static final DBUtils dbutils = mock(DBUtils.class);
  static {
    dbUtilsMockedStatic.when(DBUtils::getInstance).thenReturn(dbutils);
  }
  private final AuthenticationService validationService = new AuthenticationService();

  {
    Whitebox.setInternalState(validationService, "userService", userService);
    Whitebox.setInternalState(validationService, "avatarRepository", avatarRepository);
  }
  private final UserEntity testEntity = new UserEntity(1, "example@email.com","Name", "Name", "Name",
      "12345678", true);

  @BeforeEach
  void setUp() throws DAOException {
    Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(null);
    Mockito.when(userService.getUserByEmail("example@email.com")).thenReturn(testEntity);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(avatarRepository.getAvatar(Mockito.anyLong())).thenReturn(null);
  }

  @ParameterizedTest
  @MethodSource("getCredentialCheckParams")
  void validate(ValidationForm formToValidate, String message, ValidationForm formAfterValidation, int servicesInvocations) {
    Assertions.assertEquals(message, validationService.validate(request, formToValidate));
    Assertions.assertEquals(formAfterValidation, formToValidate);
    verify(request, times(servicesInvocations)).getSession();
    verify(avatarRepository, times(servicesInvocations)).getAvatar(anyLong());
    verify(session, times(servicesInvocations)).setAttribute("user", User.toModel(testEntity));
  }


  static Stream<Arguments> getCredentialCheckParams() {
    return Stream.of(
        Arguments.of(
            new ValidationForm("12412exa3123.com", "", "", "",
                "", "", false),
            "Email isn`t valid Password isn`t valid",
            new ValidationForm("", "", "", "",
                "", "", false), 0),
        Arguments.of(
            new ValidationForm("example12@email.com", "", "", "",
                "", "", false),
            "Password isn`t valid",
            new ValidationForm("example12@email.com", "", "", "",
                "", "", false), 0),
        Arguments.of(
            new ValidationForm("example@email.com", "", "", "",
                "31231231231", "", false),
            "Wrong credentials",
            new ValidationForm("example@email.com", "", "", "",
                "", "", false), 0),
        Arguments.of(
            new ValidationForm("exam1ple@email.com", "", "", "",
                "31231231231", "", false),
            "Wrong credentials",
            new ValidationForm("exam1ple@email.com", "", "", "",
                "", "", false), 0),
        Arguments.of(
            new ValidationForm("example@email.com", "", "", "",
                "12345678", "", false),
            "",
            new ValidationForm("example@email.com", "", "", "",
                "12345678", "", false), 1)
    );
  }
}
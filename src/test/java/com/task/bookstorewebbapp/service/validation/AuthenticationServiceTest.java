package com.task.bookstorewebbapp.service.validation;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.db.exception.DataSourceException;
import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.UserFormDTO;
import com.task.bookstorewebbapp.model.ValidationDTO;
import com.task.bookstorewebbapp.repository.avatar.impl.AvatarRepositoryImpl;
import com.task.bookstorewebbapp.service.user.impl.UserServiceImpl;
import com.task.bookstorewebbapp.service.validation.impl.AuthenticationService;
import com.task.bookstorewebbapp.utils.PasswordUtils;
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
class AuthenticationServiceTest {

//  private final static MockedStatic<DBUtils> dbUtilsMockedStatic = mockStatic(DBUtils.class);
  @Mock
  private HttpServletRequest request;
  @Mock
  private static UserServiceImpl userService;
  @Mock
  private static AvatarRepositoryImpl avatarRepository;
  private final AuthenticationService validationService = new AuthenticationService();
  private final UserEntity testEntity = new UserEntity(1, "example@email.com", "Name", "Name",
      "Name", PasswordUtils.encodePassword("12345678"), true);


  @BeforeAll
  static void init() {
    DBUtilsStaticMock.makeDBMock();
  }

  @BeforeEach
  void setUp() throws DataSourceException {
    when(userService.authenticateUser(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
    lenient().when(userService.authenticateUser("example@email.com", "12345678")).thenReturn(
        Optional.of(User.toModel(testEntity)));
    lenient().when(avatarRepository.getAvatar(Mockito.anyLong())).thenReturn(null);
    Whitebox.setInternalState(validationService, "userService", userService);
    Whitebox.setInternalState(validationService, "avatarRepository", avatarRepository);
  }

  @ParameterizedTest
  @MethodSource("getCredentialCheckParams")
  void validate(UserFormDTO formToValidate, String message, UserFormDTO formAfterValidation,
      int servicesInvocations, boolean validationResult) {
    ValidationDTO<User> userValidationDTO = new ValidationDTO<>(request, formToValidate);
    boolean res = validationService.checkErrors(userValidationDTO);
    Assertions.assertEquals(message, userValidationDTO.getErrorMessage().toString().trim());
    Assertions.assertEquals(validationResult, res);
    Assertions.assertEquals(formAfterValidation, formToValidate);
    verify(avatarRepository, times(servicesInvocations)).getAvatar(anyLong());
  }


  static Stream<Arguments> getCredentialCheckParams() {
    return Stream.of(
        Arguments.of(new UserFormDTO("example@email.com", "", "", "", "12345678", "", false), "",
            new UserFormDTO("example@email.com", "", "", "", "12345678", "", false), 1, false),
        Arguments.of(new UserFormDTO("example12@email.com", "", "", "", "", "", false),
            "Password isn`t valid",
            new UserFormDTO("example12@email.com", "", "", "", "", "", false), 0, true),
        Arguments.of(new UserFormDTO("example@email.com", "", "", "", "31231231231", "", false),
            "Wrong credentials", new UserFormDTO("example@email.com", "", "", "", "", "", false), 0,
            true),
        Arguments.of(new UserFormDTO("exam1ple@email.com", "", "", "", "31231231231", "", false),
            "Wrong credentials", new UserFormDTO("exam1ple@email.com", "", "", "", "", "", false),
            0, true),
        Arguments.of(new UserFormDTO("12412exa3123.com", "", "", "", "", "", false),
            "Email isn`t valid Password isn`t valid",
            new UserFormDTO("", "", "", "", "", "", false), 0, true)

    );
  }
}
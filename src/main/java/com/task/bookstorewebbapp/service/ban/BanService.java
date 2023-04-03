package com.task.bookstorewebbapp.service.ban;

import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.db.entity.BanInfoEntity;
import java.util.Optional;

public interface BanService {

  Optional<BanInfoEntity> getBanInfo(UserEntity userEntity);
  boolean isUserBanned(UserEntity userEntity);
  boolean updateLogCountOnWrongLogIn(UserEntity userEntity);
  boolean updateLogCountOnCorrectLogIn(UserEntity userEntity);
  long createBanInfo(long id);
}

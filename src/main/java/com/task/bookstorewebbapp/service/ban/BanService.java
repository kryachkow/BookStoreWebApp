package com.task.bookstorewebbapp.service.ban;

import com.task.bookstorewebbapp.db.entity.BanInfoEntity;
import java.util.Optional;

public interface BanService {

  Optional<BanInfoEntity> getBanInfo(long id);
  boolean isUserBanned(long id);
  boolean updateLogCountOnWrongLogIn(long id);
  boolean updateLogCountOnCorrectLogIn(long id);
  long createBanInfo(long id);
}

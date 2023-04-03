package com.task.bookstorewebbapp.service.ban.impl;

import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.dao.impl.BanInfoDAO;
import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.db.entity.BanInfoEntity;
import com.task.bookstorewebbapp.service.ban.BanService;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BanServiceImpl implements BanService {

  private static final int MAX_LOG_COUNT = 3;
  private static final Logger LOGGER = LogManager.getLogger(BanServiceImpl.class.getName());


  private final DAO<BanInfoEntity> banInfoDAO = new BanInfoDAO();

  @Override
  public Optional<BanInfoEntity> getBanInfo(UserEntity userEntity) {
    BanInfoEntity banInfoEntity = null;
    try {
      banInfoEntity = banInfoDAO.getEntityByField(new SearchField<>("id", userEntity.getId()));
    } catch (SQLException ignored) {
    }
    return Optional.ofNullable(banInfoEntity);
  }

  @Override
  public boolean isUserBanned(UserEntity userEntity) {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    getBanInfo(userEntity).ifPresent(
        (banInfoEntity -> atomicBoolean.set(
            banInfoEntity.getDateTime() != null && banInfoEntity.getDateTime().isAfter(LocalDateTime.now())))
    );
    return atomicBoolean.get();
  }

  @Override
  public boolean updateLogCountOnWrongLogIn(UserEntity userEntity) {
    AtomicBoolean toReturn = new AtomicBoolean(false);
    getBanInfo(userEntity).ifPresent(
        banInfoEntity -> toReturn.set(increaseLogCount(banInfoEntity))
    );
    return toReturn.get();
  }

  @Override
  public boolean updateLogCountOnCorrectLogIn(UserEntity userEntity) {
    return resetLogCount(userEntity.getId());
  }

  @Override
  public long createBanInfo(long id) {
    BanInfoEntity banInfoEntity = new BanInfoEntity(id, 0, null);
    try {
      return banInfoDAO.insertEntity(banInfoEntity);
    } catch (SQLException e) {
      LOGGER.error("Couldn't insert user ban info", e);
    }
    return -1L;
  }


  private boolean resetLogCount(long id) {
    BanInfoEntity banInfoEntity = new BanInfoEntity(id, 0, null);
    try {
      return banInfoDAO.updateEntity(banInfoEntity);
    } catch (SQLException e) {
      LOGGER.error("Exception with resetting log count occurred", e);
      return false;
    }
  }


  private boolean increaseLogCount(BanInfoEntity banInfoEntity) {
    banInfoEntity.setLogCount(banInfoEntity.getLogCount() + 1);
    if (banInfoEntity.getLogCount() > MAX_LOG_COUNT && banInfoEntity.getDateTime() == null) {
      banInfoEntity.setDateTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
    }
    try {
      return banInfoDAO.updateEntity(banInfoEntity);
    } catch (SQLException e) {
      LOGGER.error("Exception occurred", e);
      return false;
    }
  }
}

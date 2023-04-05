package com.task.bookstorewebbapp.service.ban.impl;

import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.dao.impl.BanInfoDAO;
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
  private static final String ID_FIELD = "id";
  private static final Logger LOGGER = LogManager.getLogger(BanServiceImpl.class.getName());

  private final DAO<BanInfoEntity> banInfoDAO = new BanInfoDAO();

  @Override
  public Optional<BanInfoEntity> getBanInfo(long id) {
    BanInfoEntity banInfoEntity = null;
    try {
      banInfoEntity = banInfoDAO.getEntityByField(new SearchField<>(ID_FIELD, id));
    } catch (SQLException ignored) {
    }
    return Optional.ofNullable(banInfoEntity);
  }

  @Override
  public boolean isUserBanned(long id) {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    getBanInfo(id).ifPresent(
        (banInfoEntity -> atomicBoolean.set(
            banInfoEntity.getDateTime() != null && banInfoEntity.getDateTime()
                .isAfter(LocalDateTime.now())))
    );
    return atomicBoolean.get();
  }

  @Override
  public boolean updateLogCountOnWrongLogIn(long id) {
    AtomicBoolean toReturn = new AtomicBoolean(false);
    getBanInfo(id).ifPresent(
        banInfoEntity -> toReturn.set(increaseLogCount(banInfoEntity))
    );
    return toReturn.get();
  }

  @Override
  public boolean updateLogCountOnCorrectLogIn(long id) {
    return resetLogCount(id);
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
    if (banInfoEntity.getDateTime() != null && banInfoEntity.getDateTime()
        .isBefore(LocalDateTime.now())) {
      banInfoEntity.setLogCount(0);
      banInfoEntity.setDateTime(null);
    }
    if (banInfoEntity.getLogCount() > MAX_LOG_COUNT && banInfoEntity.getDateTime() == null) {
      banInfoEntity.setDateTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
    }
    banInfoEntity.setLogCount(banInfoEntity.getLogCount() + 1);
    try {
      return banInfoDAO.updateEntity(banInfoEntity);
    } catch (SQLException e) {
      LOGGER.error("Exception occurred", e);
      return false;
    }
  }
}

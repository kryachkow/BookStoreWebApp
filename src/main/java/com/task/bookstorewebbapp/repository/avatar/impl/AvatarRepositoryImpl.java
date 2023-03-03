package com.task.bookstorewebbapp.repository.avatar.impl;

import com.task.bookstorewebbapp.repository.avatar.AvatarRepository;
import com.task.bookstorewebbapp.utils.ProjectPaths;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AvatarRepositoryImpl implements AvatarRepository {

  private static final String BASE_AVATAR_PATH = "img/baseAvatar.jpg";
  private static final String CATALOG_PATH = ProjectPaths.CATALOG_PATH;
  private static final String CATALOG_PATH_PART = "data:image/jpeg;base64,";
  private static final Logger LOGGER = LogManager.getLogger(AvatarRepositoryImpl.class.getName());

  public AvatarRepositoryImpl() {
    Path catalogPath = Paths.get(CATALOG_PATH);
    if (!Files.exists(catalogPath)) {
      try {
        Files.createDirectory(catalogPath);
      } catch (IOException e) {
        LOGGER.error("Cannot attach avatar catalog", e);
        throw new RuntimeException("Cannot attach avatar catalog", e);
      }
    }
  }

  @Override
  public void addAvatarToCatalog(Part filePart, long userId) throws IOException {
    if (filePart.getSize() != 0) {
      String submittedFileName = filePart.getSubmittedFileName();
      Path uploadPath = Paths.get(CATALOG_PATH,
          userId + submittedFileName.substring(submittedFileName.indexOf(".")));

      try (InputStream inputStream = filePart.getInputStream()) {
        Files.copy(inputStream, uploadPath);
      }
    }
  }

  @Override
  public String getAvatar(long userId) {
    Path imgPath = Objects.requireNonNull(new File(CATALOG_PATH).listFiles(
        (file) -> file.getName().contains(String.valueOf(userId))))[0].toPath();
    if (Files.exists(imgPath)) {
      try {
        return CATALOG_PATH_PART + Base64.getEncoder().encodeToString(Files.readAllBytes(imgPath));
      } catch (IOException e) {
        LOGGER.error("Avatar exists but couldn`t be retrieved", e);
        return BASE_AVATAR_PATH;
      }
    }
    return BASE_AVATAR_PATH;
  }
}

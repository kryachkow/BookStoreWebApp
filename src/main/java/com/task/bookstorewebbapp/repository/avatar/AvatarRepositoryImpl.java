package com.task.bookstorewebbapp.repository.avatar;

import com.task.bookstorewebbapp.ProjectPaths;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class AvatarRepositoryImpl implements AvatarRepository {

  private static final String BASE_AVATAR_PATH = "img/baseAvatar.jpg";
  private static final String CATALOG_PATH = ProjectPaths.CATALOG_PATH;
  private static final String CATALOG_PATH_PART = "data:image/jpeg;base64,";

  public AvatarRepositoryImpl() {
    Path catalogPath = Paths.get(CATALOG_PATH);
    if (!Files.exists(catalogPath)) {
      try {
        Files.createDirectory(catalogPath);
      } catch (IOException e) {
        throw new RuntimeException("Cannot attach avatar catalog", e);
      }
    }
  }

  @Override
  public void addAvatarToCatalog(Part filePart, long userId) throws IOException {
    Path uploadPath = Paths.get(CATALOG_PATH, userId + ".jpg");
    if (filePart.getSize() != 0) {
      try (InputStream inputStream = filePart.getInputStream()) {
        Files.copy(inputStream, uploadPath);
      }
    }
  }

  @Override
  public String getAvatar(long userId) {
    Path imgPath = Paths.get(CATALOG_PATH, userId + ".jpg");
    if (Files.exists(imgPath)) {
      try {
        return CATALOG_PATH_PART + Base64.getEncoder().encodeToString(Files.readAllBytes(imgPath));
      } catch (IOException e) {
        return BASE_AVATAR_PATH;
      }
    }
    return BASE_AVATAR_PATH;
  }
}

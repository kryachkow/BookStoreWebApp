package com.task.bookstorewebbapp.repository.avatar;

import jakarta.servlet.http.Part;
import java.io.IOException;

public interface AvatarRepository {

  void addAvatarToCatalog(Part filePart, long userId) throws IOException;

  String getAvatar(long userId);

}

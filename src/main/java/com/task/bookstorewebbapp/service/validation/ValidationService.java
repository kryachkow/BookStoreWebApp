package com.task.bookstorewebbapp.service.validation;

import com.task.bookstorewebbapp.model.ValidationForm;
import jakarta.servlet.http.HttpServletRequest;

public interface ValidationService {

  String validate(HttpServletRequest request, ValidationForm validationForm);
}

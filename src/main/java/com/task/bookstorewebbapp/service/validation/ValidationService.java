package com.task.bookstorewebbapp.service.validation;

import com.task.bookstorewebbapp.model.ValidationDTO;

public interface ValidationService <V> {

   boolean checkErrors(ValidationDTO<V> validationDTO);
}

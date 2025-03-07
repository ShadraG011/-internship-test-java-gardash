package ru.farpost.utils.exceptionsUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.farpost.dto.ExceptionDTO;

/**
 * Класс для глобального отлавливания исключений которые могут быть выброшены во время работы API.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Метод осуществляющий JSON ответ с кодом и сообщением исключения.
     * @param e Исключение выброшенное во время работы API.
     * @return JSON ответ с кодом и сообщением исключения.
     */
    @ExceptionHandler(ErrorResponse.class)
    public ResponseEntity<ExceptionDTO> handleException(ErrorResponse e) {
        ExceptionDTO errorResponse = new ExceptionDTO(e.getCode(), e.getMessage());
        return ResponseEntity.ok(errorResponse);
    }

}


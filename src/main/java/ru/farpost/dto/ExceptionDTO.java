package ru.farpost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс для передачи данных об исключении в JSON-ответе.
 * Используется для формирования упрощённого представления объекта.
 */
@Setter
@Getter
@AllArgsConstructor
public class ExceptionDTO {
    // region FIELDS
    private int code;
    private String message;
    // endregion
}

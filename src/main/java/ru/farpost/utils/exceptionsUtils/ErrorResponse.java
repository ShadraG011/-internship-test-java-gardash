package ru.farpost.utils.exceptionsUtils;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.farpost.components.ExceptionsMessage;

/**
 * Класс для создания исключений которые могут происходить во время работы API.
 */
@Getter
public class ErrorResponse extends RuntimeException {

    /**
     * Числовое представление кода HTTP-ошибки.
     */
    private final int code;

    /**
     * Конструктор для осуществления выбрасывания (throw) исключений
     * @param code Код исключения.
     * @param message Сообщение исключения.
     */
    public ErrorResponse(HttpStatus code, ExceptionsMessage message) {
        super(message.getMessage());
        this.code = code.value();
    }
}

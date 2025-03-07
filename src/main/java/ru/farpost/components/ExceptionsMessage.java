package ru.farpost.components;

import lombok.Getter;

/**
 * Перечисление для настройки сообщений исключений, которые могут выбрасываться во время работы API.
 */
@Getter
public enum ExceptionsMessage {

    OPERATION_NOT_FOUND("Операция не найдена!"),
    ACCOUNT_NOT_FOUND("Аккаунт не найден!"),
    INSUFFICIENT_FUNDS("Недостаточно средств для снятия!"),
    UNSUPPORTED_TYPE_CLASS("Неподдерживаемый тип класса!"),
    UNSUPPORTED_TYPE_OPERATION("Неподдерживаемый тип операции!");

    private final String message;

    ExceptionsMessage(String message) {
        this.message = message;
    }

}

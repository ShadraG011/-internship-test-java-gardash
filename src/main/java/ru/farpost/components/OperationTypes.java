package ru.farpost.components;

import lombok.Getter;

/**
 * Перечисление содержащее типы операций которые осуществляются на аккаунте.
 */
@Getter
public enum OperationTypes {

    DEPOSIT("Пополнение счета"),
    WITHDRAW("Снятие средств с счета");

    private final String description;

    OperationTypes(String description) {
        this.description = description;
    }

}

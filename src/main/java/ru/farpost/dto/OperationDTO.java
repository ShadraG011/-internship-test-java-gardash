package ru.farpost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.farpost.components.OperationTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Класс для передачи данных об операции в JSON-ответе.
 * Используется для формирования упрощённого представления объекта.
 */
@Getter
@Setter
@AllArgsConstructor
public class OperationDTO {
    // region FIELDS
    private Long id;
    private BigDecimal amount;
    private OperationTypes type;
    private String description;
    private LocalDateTime date;
    private Long accountId;
    private BigDecimal accountBalance;
    // endregion
}

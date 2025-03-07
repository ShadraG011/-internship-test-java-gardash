package ru.farpost.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс для передачи данных об аккаунте в JSON-ответе.
 * Используется для формирования упрощённого представления объекта.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    // region FIELDS
    private Long id;
    private String username;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private List<OperationDTO> operationsList;
    // endregion
}

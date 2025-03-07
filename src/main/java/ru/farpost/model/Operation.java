package ru.farpost.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import ru.farpost.components.OperationTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Класс для объявления сущности {@link Operation} и создания таблицы в БД.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "operations")
public class Operation {

    // region CONSTRUCTORS
    public Operation(BigDecimal amount, BigDecimal accountBalance, OperationTypes type, String description) {
        this.amount = amount;
        this.accountBalance = accountBalance;
        this.type = type;
        this.description = description;
    }
    // endregion

    // region FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "type", length = 8, nullable = false)
    private OperationTypes type;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"operationsList"})
    private Account account;

    @Column(name = "account_balance", nullable = false)
    private BigDecimal accountBalance;
    // endregion

}

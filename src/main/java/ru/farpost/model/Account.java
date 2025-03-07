package ru.farpost.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс для объявления сущности {@link Account} и создания таблицы в БД.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "accounts")
public class Account {

    // region CONSTRUCTORS
    public Account(String username) {
        this.balance = BigDecimal.ZERO;
        this.username = username;
        this.createdAt = LocalDateTime.now();
    }
    // endregion

    // region FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 100, nullable = false)
    private String username;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "account")
    private List<Operation> operationsList;
    // endregion

}


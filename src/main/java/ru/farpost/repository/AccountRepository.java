package ru.farpost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.farpost.model.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Класс (репозиторий) предоставляющий методы взаимодействия с БД для сервиса {@link ru.farpost.service.AccountService}.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Метод для получения баланса за указанную дату.
     * @param accountId Идентификатор аккаунта.
     * @param date Переданная дата.
     * @return Баланс аккаунта за переданную дату.
     */
    @Query("SELECT t.accountBalance FROM Operation t " +
            "WHERE t.account.id = :accountId " +
            "AND t.date <= :transactionDate " +
            "ORDER BY t.date " +
            "DESC LIMIT 1")
    BigDecimal findBalanceAtDate(@Param("accountId") Long accountId,
                                 @Param("transactionDate") LocalDateTime date);

}

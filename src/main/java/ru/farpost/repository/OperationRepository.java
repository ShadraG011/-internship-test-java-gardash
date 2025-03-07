package ru.farpost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.farpost.model.Operation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс (репозиторий) предоставляющий методы взаимодействия с БД для сервиса {@link ru.farpost.service.OperationService}.
 */
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    /**
     * Метод для получения операций аккаунта за указанный период.
     * @param accountId Идентификатор аккаунта.
     * @param from Дата начала периода для поиска операций (С).
     * @param to Дата конца периода для поиска операций (По).
     * @return Список операций аккаунта.
     */
    List<Operation> findByAccountIdAndDateBetween(Long accountId,
                                                  LocalDateTime from,
                                                  LocalDateTime to);

}
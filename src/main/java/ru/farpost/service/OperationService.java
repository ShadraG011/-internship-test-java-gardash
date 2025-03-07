package ru.farpost.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.farpost.components.OperationTypes;
import ru.farpost.dto.OperationDTO;
import ru.farpost.model.Account;
import ru.farpost.model.Operation;
import ru.farpost.repository.AccountRepository;
import ru.farpost.repository.OperationRepository;
import ru.farpost.utils.exceptionsUtils.ErrorResponse;
import ru.farpost.components.ExceptionsMessage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс (сервис) для осуществления работы с БД для сущности: {@link Operation}
 */
@Service
public class OperationService {

    /**
     * Объект класса {@link OperationRepository}
     */
    private final OperationRepository operationRepository;

    /**
     * Объект класса {@link AccountService}
     */
    private final AccountService accountService;

    /**
     * Конструктор для создания объектов {@link AccountService} и {@link OperationRepository} с использованием "@Autowired".
     * @param accountService Ссылка на объект {@link AccountService}.
     * @param operationRepository Ссылка на объект {@link OperationRepository}.
     */
    @Autowired
    public OperationService(AccountService accountService, OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
        this.accountService = accountService;
    }

    /**
     * Метод для преобразования объекта {@link Operation} в {@link OperationDTO} для осуществления JSON ответов.
     * @param operation ссылка на объект {@link Operation}.
     * @return Объект {@link OperationDTO}.
     */
    public OperationDTO convertToDTO(Operation operation) {
        return new OperationDTO(
                operation.getId(),
                operation.getAmount(),
                operation.getType(),
                operation.getDescription(),
                operation.getDate(),
                operation.getAccount().getId(),
                operation.getAccountBalance()
        );
    }

    /**
     * Метод для осуществления операции (пополнение, списание) на аккаунте.
     * @param accountId Идентификатор аккаунта.
     * @param amount Сумма операции.
     * @param type Тип операции (DEPOSIT, WITHDRAW).
     * @return Объект {@link OperationDTO} который был конвертирован из объекта {@link Operation}.
     */
    public OperationDTO processOperation(Long accountId, BigDecimal amount, OperationTypes type) {

        Account account = accountService.findAccountById(accountId, Account.class);

        switch (type){
            case OperationTypes.DEPOSIT -> account.setBalance(account.getBalance().add(amount));
            case OperationTypes.WITHDRAW -> {
                if (account.getBalance().compareTo(amount) < 0)
                    throw new ErrorResponse(HttpStatus.BAD_REQUEST, ExceptionsMessage.INSUFFICIENT_FUNDS);
                else
                    account.setBalance(account.getBalance().subtract(amount));
            }
            default -> throw new ErrorResponse(HttpStatus.BAD_REQUEST, ExceptionsMessage.UNSUPPORTED_TYPE_OPERATION);
        }

        String operationDescription = String.format("%s на сумму: %s", type.getDescription(), amount);
        Operation operation = new Operation(amount, account.getBalance(), type, operationDescription);

        operation.setAccount(account);
        operationRepository.save(operation);

        account.getOperationsList().add(operation);
        accountService.updateAccountBalance(account);

        return convertToDTO(operation);

    }

    /**
     * Метод для получения операций (пополнений, списаний) аккаунта за определенный период.
     * @param accountId Идентификатор аккаунта.
     * @param from Дата начала периода для поиска операций (С).
     * @param to Дата конца периода для поиска операций (По).
     * @return Список операций аккаунта.
     */
    public List<OperationDTO> getOperationsByAccountIdAndDateBetween(Long accountId, LocalDateTime from, LocalDateTime to) {
        Account account = accountService.findAccountById(accountId, Account.class);
        return operationRepository
                .findByAccountIdAndDateBetween(account.getId(), from, to)
                .stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

}

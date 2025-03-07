package ru.farpost.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.farpost.dto.AccountDTO;
import ru.farpost.dto.OperationDTO;
import ru.farpost.model.Account;
import ru.farpost.model.Operation;
import ru.farpost.repository.AccountRepository;
import ru.farpost.utils.exceptionsUtils.ErrorResponse;
import ru.farpost.components.ExceptionsMessage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Класс (сервис) для осуществления работы с БД для сущности {@link Account}
 */
@Service
public class AccountService {

    /**
     * Объект класса {@link AccountRepository}
     */
    private final AccountRepository accountRepository;

    /**
     * Конструктор для создания объекта {@link AccountRepository} с использованием "@Autowired".
     *
     * @param accountRepository Ссылка на объект {@link AccountRepository}.
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Метод для преобразования объекта {@link Account} в {@link AccountDTO} для осуществления JSON ответов.
     *
     * @param account ссылка на объект Account.
     * @return Объект {@link AccountDTO}.
     */
    public AccountDTO convertToDTO(Account account) {

        return new AccountDTO(
                account.getId(),
                account.getUsername(),
                account.getBalance(),
                account.getCreatedAt(),
                account.getOperationsList()
                        .stream()
                        .map(operation -> (
                                new OperationDTO(
                                        operation.getId(),
                                        operation.getAmount(),
                                        operation.getType(),
                                        operation.getDescription(),
                                        operation.getDate(),
                                        operation.getAccount().getId(),
                                        operation.getAccountBalance()
                                )
                        )).collect(Collectors.toList())
        );

    }

    /**
     * Метод для создания аккаунта и сохранения его в БД.
     *
     * @param username Имя пользователя.
     * @return Созданный и сохраненный объект {@link Account}.
     */
    public Account createAccount(String username) {
        return accountRepository.save(new Account(username));
    }

    /**
     * Метод для обновления информации об аккаунте (в данном случае username).
     *
     * @param id          Идентификатор аккаунта в БД.
     * @param newUsername Новое имя пользователя.
     * @return Созданный и сохраненный объект {@link Account}.
     */
    public AccountDTO updateAccount(Long id, String newUsername) {

        Account account = accountRepository.findById(id).orElseThrow(() ->
                new ErrorResponse(HttpStatus.NOT_FOUND, ExceptionsMessage.ACCOUNT_NOT_FOUND));

        account.setUsername(newUsername);
        account = accountRepository.save(account);
        return convertToDTO(account);
    }

    /**
     * Метод для обновления баланса аккаунта.
     *
     * @param account Объект {@link Account}.
     */
    public void updateAccountBalance(Account account) {
        accountRepository.save(account);
    }

    /**
     * Метод для поиска аккаунта по идентификатору (id) в БД.
     *
     * @param id Идентификатор аккаунта в БД.
     * @return Объект {@link Account}.
     */
    public <T> T findAccountById(Long id, Class<T> clazz) {

        Account account = accountRepository.findById(id).orElseThrow(() ->
                new ErrorResponse(HttpStatus.NOT_FOUND, ExceptionsMessage.ACCOUNT_NOT_FOUND));

        if (clazz == AccountDTO.class) {
            return clazz.cast(convertToDTO(account));
        } else if (clazz == Account.class) {
            return clazz.cast(account);
        } else {
            throw new ErrorResponse(HttpStatus.NOT_FOUND, ExceptionsMessage.UNSUPPORTED_TYPE_CLASS);
        }

    }

    /**
     * Метод для удаления аккаунта из БД (при удалении аккаунта удаляются связанные с ним транзакции).
     *
     * @param id Идентификатор аккаунта в БД.
     */
    public void deleteAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new ErrorResponse(HttpStatus.NOT_FOUND, ExceptionsMessage.ACCOUNT_NOT_FOUND));
        accountRepository.delete(account);
    }

    /**
     * Метод для получения баланса аккаунта по указанной дате.
     *
     * @param id   Идентификатор аккаунта в БД.
     * @param date Дата за которую нужно получить баланс аккаунта.
     * @return Баланс аккаунта за указанную дату.
     */
    public BigDecimal findBalanceAtDate(Long id, LocalDateTime date) {

        Account account = accountRepository.findById(id).orElseThrow(() ->
                new ErrorResponse(HttpStatus.NOT_FOUND, ExceptionsMessage.ACCOUNT_NOT_FOUND));

        BigDecimal balanceAtDate = accountRepository.findBalanceAtDate(account.getId(), date);
        if (balanceAtDate == null) {
            balanceAtDate = BigDecimal.ZERO;
        }
        return balanceAtDate;

    }

}

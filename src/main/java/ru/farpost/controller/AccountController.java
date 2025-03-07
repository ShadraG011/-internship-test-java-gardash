package ru.farpost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.farpost.dto.AccountDTO;
import ru.farpost.model.Account;
import ru.farpost.service.AccountService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс (контроллер), отвечающий за обработку HTTP-запросов, связанных с аккаунтами.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    /**
     * Объект класса {@link AccountService}
     */
    private final AccountService accountService;

    /**
     * Конструктор для создания объекта {@link AccountService}.
     * @param accountService Ссылка на объект {@link AccountService}.
     */
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Метод для обработки запроса на осуществления аккаунта.
     * @param newAccount Объект {@link AccountDTO} преобразованный из JSON объекта.
     * @return JSON объект с информацией об аккаунте.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Account> createAccount(@RequestBody AccountDTO newAccount) {
        return ResponseEntity.ok(accountService.createAccount(newAccount.getUsername()));
    }

    /**
     * Метод для обработки запроса на получение информации об аккаунте.
     * @param accountId Идентификатор аккаунта.
     * @return JSON объект с информацией об аккаунте.
     */
    @RequestMapping(value = "/{accountId}/info", method = RequestMethod.GET)
    public ResponseEntity<AccountDTO> getAccountInfo(@PathVariable("accountId") Long accountId) {
        return ResponseEntity.ok(accountService.findAccountById(accountId, AccountDTO.class));
    }

    /**
     * Метод для обработки запроса на изменение информации о пользователе в данном случае имя пользователя.
     * @param accountId Идентификатор аккаунта.
     * @param updatedAccount Объект {@link AccountDTO} преобразованный из JSON объекта.
     * @return JSON объект с информацией об измененном аккаунте.
     */
    @RequestMapping(value = "/{accountId}/update", method = RequestMethod.POST)
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable("accountId") Long accountId,
                                                 @RequestBody AccountDTO updatedAccount) {
        return ResponseEntity.ok(accountService.updateAccount(accountId, updatedAccount.getUsername()));
    }

    /**
     * Метод для обработки запроса на удаление аккаунта (при удалении аккаунта удаляются связанные с ним операции).
     * @param accountId Идентификатор аккаунта.
     * @return JSON ответ с сообщением об успешном удалении аккаунта.
     */
    @RequestMapping(value = "/{accountId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, String>> deleteAccount(@PathVariable("accountId") Long accountId) {
        accountService.deleteAccountById(accountId);
        var response = Map.of("deleteStatus", "Аккаунт успешно удален!");
        return ResponseEntity.ok(response);
    }

    /**
     * Метод для обработки запроса на получения текущего баланса аккаунта.
     * @param accountId Идентификатор аккаунта.
     * @return JSON объект с сообщением о текущем балансе аккаунта.
     */
    @RequestMapping(value = "/{accountId}/balance", method = RequestMethod.GET)
    public ResponseEntity<Map<String, BigDecimal>> getCurrentBalance(@PathVariable("accountId") Long accountId) {
        var response = Map.of("currentBalance", accountService.findAccountById(accountId, Account.class).getBalance());
        return ResponseEntity.ok(response);
    }

    /**
     * Метод для обработки запроса на получение баланса аккаунта на переданную дату.
     * @param accountId Идентификатор аккаунта.
     * @param date Переданная дата.
     * @return JSON объект с переданной датой и балансом аккаунта за эту дату.
     */
    @RequestMapping(value = "/{accountId}/balance-by-date", method = RequestMethod.GET)
    public Map<String, Object> getBalanceAtDate(@PathVariable("accountId") Long accountId,
                                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("date", date);
        response.put("accountBalance", accountService.findBalanceAtDate(accountId, date));
        return response;
    }

}
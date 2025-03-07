package ru.farpost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.farpost.components.OperationTypes;
import ru.farpost.dto.OperationDTO;
import ru.farpost.service.AccountService;
import ru.farpost.service.OperationService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс (контроллер), отвечающий за обработку HTTP-запросов, связанных с операциями.
 */
@Controller
@RequestMapping("/api/operations")
public class OperationController {

    /**
     * Объект класса {@link OperationService}
     */
    private final OperationService operationService;

    /**
     * Конструктор для создания объекта {@link OperationService}.
     * @param operationService Ссылка на объект {@link OperationService}.
     */
    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    /**
     * Метод для обработки запроса на осуществление операции (пополнение, списание) на аккаунте.
     * @param accountId Идентификатор аккаунта.
     * @param amount Сумма операции.
     * @param type Тип операции.
     * @return JSON объект содержащий информацию об операции.
     */
    @RequestMapping(value = "/{accountId}/make-operation", method = RequestMethod.POST)
    public ResponseEntity<OperationDTO> processOperation(@PathVariable("accountId") Long accountId,
                                                         @RequestParam("amount") BigDecimal amount,
                                                         @RequestParam("type") OperationTypes type) {
        return ResponseEntity.ok(operationService.processOperation(accountId, amount, type));
    }

    /**
     * Метод для обработки запроса на получение списка операций аккаунта за определенный период.
     * @param accountId Идентификатор аккаунта.
     * @param from Дата начала периода для поиска операций (С).
     * @param to Дата конца периода для поиска операций (По).
     * @return JSON объект содержащий список операций аккаунта.
     */
    @RequestMapping(value = "/{accountId}/get-by-period", method = RequestMethod.GET)
    public ResponseEntity<List<OperationDTO>> getOperationsByAccountIdAndDateBetween(
            @PathVariable("accountId") Long accountId,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(operationService.getOperationsByAccountIdAndDateBetween(accountId, from, to));
    }

}

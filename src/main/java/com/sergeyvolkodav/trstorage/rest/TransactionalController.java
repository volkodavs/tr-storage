package com.sergeyvolkodav.trstorage.rest;

import com.sergeyvolkodav.trstorage.rest.data.TransactionData;
import com.sergeyvolkodav.trstorage.rest.enums.AppStatusCodes;
import com.sergeyvolkodav.trstorage.rest.data.ResponseData;
import com.sergeyvolkodav.trstorage.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/v1/transactionservice")
public class TransactionalController {

    private static Logger log = LoggerFactory.getLogger(TransactionalController.class);

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.PUT, headers = "content-type=application/json")
    public ResponseData saveTransaction(@Valid @RequestBody TransactionData transactionData,
                                        @PathVariable Long id) {
        transactionData.setId(id);
        transactionService.saveTransaction(transactionData);
        return new ResponseData(AppStatusCodes.StatusOk);
    }

    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.GET)
    public TransactionData getTransaction(@PathVariable("id") Long transactionId) {
        return transactionService.getTransactionDataById(transactionId);
    }

    @RequestMapping(value = "/types/{type}", method = RequestMethod.GET)
    public List<Long> getTransactionByType(@PathVariable("type") String type) {
        return transactionService.getTransactionListByType(type);
    }

    @RequestMapping(value = "/sum/{id}", method = RequestMethod.GET)
    public ModelMap getTransactionSum(@PathVariable("id") Long transactionId) {
        ModelMap modelMap = new ModelMap();
        Double sum = transactionService.getTransactionSum(transactionId);
        modelMap.put("sum", sum);
        return modelMap;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseData handleUncheckedExceptions(Exception ex) {
        log.warn("Unchecked exception:", ex);
        return new ResponseData(AppStatusCodes.StatusError, ex);
    }
}
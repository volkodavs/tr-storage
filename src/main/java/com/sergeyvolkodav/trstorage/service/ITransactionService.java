package com.sergeyvolkodav.trstorage.service;

import com.sergeyvolkodav.trstorage.rest.data.TransactionData;

import java.util.List;

public interface ITransactionService {

    void saveTransaction(TransactionData transactionData);

    TransactionData getTransactionDataById(Long id);

    List<Long> getTransactionListByType(String type);

    Double getTransactionSum(Long id);


}

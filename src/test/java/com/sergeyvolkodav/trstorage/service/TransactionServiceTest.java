package com.sergeyvolkodav.trstorage.service;

import com.sergeyvolkodav.trstorage.rest.data.TransactionData;
import com.sergeyvolkodav.trstorage.rest.exception.AppTRStoreException;
import com.sergeyvolkodav.trstorage.test.TrTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TransactionServiceTest extends TrTest {

    @Autowired
    ITransactionService transactionService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testSaveTransactionWithoutParent() {
        TransactionData transactionData = new TransactionData();
        transactionData.setAmount(400D);
        transactionData.setId(99L);
        transactionData.setType("Bank");
        transactionService.saveTransaction(transactionData);
    }

    @Test
    public void testInvalidSaveCycling() {
        String errorMsg = "Detect cycling graph";
        TransactionData transactionData = new TransactionData();
        transactionData.setAmount(400D);
        transactionData.setId(1L);
        transactionData.setType("Bank");
        transactionData.setParentId(3L);
        exception.expect(AppTRStoreException.class);
        transactionService.saveTransaction(transactionData);
        exception.expectMessage(equalTo(errorMsg));
    }

    @Test
    public void testInvalidTransactionId() {
        exception.expect(AppTRStoreException.class);
        transactionService.getTransactionDataById(9999L);
    }

    @Test
    public void testValidTransactionId() {
        Long id = 1L;
        TransactionData transactionDataById = transactionService.getTransactionDataById(id);
        assertThat(transactionDataById.getId(), is(id));
    }

    @Test
    public void testGetTransactionList() {
        String type = "bank";
        List<Long> transactionListByType = transactionService.getTransactionListByType(type);
        assertThat(transactionListByType.size(), is(2));
    }

    @Test
    public void testGetTransactionEmptyList() {
        String type = "unknown";
        List<Long> transactionListByType = transactionService.getTransactionListByType(type);
        assertThat(transactionListByType.size(), is(0));
    }

    @Test
    public void testTransactionSum() {
        Double transactionSum = transactionService.getTransactionSum(4L);
        assertThat(transactionSum, is(950D));
    }

    @Test
    public void testInvalidTransactionSum() {
        exception.expect(AppTRStoreException.class);
        Double transactionSum = transactionService.getTransactionSum(9999L);
    }
}
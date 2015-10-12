package com.sergeyvolkodav.trstorage.repository;


import com.sergeyvolkodav.trstorage.model.TransactionModel;
import com.sergeyvolkodav.trstorage.test.TrTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertThat;


public class TrRepositoryTest  extends TrTest{

    @Autowired
    TrRepository trRepository;

    @Test
    public void testSaveTransaction() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setId(100l);
        transactionModel.setAmount(new BigDecimal(500.11));
        transactionModel.setType("Bank");
        transactionModel.setMpath("/100");
        trRepository.save(transactionModel);
    }

    @Test
    public void getAllTransaction() {
        List<TransactionModel> all = trRepository.findAll();
        assertThat(all, is(notNullValue()));
    }

    @Test
    public void getTransactionsByType() {
        List<TransactionModel> trList = trRepository.findByTypeIgnoreCase("car");
        assertThat(trList.size(), is(3));
    }

    @Test
    public void getTransactionById() {
        BigDecimal amountExpected = new BigDecimal(100).setScale(2);
        TransactionModel tr = trRepository.findById(1L);
        assertThat(tr.getId(), is(1L));
        assertThat(tr.getAmount(), is(amountExpected));
        assertThat(tr.getParentId(), is(nullValue()));
        assertThat(tr.getType(), is("car"));
    }

    @Test
    public void getTransactionSum() {
        Double sum = trRepository.sumParentByPath("/1");
        assertThat(sum, is (600D));
    }
}

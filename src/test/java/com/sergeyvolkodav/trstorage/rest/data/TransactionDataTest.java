package com.sergeyvolkodav.trstorage.rest.data;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TransactionDataTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testInvalidTransactionDataNullType() {
        TransactionData transactionData = new TransactionData();
        transactionData.setAmount(10.10D);

        Set<ConstraintViolation<TransactionData>> constraintViolations =
                validator.validate(transactionData);

        assertThat(constraintViolations.size(), is(1));
        assertThat(constraintViolations.iterator().next().getMessage(), is("Type cannot be blank"));
    }

    @Test
    public void testInvalidTransactionDataAmount() {
        TransactionData transactionData = new TransactionData();
        transactionData.setType("bank");
        transactionData.setAmount(10.101D);

        Set<ConstraintViolation<TransactionData>> constraintViolations =
                validator.validate(transactionData);

        assertThat(constraintViolations.size(), is(1));
        assertThat(constraintViolations.iterator().next().getMessage(), is("Amount should be set in money format"));
    }

    @Test
    public void testValidTransactionData() {
        TransactionData transactionData = new TransactionData();
        transactionData.setType("bank");
        transactionData.setAmount(100.99D);

        Set<ConstraintViolation<TransactionData>> constraintViolations =
                validator.validate(transactionData);

        assertThat(constraintViolations.size(), is(0));
    }


}
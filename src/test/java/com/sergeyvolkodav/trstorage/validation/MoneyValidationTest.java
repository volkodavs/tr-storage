package com.sergeyvolkodav.trstorage.validation;

import com.sergeyvolkodav.trstorage.utils.validation.MoneyValidation;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class MoneyValidationTest {

    @Test
    public void testValidMoneyFormat() {
        MoneyValidation moneyValidation = new MoneyValidation();
        assertThat(moneyValidation.isValid(22.11D, null), is(true));
    }

    @Test
    public void testInvalidScaleMoneyFormat(){
        MoneyValidation moneyValidation = new MoneyValidation();
        assertThat(moneyValidation.isValid(22.111D, null), is(false));
    }

    @Test
    public void testInvalidPrecisionMoneyFormat(){
        MoneyValidation moneyValidation = new MoneyValidation();
        assertThat(moneyValidation.isValid(-10.11D, null), is(false));
    }
}

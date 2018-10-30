package calc.currency.com.currencycalculator.utils;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import calc.currency.com.currencycalculator.database.DbHelper;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.service.StorageService;
import calc.currency.com.currencycalculator.service.impl.StorageServiceImpl;

import static org.junit.Assert.assertTrue;

public class CurrencyUtilsTest {

    @Before
    public void init(){
    }

    @Test
    public void valueStringToDouble() {

    }

    @Test
    public void getPrice() {
        Currency firstCurrency = new Currency();
        firstCurrency.setCurrencyId("");
        firstCurrency.setCharCode("RUB");
        firstCurrency.setName("Рубль");
        firstCurrency.setNominal(1);
        firstCurrency.setNumCode(-1);
        firstCurrency.setValue("1,0");

        Currency secondCurrency = new Currency();
        secondCurrency.setCurrencyId("");
        secondCurrency.setCharCode("USD");
        secondCurrency.setName("Dollar");
        secondCurrency.setNominal(1);
        secondCurrency.setNumCode(42);
        secondCurrency.setValue(String.valueOf(65.32));

        double price = CurrencyUtils.getPrice(firstCurrency, secondCurrency, 4.5);

        assertTrue(price > 0);
    }
}
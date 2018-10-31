package calc.currency.com.currencycalculator.utils;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import calc.currency.com.currencycalculator.database.DbHelper;
import calc.currency.com.currencycalculator.exception.CouldNotConvertException;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.service.StorageService;
import calc.currency.com.currencycalculator.service.impl.StorageServiceImpl;

import static org.junit.Assert.assertTrue;

public class CurrencyUtilsTest {

    @Before
    public void init(){
    }

    @Test
    public void getPriceStaticNullTest() {

        Currency firstCurrency = new Currency();
        firstCurrency.setCurrencyId("");
        firstCurrency.setCharCode("RUB");
        firstCurrency.setName("Рубль");
        firstCurrency.setNominal(1);
        firstCurrency.setNumCode(-1);
        firstCurrency.setValue("1as0");


        Currency secondCurrency = new Currency();
        secondCurrency.setCurrencyId("");
        secondCurrency.setCharCode("USD");
        secondCurrency.setName("Dollar");
        secondCurrency.setNominal(1);
        secondCurrency.setNumCode(42);
        secondCurrency.setValue(String.valueOf(65.32));

        double price = 0;
        try {
            price = CurrencyUtils.getPrice(firstCurrency, secondCurrency, -1);
        } catch (CouldNotConvertException e) {
            e.printStackTrace();
        }
        System.out.println(price);
        try {
            price = CurrencyUtils.getPrice(null, secondCurrency, 1);
        } catch (CouldNotConvertException e) {
            e.printStackTrace();
        }
        System.out.println(price);
        try {
            price = CurrencyUtils.getPrice(secondCurrency, null, 1);
        } catch (CouldNotConvertException e) {
            e.printStackTrace();
        }
        System.out.println(price);
        try {
            price = CurrencyUtils.getPrice(null, null, -2);
        } catch (CouldNotConvertException e) {
            e.printStackTrace();
        }
        System.out.println(price);

    }
}
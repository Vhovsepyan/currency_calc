package utils;

import org.junit.Test;

import calc.currency.com.currencycalculator.model.Currency;

public class CurrencyUtilsTest {

    @Test
    public void valueStringToDouble() {
        CurrencyUtils.valueStringToDouble("-5");
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
    }
}
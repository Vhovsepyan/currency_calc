package calc.currency.com.currencycalculator.service;

import calc.currency.com.currencycalculator.model.CurrencyList;

public interface CacheableCurrencyDataSource extends CurrencyDataSource {
    void saveCurrencies(CurrencyList currencyList);
}

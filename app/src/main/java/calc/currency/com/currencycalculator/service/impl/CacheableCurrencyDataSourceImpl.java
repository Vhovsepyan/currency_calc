package calc.currency.com.currencycalculator.service.impl;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import calc.currency.com.currencycalculator.service.DataSourceListener;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.model.CurrencyList;
import calc.currency.com.currencycalculator.service.CacheableCurrencyDataSource;
import calc.currency.com.currencycalculator.service.StorageService;

public class CacheableCurrencyDataSourceImpl implements CacheableCurrencyDataSource {
    private final StorageService storageService;

    public CacheableCurrencyDataSourceImpl(@NonNull StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void saveCurrencies(CurrencyList currencyList) {
        insertOrUpdateCurrencies(currencyList);
    }

    @Override
    public void getCurrencies(DataSourceListener<CurrencyList> responseListener) {
        List<Currency> currencies = storageService.getAllCurrencies();
        if (currencies == null || currencies.isEmpty()) {
            responseListener.onError("NO CACHED VALUE");
        } else {
            CurrencyList currencyList = new CurrencyList();//TODO get CurrencyList from db
            currencyList.setDate(new Date().toString());
            currencyList.setName("Name");
            currencyList.setCurrencies(currencies);
            responseListener.onSuccess(currencyList);
        }
    }

    private void insertOrUpdateCurrencies(@NonNull CurrencyList currencyList) {
        List<Currency> currencies = currencyList.getCurrencies();
        if (currencies != null && !currencies.isEmpty()) {
            for (int i = 0; i < currencies.size(); i++) {
                Currency currency = currencies.get(i);
                storageService.insertOrUpdateCurrency(currency);
            }
        }
    }
}

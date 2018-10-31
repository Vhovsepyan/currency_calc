package calc.currency.com.currencycalculator.service;

import android.support.annotation.NonNull;

import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.model.CurrencyList;

public class CurrencyRepository implements CurrencyDataSource {
    private final CacheableCurrencyDataSource cacheableCurrencyDataSource;
    private final CurrencyDataSource currencyDataSource;

    public CurrencyRepository(CacheableCurrencyDataSource cacheableCurrencyDataSource, CurrencyDataSource currencyDataSource) {
        this.cacheableCurrencyDataSource = cacheableCurrencyDataSource;
        this.currencyDataSource = currencyDataSource;
    }

    private static Currency generateCurrencyRub() {
        Currency rub = new Currency();
        rub.setCurrencyId("");
        rub.setCharCode("RUB");
        rub.setName("Рубль");
        rub.setNominal(1);
        rub.setNumCode(-1);
        rub.setValue("1,0");
        return rub;
    }

    @Override
    public void getCurrencies(DataSourceListener<CurrencyList> responseListener) {
        DataSourceListener<CurrencyList> listenerRemote = new DataSourceListener<CurrencyList>() {
            @Override
            public void onSuccess(@NonNull CurrencyList obj) {
                obj.getCurrencies().add(generateCurrencyRub());
                cacheableCurrencyDataSource.saveCurrencies(obj);
                responseListener.onSuccess(obj);
            }

            @Override
            public void onError(String errorMessage) {
                DataSourceListener<CurrencyList> listenerFromCache = new DataSourceListener<CurrencyList>() {
                    @Override
                    public void onSuccess(@NonNull CurrencyList obj) {
                        responseListener.onSuccess(obj);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        responseListener.onError(errorMessage);
                    }
                };
                cacheableCurrencyDataSource.getCurrencies(listenerFromCache);
            }
        };
        currencyDataSource.getCurrencies(listenerRemote);
    }
}

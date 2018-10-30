package calc.currency.com.currencycalculator.service;

import android.support.annotation.NonNull;

import calc.currency.com.currencycalculator.http.HttpResponseListener;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.model.CurrencyList;

public class CurrencyRepository implements CurrencyDataSource {
    private final CacheableCurrencyDataSource cacheableCurrencyDataSource;
    private final CurrencyDataSource currencyDataSource;

    public CurrencyRepository(CacheableCurrencyDataSource cacheableCurrencyDataSource, CurrencyDataSource currencyDataSource) {
        this.cacheableCurrencyDataSource = cacheableCurrencyDataSource;
        this.currencyDataSource = currencyDataSource;
    }

    private static Currency getCurrencyRub() {
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
    public void getCurrencies(HttpResponseListener<CurrencyList> responseListener) {
        HttpResponseListener<CurrencyList> listenerFromCache = new HttpResponseListener<CurrencyList>() {
            @Override
            public void onSuccess(@NonNull CurrencyList obj) {
                responseListener.onSuccess(obj);
            }

            @Override
            public void onError(String errorMessage) {
                responseListener.onError(errorMessage);
            }
        };
        HttpResponseListener<CurrencyList> listenerRemote = new HttpResponseListener<CurrencyList>() {
            @Override
            public void onSuccess(@NonNull CurrencyList obj) {
                obj.getCurrencies().add(getCurrencyRub());
                cacheableCurrencyDataSource.saveCurrencies(obj);
                responseListener.onSuccess(obj);
            }

            @Override
            public void onError(String errorMessage) {
                responseListener.onError(errorMessage);
            }
        };
        cacheableCurrencyDataSource.getCurrencies(listenerFromCache);
        currencyDataSource.getCurrencies(listenerRemote);
    }
}

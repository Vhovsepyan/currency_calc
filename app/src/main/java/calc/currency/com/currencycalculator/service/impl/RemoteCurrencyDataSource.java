package calc.currency.com.currencycalculator.service.impl;

import android.support.annotation.NonNull;

import calc.currency.com.currencycalculator.service.DataSourceListener;
import calc.currency.com.currencycalculator.http.RequestHelper;
import calc.currency.com.currencycalculator.model.CurrencyList;
import calc.currency.com.currencycalculator.service.CurrencyDataSource;

public class RemoteCurrencyDataSource implements CurrencyDataSource {
    private final RequestHelper<CurrencyList> requestHelper;

    public RemoteCurrencyDataSource(@NonNull RequestHelper<CurrencyList> requestHelper) {
        this.requestHelper = requestHelper;
    }

    @Override
    public void getCurrencies(DataSourceListener<CurrencyList> responseListener) {
        requestHelper.getCurrencies(responseListener);
    }
}

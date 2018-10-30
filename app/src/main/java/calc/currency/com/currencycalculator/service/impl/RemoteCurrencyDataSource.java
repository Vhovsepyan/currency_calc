package calc.currency.com.currencycalculator.service.impl;

import android.support.annotation.NonNull;

import calc.currency.com.currencycalculator.http.HttpResponseListener;
import calc.currency.com.currencycalculator.http.RequestHelper;
import calc.currency.com.currencycalculator.model.CurrencyList;
import calc.currency.com.currencycalculator.service.CurrencyDataSource;

/**
 * Created by Norayr Tiratsyan
 * Date: 10/30/18
 * Time: 4:14 PM
 */
public class RemoteCurrencyDataSource implements CurrencyDataSource {
    private final RequestHelper<CurrencyList> requestHelper;

    public RemoteCurrencyDataSource(@NonNull RequestHelper<CurrencyList> requestHelper) {
        this.requestHelper = requestHelper;
    }

    @Override
    public void getCurrencies(HttpResponseListener<CurrencyList> responseListener) {
        requestHelper.getCurrencies(responseListener);
    }
}

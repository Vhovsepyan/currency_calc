package calc.currency.com.currencycalculator.service;

import calc.currency.com.currencycalculator.http.HttpResponseListener;
import calc.currency.com.currencycalculator.model.CurrencyList;

public interface CurrencyDataSource {
    void getCurrencies(HttpResponseListener<CurrencyList> responseListener);
}

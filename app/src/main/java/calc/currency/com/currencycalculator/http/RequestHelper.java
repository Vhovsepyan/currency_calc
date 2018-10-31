package calc.currency.com.currencycalculator.http;

import android.support.annotation.NonNull;

import java.io.InputStream;

import calc.currency.com.currencycalculator.service.DataSourceListener;

public interface RequestHelper<T> {

    InputStream getInputStream(String url) throws NullPointerException;

    void getCurrencies(@NonNull DataSourceListener<T> responseListener);

}
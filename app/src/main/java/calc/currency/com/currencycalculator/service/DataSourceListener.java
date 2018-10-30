package calc.currency.com.currencycalculator.service;

import android.support.annotation.NonNull;

public interface DataSourceListener<T> {

    void onSuccess(@NonNull T obj);

    void onError(String errorMessage);
}
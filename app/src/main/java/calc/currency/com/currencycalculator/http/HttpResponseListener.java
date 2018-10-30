package calc.currency.com.currencycalculator.http;

import android.support.annotation.NonNull;

public interface HttpResponseListener<T> {

    void onSuccess(@NonNull T obj);

    void onError(String errorMessage);
}
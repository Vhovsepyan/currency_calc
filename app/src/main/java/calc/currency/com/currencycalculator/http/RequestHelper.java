package calc.currency.com.currencycalculator.http;

import java.io.InputStream;

public interface RequestHelper<T> {

    InputStream getInputStream(String url) throws NullPointerException;

    void getCurrencies(HttpResponseListener<T> responseListener);

}
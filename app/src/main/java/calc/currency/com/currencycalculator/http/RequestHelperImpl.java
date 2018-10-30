package calc.currency.com.currencycalculator.http;

import android.support.annotation.Nullable;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import calc.currency.com.currencycalculator.model.CurrencyList;

public class RequestHelperImpl implements RequestHelper<CurrencyList> {

    @Nullable
    private URLConnection getConnection(String stringUrl) {
        URLConnection urlConnection = null;
        try {
            URL url = new URL(stringUrl);
            urlConnection = url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlConnection;
    }

    @Override
    public InputStream getInputStream(String stringUrl) throws NullPointerException {
        HttpURLConnection connection = (HttpURLConnection) getConnection(stringUrl);
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    public void getCurrencies(HttpResponseListener<CurrencyList> responseListener) {
        Serializer serializer = new Persister();
        InputStream inputStream = getInputStream(HttpConstants.CURRENCIES_URL);
        CurrencyList currencies = null;
        try {
            currencies = serializer.read(CurrencyList.class, inputStream);
        } catch (Exception e) {
            responseListener.onError(e.getMessage());
            e.printStackTrace();
        }

        if (responseListener != null) {
            if (currencies == null) {
                responseListener.onError("NO INTERNET");
            } else {
                responseListener.onSuccess(currencies);
            }
        }
    }
}
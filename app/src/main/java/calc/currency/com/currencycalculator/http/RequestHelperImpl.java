package calc.currency.com.currencycalculator.http;

import android.support.annotation.NonNull;
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
import calc.currency.com.currencycalculator.service.DataSourceListener;

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
    public void getCurrencies(@NonNull DataSourceListener<CurrencyList> responseListener) {
        Serializer serializer = new Persister();
        InputStream inputStream = getInputStream(HttpConstants.CURRENCIES_URL);
        try {
            CurrencyList currencies = serializer.read(CurrencyList.class, inputStream);
            if (currencies == null) {
                responseListener.onError("NO INTERNET");
            } else {
                responseListener.onSuccess(currencies);
            }
        } catch (Exception e) {
            responseListener.onError(e.getMessage());
        }
    }
}
package calc.currency.com.currencycalculator.http;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RequestHelperImpl implements RequestHelper{

    @Nullable
    private URLConnection getConnection(String stringUrl) {
        URLConnection urlConnection = null;
        URL url = null;
        try {
            url = new URL(stringUrl);
            urlConnection = url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlConnection;
    }

    @Override
    public InputStream getInputStream(String stringUrl) throws NullPointerException{
        HttpURLConnection connection = (HttpURLConnection) getConnection(stringUrl);
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}

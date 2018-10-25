package calc.currency.com.currencycalculator.example;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import calc.currency.com.currencycalculator.http.RequestHelper;
import calc.currency.com.currencycalculator.http.RequestHelperImpl;
import calc.currency.com.currencycalculator.model.CurrencyList;

public class Main {
    public static void main(String[] args) throws Exception {
        Serializer serializer = new Persister();
        RequestHelper requestHelper = new RequestHelperImpl();
        InputStream inputStream = requestHelper.getInputStream("http://www.cbr.ru/scripts/XML_daily.asp");
        CurrencyList currencies = serializer.read(CurrencyList.class, inputStream);
        System.out.println(currencies.toString());
    }
}

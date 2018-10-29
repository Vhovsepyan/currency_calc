package calc.currency.com.currencycalculator.http;

import java.io.InputStream;

public interface RequestHelper {

    InputStream getInputStream(String url) throws NullPointerException;

}

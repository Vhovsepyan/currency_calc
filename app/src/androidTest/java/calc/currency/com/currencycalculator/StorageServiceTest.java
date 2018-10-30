package calc.currency.com.currencycalculator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import calc.currency.com.currencycalculator.database.DbHelper;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.service.CacheableCurrencyDataSource;
import calc.currency.com.currencycalculator.service.StorageService;
import calc.currency.com.currencycalculator.service.impl.CacheableCurrencyDataSourceImpl;
import calc.currency.com.currencycalculator.service.impl.StorageServiceImpl;

import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StorageServiceTest {
    private StorageService storageService;
    private Context mContext;

    @Before
    public void init() {
        mContext = InstrumentationRegistry.getTargetContext();
        DbHelper helper = DbHelper.getInstance(mContext);
        storageService = new StorageServiceImpl(helper);
    }


    @Test
    public void getAllCurrencies() {
        List<Currency> currencies = storageService.getAllCurrencies();
        assertTrue(currencies.size() > 0);
    }

    @Test
    public void dbCurrencyReadWriteTest() {
        Currency currency = new Currency();
        currency.setCharCode("USD");
        currency.setCurrencyId("R0101");
        currency.setName("Dollar USA");
        currency.setNominal(1);
        currency.setNumCode(456);
        currency.setValue(String.valueOf(45.32));

        storageService.insertOrUpdateCurrency(currency);

        Currency newCurrency = storageService.getCurrencyById(currency.getCurrencyId());

        assertTrue(currency.equals(newCurrency));
    }
}

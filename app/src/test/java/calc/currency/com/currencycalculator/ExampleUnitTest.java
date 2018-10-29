package calc.currency.com.currencycalculator;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import calc.currency.com.currencycalculator.database.DbHelper;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.presenter.HomePresenter;
import calc.currency.com.currencycalculator.service.StorageService;
import calc.currency.com.currencycalculator.service.impl.StorageServiceImpl;
import calc.currency.com.currencycalculator.view.HomeActivityView;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private HomePresenter presenter;

    @Mock
    private StorageService storageService;

    @Mock
    private HomeActivityView view;

    @Mock
    private Context context;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void addition_isCorrect() {
        DbHelper.initDatabase(context);
        DbHelper helper = DbHelper.getInstance();
        storageService = new StorageServiceImpl(helper);

        List<Currency> currencies = storageService.getAllCurrencies();

        assertTrue(currencies.size() < 0);


    }


}
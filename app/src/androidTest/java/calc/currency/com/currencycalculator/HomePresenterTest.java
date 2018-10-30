package calc.currency.com.currencycalculator;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import calc.currency.com.currencycalculator.database.DbHelper;
import calc.currency.com.currencycalculator.http.RequestHelper;
import calc.currency.com.currencycalculator.http.RequestHelperImpl;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.presenter.HomePresenter;
import calc.currency.com.currencycalculator.service.StorageService;
import calc.currency.com.currencycalculator.service.impl.StorageServiceImpl;
import calc.currency.com.currencycalculator.view.HomeActivityView;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class HomePresenterTest {

    private DbHelper dbHelper;
    private RequestHelper requestHelper;
    private StorageService storageService;
    private HomeActivityView activityView;


    @Before
    public void init(){
        dbHelper = DbHelper.getInstance();
        requestHelper = new RequestHelperImpl();
        storageService = new StorageServiceImpl(dbHelper);

    }

    @Test
    public void startTest(){
        final int[] dataIsReadyCount = {0};
        final int[] showLoaderCount = {0};
        final int[] calculationIsReadyCount = {0};
        final int[] isAliveCount = {0};

        HomePresenter presenter = new HomePresenter(new HomeActivityView() {
            @Override
            public void showLoader() {
                showLoaderCount[0]++;
            }

            @Override
            public void dismissLoader() {

            }

            @Override
            public void dataIsReady(List<Currency> currencies) {
                dataIsReadyCount[0]++;
            }

            @Override
            public void calculationIsReady(String result) {
                calculationIsReadyCount[0]++;
            }

            @Override
            public void replaceCurrencies(int firstCurrencyPosition, int secondCurrencyPosition) {

            }

            @Override
            public boolean isAlive() {
                isAliveCount[0]++;
                return false;
            }
        }, storageService);

        presenter.start();
        Currency currencyUSD = storageService.getCurrencyByCharCode("USD");
        Currency currencyAMD = storageService.getCurrencyByCharCode("AMD");
        presenter.setFirstCurrency(currencyAMD);
        presenter.setSecondCurrency(currencyUSD);

        assertTrue(dataIsReadyCount[0] == 1




























































































































































































































































        );
        assertTrue(calculationIsReadyCount[0] == 0);

        presenter.convertCurrencies();

        assertTrue(dataIsReadyCount[0] == 1);
        assertTrue(calculationIsReadyCount[0] == 1);
    }

}

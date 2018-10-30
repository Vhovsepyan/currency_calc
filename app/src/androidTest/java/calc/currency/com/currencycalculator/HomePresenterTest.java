package calc.currency.com.currencycalculator;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import calc.currency.com.currencycalculator.database.DbHelper;
import calc.currency.com.currencycalculator.http.RequestHelper;
import calc.currency.com.currencycalculator.http.RequestHelperImpl;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.presenter.HomePresenter;
import calc.currency.com.currencycalculator.service.StorageService;
import calc.currency.com.currencycalculator.service.impl.StorageServiceImpl;
import calc.currency.com.currencycalculator.view.HomeActivityView;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class HomePresenterTest {

    private DbHelper dbHelper;
    private RequestHelper requestHelper;
    private StorageService storageService;
    private HomeActivityView activityView;
    private ExecutorService delegateExecutor;


    @Before
    public void init() {
        dbHelper = DbHelper.getInstance();
        requestHelper = new RequestHelperImpl();
        storageService = new StorageServiceImpl(dbHelper);
        delegateExecutor = Executors.newSingleThreadExecutor();

    }

    @Test
    public void startTest() {
        final int[] dataIsReadyCount = {0};
        final int[] showLoaderCount = {0};
        final int[] calculationIsReadyCount = {0};
        final int[] isAliveCount = {0};

        Currency currencyUSD = storageService.getCurrencyByCharCode("USD");
        Currency currencyAMD = storageService.getCurrencyByCharCode("AMD");

        CountDownLatch l = new CountDownLatch(1);

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
                l.countDown();
                dataIsReadyCount[0]++;
            }

            @Override
            public void calculationIsReady(String result) {
                calculationIsReadyCount[0]++;
                Log.i("tttt_log", "calculationIsReady calcCount = " + calculationIsReadyCount[0]);
            }

            @Override
            public void replaceCurrencies(int firstCurrencyPosition, int secondCurrencyPosition) {

            }

            @Override
            public boolean isAlive() {
                isAliveCount[0]++;
                return false;
            }
        }, storageService, delegateExecutor);

        presenter.start();

        presenter.setFromCurrency(currencyAMD);
        presenter.setToCurrency(currencyUSD);
        try {
            l.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(dataIsReadyCount[0] == 1);
        assertTrue(calculationIsReadyCount[0] == 1);
        Log.i("tttt_log", " 1 calcCount = " + calculationIsReadyCount[0]);
        presenter.convertCurrencies();

        assertTrue(dataIsReadyCount[0] == 1);
        assertTrue(calculationIsReadyCount[0] == 1);
        Log.i("tttt_log", " 2 calcCount = " + calculationIsReadyCount[0]);
    }

}

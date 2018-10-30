package calc.currency.com.currencycalculator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
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
import calc.currency.com.currencycalculator.model.CurrencyList;
import calc.currency.com.currencycalculator.presenter.HomePresenter;
import calc.currency.com.currencycalculator.service.CacheableCurrencyDataSource;
import calc.currency.com.currencycalculator.service.CurrencyDataSource;
import calc.currency.com.currencycalculator.service.CurrencyRepository;
import calc.currency.com.currencycalculator.service.StorageService;
import calc.currency.com.currencycalculator.service.impl.CacheableCurrencyDataSourceImpl;
import calc.currency.com.currencycalculator.service.impl.RemoteCurrencyDataSource;
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
    private CurrencyDataSource currencyDataSource;
    private Context mContext;

    @Before
    public void init() {
        mContext = InstrumentationRegistry.getTargetContext();
        dbHelper = DbHelper.getInstance(mContext);
        requestHelper = new RequestHelperImpl();
        storageService = new StorageServiceImpl(dbHelper);
        delegateExecutor = Executors.newSingleThreadExecutor();

        StorageService storageService = new StorageServiceImpl(dbHelper);
        RequestHelper<CurrencyList> requestHelper = new RequestHelperImpl();
        CacheableCurrencyDataSource cacheableCurrencyDataSource = new CacheableCurrencyDataSourceImpl(storageService);
        CurrencyDataSource remoteCurrencyDataSource = new RemoteCurrencyDataSource(requestHelper);
        currencyDataSource = new CurrencyRepository(cacheableCurrencyDataSource, remoteCurrencyDataSource);

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
        }, delegateExecutor, currencyDataSource);

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

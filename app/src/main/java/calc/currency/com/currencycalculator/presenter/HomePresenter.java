package calc.currency.com.currencycalculator.presenter;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

import calc.currency.com.currencycalculator.service.DataSourceListener;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.model.CurrencyList;
import calc.currency.com.currencycalculator.service.CurrencyDataSource;
import calc.currency.com.currencycalculator.view.HomeActivityView;
import calc.currency.com.currencycalculator.utils.CurrencyUtils;

public class HomePresenter extends BasePresenter {
    private final Executor executor;
    private final CurrencyDataSource dataSource;
    private volatile HomeActivityView homeView;
    private Currency fromCurrency;
    private Currency toCurrency;
    private double valueToConvert;
    private CopyOnWriteArrayList<Currency> mCurrencies = new CopyOnWriteArrayList<>();
    private DataSourceListener<CurrencyList> responseListener = new DataSourceListener<CurrencyList>() {
        @Override
        public void onSuccess(@NonNull CurrencyList obj) {
            mCurrencies.clear();
            mCurrencies.addAll(obj.getCurrencies());
            Collections.sort(mCurrencies, (o1, o2) -> o1.getCharCode().compareTo(o2.getCharCode()));
            getMainHandler().post(() -> {
                if (homeView != null) {
                    homeView.dataIsReady(mCurrencies);//TODO pass value CurrencyList ensted list, there is usefull information in CurrencyList object
                    homeView.dismissLoader();
                }
            });
        }

        @Override
        public void onError(String errorMessage) {
            //TODO show the error message
            getMainHandler().post(() -> {
                if (homeView != null) {
                    homeView.dismissLoader();
                }
            });
        }
    };

    public HomePresenter(HomeActivityView homeView, Executor executor, CurrencyDataSource dataSource) {
        super();
        this.dataSource = dataSource;
        this.homeView = homeView;
        this.executor = executor;
    }

    public void start() {
        homeView.showLoader();
        executor.execute(this::getCurrenciesFromAPI);
    }

    public void stop() {
        homeView = null;
        getMainHandler().removeCallbacksAndMessages(null);
    }


    private void getCurrenciesFromAPI() {
        dataSource.getCurrencies(responseListener);
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
        convertCurrencies();
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
        convertCurrencies();
    }

    public void setValueToConvert(String valueToConvert) {
        if (valueToConvert.isEmpty()) {
            this.valueToConvert = 0;
        } else {
            this.valueToConvert = Double.parseDouble(valueToConvert);
        }
        convertCurrencies();
    }

    public void replaceCurrencies() {
        int firstCurrencyIndex = mCurrencies.indexOf(fromCurrency);
        int secondCurrencyIndex = mCurrencies.indexOf(toCurrency);

        Currency tempCurrency = fromCurrency;
        fromCurrency = toCurrency;
        toCurrency = tempCurrency;

        if (homeView != null) {
            getMainHandler().post(() -> homeView.replaceCurrencies(firstCurrencyIndex, secondCurrencyIndex));
        }
    }


    public void convertCurrencies() {
        if (fromCurrency == null || toCurrency == null) {
            return;
        }
        double result = CurrencyUtils.getPrice(fromCurrency, toCurrency, valueToConvert);
        final String moneyString = CurrencyUtils.getString(result);
        if (homeView != null) {
            getMainHandler().post(() -> homeView.calculationIsReady(moneyString));
        }
    }


}
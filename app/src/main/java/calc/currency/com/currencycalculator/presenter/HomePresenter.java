package calc.currency.com.currencycalculator.presenter;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import calc.currency.com.currencycalculator.http.HttpResponseListener;
import calc.currency.com.currencycalculator.http.RequestHelper;
import calc.currency.com.currencycalculator.http.RequestHelperImpl;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.model.CurrencyList;
import calc.currency.com.currencycalculator.service.StorageService;
import calc.currency.com.currencycalculator.view.HomeActivityView;
import utils.CurrencyUtils;

public class HomePresenter extends BasePresenter {
    private final Executor executor;
    private volatile HomeActivityView homeView;
    private Currency fromCurrency;
    private Currency toCurrency;
    private double valueToConvert;
    private List<Currency> mCurrencies = new ArrayList<>();
    private HttpResponseListener<CurrencyList> responseListener = new HttpResponseListener<CurrencyList>() {
        @Override
        public void onSuccess(CurrencyList obj) {
            if (obj != null) {
                insertOrUpdateCurrencies(obj);
                mCurrencies.addAll(obj.getCurrencies());
            } else {
                mCurrencies.addAll(getStorageService().getAllCurrencies());
            }
            mCurrencies.add(getCurrencyRub());

            Collections.sort(mCurrencies, (o1, o2) -> o1.getCharCode().compareTo(o2.getCharCode()));

            getMainHandler().post(() -> {
                if (homeView != null) {
                    homeView.dataIsReady(mCurrencies);
                    homeView.dismissLoader();
                }
            });
        }

        @Override
        public void onError(String errorMessage) {
            getMainHandler().post(() -> {
                if (homeView != null) {
                    homeView.dismissLoader();
                }
            });
        }
    };

    public HomePresenter(HomeActivityView homeView, StorageService storageService, Executor executor) {
        super(storageService);
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

    private Currency getCurrencyRub(){
        Currency rub = new Currency();
        rub.setId("");
        rub.setCharCode("RUB");
        rub.setName("Рубль");
        rub.setNominal(1);
        rub.setNumCode(-1);
        rub.setValue("1,0");
        return rub;
    }

    private void getCurrenciesFromAPI() {
        RequestHelper<CurrencyList> requestHelper = new RequestHelperImpl();
        requestHelper.getCurrencies(responseListener);
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
        convertCurrencies();
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
        convertCurrencies();
    }

    private void insertOrUpdateCurrencies(@NonNull CurrencyList currencyList) {
        List<Currency> currencies = currencyList.getCurrencies();
        if (currencies != null && !currencies.isEmpty()) {
            for (int i = 0; i < currencies.size(); i++) {
                getStorageService().inserCurrency(currencies.get(i));
            }
        }
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
        final String moneyString = getString(result);
        if (homeView != null) {
            getMainHandler().post(() -> homeView.calculationIsReady(moneyString));
        }
    }

    private String getString(double result) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        return formatter.format(result);
    }
}
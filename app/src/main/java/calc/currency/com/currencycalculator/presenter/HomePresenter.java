package calc.currency.com.currencycalculator.presenter;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import calc.currency.com.currencycalculator.http.HttpResponseListener;
import calc.currency.com.currencycalculator.http.RequestHelper;
import calc.currency.com.currencycalculator.http.RequestHelperImpl;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.model.CurrencyList;
import calc.currency.com.currencycalculator.service.StorageService;
import calc.currency.com.currencycalculator.view.HomeActivity;
import calc.currency.com.currencycalculator.view.HomeActivityView;
import utils.CurrencyUtils;

public class HomePresenter extends BasePresenter<HomeActivity> {
    private volatile HomeActivityView homeView;
    private Currency firstCurrency;
    private Currency secondCurrency;
    private double valueToConvert;
    private List<Currency> mCurrencies = new ArrayList<>();


    public HomePresenter(HomeActivityView homeView, StorageService storageService) {
        super(storageService);
        this.homeView = homeView;

    }

    public void start() {
        homeView.showLoader();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(this::getCurrenciesFromAPI);
    }

    public void stop() {
        homeView = null;
    }

    private void getCurrenciesFromAPI() {
        RequestHelper requestHelper = new RequestHelperImpl();
        requestHelper.getCurrencies(responseListener);
    }

    private HttpResponseListener<CurrencyList> responseListener = new HttpResponseListener<CurrencyList>() {
        @Override
        public void onSuccess(CurrencyList obj) {
            if (obj != null) {
                insertOrUpdateCurrencies(obj);
                mCurrencies.addAll(obj.getCurrencies());
            } else {
                mCurrencies.addAll(getStorageService().getAllCurrencies());
            }
            getMainHandler().post(() -> {
                if (homeView != null){
                    homeView.dataIsReady(mCurrencies);
                    homeView.dismissLoader();
                }
            });
        }

        @Override
        public void onError(String errorMessage) {
            getMainHandler().post(() -> {
                if (homeView != null){
                    homeView.dismissLoader();
                }
            });
        }
    };

    public void setFirstCurrency(Currency firstCurrency) {
        this.firstCurrency = firstCurrency;
        convertCurrencies();
    }

    public void setSecondCurrency(Currency secondCurrency) {
        this.secondCurrency = secondCurrency;
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

    public void replaceCurrencies(){
        int firstCurrencyIndex = mCurrencies.indexOf(firstCurrency);
        int secondCurrencyIndex = mCurrencies.indexOf(secondCurrency);

        Currency tempCurrency = firstCurrency;
        firstCurrency = secondCurrency;
        secondCurrency = tempCurrency;

        if (homeView != null) {
            getMainHandler().post(() -> {
                homeView.replaceCurrencies(firstCurrencyIndex, secondCurrencyIndex);
            });
        }
    }


    public void convertCurrencies() {
        if (firstCurrency == null || secondCurrency == null) {
            return;
        }
        double result = CurrencyUtils.getPrice(firstCurrency, secondCurrency, valueToConvert);
        final String moneyString = getString(result);
        if (homeView != null) {
            getMainHandler().post(() -> {
                homeView.calculationIsReady(moneyString);
            });
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

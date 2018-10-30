package calc.currency.com.currencycalculator.view;

import java.util.List;

import calc.currency.com.currencycalculator.model.Currency;

public interface HomeActivityView extends BaseView {
    void showLoader();

    void dismissLoader();

    void dataIsReady(List<Currency> currencies);

    void calculationIsReady(String result);

    void replaceCurrencies(int firstCurrencyPosition, int secondCurrencyPosition);
}
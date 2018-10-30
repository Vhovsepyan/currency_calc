package calc.currency.com.currencycalculator.service;

import java.util.List;

import calc.currency.com.currencycalculator.model.Currency;

public interface StorageService {

    long insertCurrency(Currency currency);

    Currency getCurrencyById(String id);

    Currency getCurrencyByCharCode(String charCode);

    List<Currency> getAllCurrencies();
}

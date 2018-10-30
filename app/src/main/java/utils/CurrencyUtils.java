package utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import calc.currency.com.currencycalculator.model.Currency;

public class CurrencyUtils {

    public static double getAmout(String amount){
        return valueStringToDouble(amount);
    }


    public static double valueStringToDouble(String value){
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        df.setDecimalFormatSymbols(symbols);
        try {
            Number number = df.parse(value);
            return number.doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return -1;

    };

    public static double getPrice(Currency first, Currency second, double amount){
        double firstValue = valueStringToDouble(first.getValue());
        double secondValue = valueStringToDouble(second.getValue());
        return  amount * (firstValue * second.getNominal()) / (first.getNominal() * secondValue);
    }
}

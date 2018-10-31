package calc.currency.com.currencycalculator.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

import calc.currency.com.currencycalculator.exception.CouldNotConvertException;
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

    public static double getPrice(Currency first, Currency second, double amount) throws CouldNotConvertException{
        if(first == null || second == null){
            return -1;
        }
        if (amount < 0){
            throw new CouldNotConvertException("Could not Convert amount < 0");
        }
        double firstValue = valueStringToDouble(first.getValue());
        double secondValue = valueStringToDouble(second.getValue());
        return  amount * (firstValue * second.getNominal()) / (first.getNominal() * secondValue);
    }

    public static String getString(double result) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        return formatter.format(result);
    }
}

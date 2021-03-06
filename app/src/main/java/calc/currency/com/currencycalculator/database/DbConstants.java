package calc.currency.com.currencycalculator.database;

public interface DbConstants {

    interface CurrencyTable {
        String TABLE_NAME = "currency";
        String COLUMN_CURRENCY_ID = "currency_id";
        String COLUMN_NUM_CODE = "num_code";
        String COLUMN_CHAR_CODE = "char_code";
        String COLUMN_NOMINAL = "nominal";
        String COLUMN_NAME = "name";
        String COLUMN_VALUE = "value";
    }
}
package calc.currency.com.currencycalculator;

import android.app.Application;

import calc.currency.com.currencycalculator.database.DbHelper;

public class CurrencyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DbHelper.initDatabase(getApplicationContext());
    }
}

package calc.currency.com.currencycalculator;

import android.app.Application;

import java.util.concurrent.Executors;

import calc.currency.com.currencycalculator.database.DbHelper;
import calc.currency.com.currencycalculator.service.ExecutorProvider;
import calc.currency.com.currencycalculator.service.impl.ExecutorProviderImpl;

public class CurrencyApplication extends Application {
    private ExecutorProvider executorProvider;

    public ExecutorProvider getExecutorProvider() {
        if (executorProvider == null) {
            synchronized (this) {
                if (executorProvider == null) {
                    executorProvider = new ExecutorProviderImpl(Executors.newSingleThreadExecutor());
                }
            }
        }
        return executorProvider;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
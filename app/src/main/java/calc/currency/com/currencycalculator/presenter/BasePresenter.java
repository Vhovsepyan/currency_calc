package calc.currency.com.currencycalculator.presenter;

import android.os.Handler;
import android.os.Looper;

public class BasePresenter {
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public BasePresenter() {

    }

    protected Handler getMainHandler() {
        return mainHandler;
    }
}
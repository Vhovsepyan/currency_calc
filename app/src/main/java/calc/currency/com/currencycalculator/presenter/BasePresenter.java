package calc.currency.com.currencycalculator.presenter;

import android.os.Handler;
import android.os.Looper;

import calc.currency.com.currencycalculator.service.StorageService;
import calc.currency.com.currencycalculator.view.BaseView;

public class BasePresenter<T extends BaseView> {
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    private final StorageService storageService;

    public BasePresenter(StorageService storageService) {
        super();
        this.storageService = storageService;
    }

    protected StorageService getStorageService(){
        return storageService;
    }

    protected Handler getMainHandler(){
        return mainHandler;
    }
}

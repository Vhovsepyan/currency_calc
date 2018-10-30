package calc.currency.com.currencycalculator.presenter;

import android.os.Handler;
import android.os.Looper;

import calc.currency.com.currencycalculator.service.StorageService;

public class BasePresenter {
    private final StorageService storageService;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public BasePresenter(StorageService storageService) {
        this.storageService = storageService;
    }

    protected StorageService getStorageService() {
        return storageService;
    }

    protected Handler getMainHandler() {
        return mainHandler;
    }
}
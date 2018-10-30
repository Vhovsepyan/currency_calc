package calc.currency.com.currencycalculator.service.impl;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import calc.currency.com.currencycalculator.service.ExecutorProvider;

public class ExecutorProviderImpl implements ExecutorProvider {
    private final Executor IOExecutor;

    public ExecutorProviderImpl(@NonNull Executor ioExecutor) {
        IOExecutor = ioExecutor;
    }

    @Override
    public Executor getIOExecutor() {
        return IOExecutor;
    }
}

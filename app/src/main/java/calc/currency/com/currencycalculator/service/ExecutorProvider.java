package calc.currency.com.currencycalculator.service;

import java.util.concurrent.Executor;

public interface ExecutorProvider {
    Executor getIOExecutor();
}

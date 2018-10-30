package calc.currency.com.currencycalculator.http;

public interface HttpResponseListener<T> {

    void onSuccess(T obj);

    void onError(String errorMessage);
}

package calc.currency.com.currencycalculator.view;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import calc.currency.com.currencycalculator.CurrencyApplication;
import calc.currency.com.currencycalculator.R;
import calc.currency.com.currencycalculator.adapter.CurrencyAdapter;
import calc.currency.com.currencycalculator.database.DbHelper;
import calc.currency.com.currencycalculator.http.RequestHelper;
import calc.currency.com.currencycalculator.http.RequestHelperImpl;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.model.CurrencyList;
import calc.currency.com.currencycalculator.presenter.HomePresenter;
import calc.currency.com.currencycalculator.service.CacheableCurrencyDataSource;
import calc.currency.com.currencycalculator.service.CurrencyDataSource;
import calc.currency.com.currencycalculator.service.CurrencyRepository;
import calc.currency.com.currencycalculator.service.ExecutorProvider;
import calc.currency.com.currencycalculator.service.StorageService;
import calc.currency.com.currencycalculator.service.impl.CacheableCurrencyDataSourceImpl;
import calc.currency.com.currencycalculator.service.impl.RemoteCurrencyDataSource;
import calc.currency.com.currencycalculator.service.impl.StorageServiceImpl;

public class HomeActivity extends AppCompatActivity implements HomeActivityView {

    private final String FIRST_CURRENCY_KEY = "FIRST_CURRENCY_KEY";
    private final String SECOND_CURRENCY_KEY = "SECOND_CURRENCY_KEY";
    int firstCurrencyPosition ;
    int secondCurrencyPosition ;

    private HomePresenter homePresenter;
    private Spinner firstSpinner;
    private Spinner secondSpinner;
    private ArrayAdapter adapter;
    private EditText firstEditText;
    private TextView resultTextView;
    private ImageView replaceIcon;
    private FrameLayout progressLayout;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            homePresenter.setValueToConvert(s.toString());
        }
    };
    private AdapterView.OnItemSelectedListener firstSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            homePresenter.setFromCurrency((Currency) firstSpinner.getAdapter().getItem(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private AdapterView.OnItemSelectedListener secondSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            homePresenter.setValueToConvert(firstEditText.getText().toString());
            homePresenter.setToCurrency((Currency) secondSpinner.getAdapter().getItem(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private View.OnClickListener replaceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            homePresenter.replaceCurrencies();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurrencyApplication application = (CurrencyApplication) getApplication();
        ExecutorProvider executorProvider = application.getExecutorProvider();
        StorageService storageService = new StorageServiceImpl(DbHelper.getInstance(application));
        RequestHelper<CurrencyList> requestHelper = new RequestHelperImpl();
        CacheableCurrencyDataSource cacheableCurrencyDataSource = new CacheableCurrencyDataSourceImpl(storageService);
        CurrencyDataSource remoteCurrencyDataSource = new RemoteCurrencyDataSource(requestHelper);
        CurrencyDataSource currencyDataSource = new CurrencyRepository(cacheableCurrencyDataSource, remoteCurrencyDataSource);
        homePresenter = new HomePresenter(this, executorProvider.getIOExecutor(), currencyDataSource);

        firstSpinner = findViewById(R.id.first_spinner);
        secondSpinner = findViewById(R.id.second_spinner);
        firstEditText = findViewById(R.id.first_edit_text);
        resultTextView = findViewById(R.id.summary_text_view);
        replaceIcon = findViewById(R.id.equals_or_replace_icon);
        replaceIcon.setOnClickListener(replaceClickListener);
        progressLayout = findViewById(R.id.progress_layout);

        if (savedInstanceState != null){
            firstCurrencyPosition = savedInstanceState.getInt(FIRST_CURRENCY_KEY);
            secondCurrencyPosition = savedInstanceState.getInt(SECOND_CURRENCY_KEY);
        }

        homePresenter.start();
    }

    @Override
    protected void onDestroy() {
        homePresenter.stop();
        super.onDestroy();
    }

    @Override
    public void showLoader() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoader() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void dataIsReady(List<Currency> currencies) {
        if (isAlive()) {
            firstEditText.addTextChangedListener(textWatcher);
            initAdapter(currencies);
        }
    }

    @Override
    public void calculationIsReady(String result) {
        resultTextView.setText(result);
    }

    @Override
    public void replaceCurrencies(int firstCurrencyPosition, int secondCurrencyPosition) {
        firstSpinner.setSelection(secondCurrencyPosition);
        secondSpinner.setSelection(firstCurrencyPosition);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    private void initAdapter(List<Currency> currencies) {
        adapter = new CurrencyAdapter(this, R.layout.currency_adapter_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstSpinner.setAdapter(adapter);
        firstSpinner.setOnItemSelectedListener(firstSpinnerListener);
        firstSpinner.setSelection(firstCurrencyPosition);
        secondSpinner.setAdapter(adapter);
        secondSpinner.setOnItemSelectedListener(secondSpinnerListener);
        secondSpinner.setSelection(secondCurrencyPosition);
    }

    @Override
    public boolean isAlive() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(FIRST_CURRENCY_KEY, firstSpinner.getSelectedItemPosition());
        outState.putInt(SECOND_CURRENCY_KEY, secondSpinner.getSelectedItemPosition());
        super.onSaveInstanceState(outState);
    }
}
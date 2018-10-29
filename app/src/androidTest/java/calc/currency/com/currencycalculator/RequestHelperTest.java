package calc.currency.com.currencycalculator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import calc.currency.com.currencycalculator.http.RequestHelper;
import calc.currency.com.currencycalculator.http.RequestHelperImpl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RequestHelperTest {
    private Context mContext;
    private RequestHelper requestHelper;

    @Before
    public void init(){
        mContext = InstrumentationRegistry.getTargetContext();
        requestHelper = new RequestHelperImpl();
    }

    @Test
    public void getUrlConnectionTest(){
        InputStream inputStream = requestHelper.getInputStream("https://examples.javacodegeeks.com/core-java/junit/junit-assertthat-example/");
        assertNotNull(inputStream);
    }
}

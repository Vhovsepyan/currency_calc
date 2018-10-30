package calc.currency.com.currencycalculator.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ValCurs")
public class CurrencyList {

    @Attribute(name = "Date")
    private String date;

    @Attribute(name = "name")
    private String name;

    @ElementList(name = "Valute", inline = true)
    private List<Currency> currencies;

    public CurrencyList() {
        super();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return "CurrencyList{" +
                "date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", currencies=" + currencies +
                '}';
    }
}
package calc.currency.com.currencycalculator.model;

import android.database.Cursor;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Objects;

import calc.currency.com.currencycalculator.database.DbConstants;

@Root(name = "Valute")
public class Currency {

    private int id;

    @Attribute(name = "ID")
    private String currencyId;

    @Element(name = "NumCode")
    private int numCode;

    @Element(name = "CharCode")
    private String charCode;

    @Element(name = "Nominal")
    private int nominal;

    @Element(name = "Name")
    private String name;

    @Element(name = "Value")
    private String value;

    public Currency() {
        super();
    }

    public Currency(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_ID);
        int currIdIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_CURRENCY_ID);
        int numCodeIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_NUM_CODE);
        int charCodeIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_CHAR_CODE);
        int nominalIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_NOMINAL);
        int nameIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_NAME);
        int valueIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_VALUE);

        this.id = cursor.getInt(idIndex);
        this.currencyId = cursor.getString(currIdIndex);
        this.numCode = cursor.getInt(numCodeIndex);
        this.charCode = cursor.getString(charCodeIndex);
        this.nominal = cursor.getInt(nominalIndex);
        this.name = cursor.getString(nameIndex);
        this.value = cursor.getString(valueIndex);


    }

    public Currency(String currencyId, int numCode, int nominal, String name, String value) {
        this.currencyId = currencyId;
        this.numCode = numCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public int getNumCode() {
        return numCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return getNumCode() == currency.getNumCode() &&
                getNominal() == currency.getNominal() &&
                Objects.equals(getCurrencyId(), currency.getCurrencyId()) &&
                Objects.equals(getCharCode(), currency.getCharCode()) &&
                Objects.equals(getName(), currency.getName()) &&
                Objects.equals(getValue(), currency.getValue());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCurrencyId(), getNumCode(), getCharCode(), getNominal(), getName(), getValue());
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyId='" + currencyId + '\'' +
                ", numCode=" + numCode +
                ", charCode='" + charCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
package calc.currency.com.currencycalculator.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import calc.currency.com.currencycalculator.database.DbConstants;

@Root(name = "Valute")
public class Currency {

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
        int currIdIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_CURRENCY_ID);
        int numCodeIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_NUM_CODE);
        int charCodeIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_CHAR_CODE);
        int nominalIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_NOMINAL);
        int nameIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_NAME);
        int valueIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_VALUE);

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

        if (numCode != currency.numCode) return false;
        if (nominal != currency.nominal) return false;
        if (currencyId != null ? !currencyId.equals(currency.currencyId) : currency.currencyId != null)
            return false;
        if (charCode != null ? !charCode.equals(currency.charCode) : currency.charCode != null)
            return false;
        if (name != null ? !name.equals(currency.name) : currency.name != null) return false;
        return value != null ? value.equals(currency.value) : currency.value == null;
    }

    @Override
    public int hashCode() {
        int result = currencyId != null ? currencyId.hashCode() : 0;
        result = 31 * result + numCode;
        result = 31 * result + (charCode != null ? charCode.hashCode() : 0);
        result = 31 * result + nominal;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @NonNull
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
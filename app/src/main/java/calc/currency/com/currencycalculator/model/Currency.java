package calc.currency.com.currencycalculator.model;

import android.database.Cursor;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Objects;

import calc.currency.com.currencycalculator.database.DbConstants;

@Root(name = "Valute")
public class Currency {

    @Attribute(name = "ID")
    private String id;

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

    public Currency(Cursor cursor){
        int idIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_ID);
        int numCodeIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_NUM_CODE);
        int charCodeIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_CHAR_CODE);
        int nominalIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_NOMINAL);
        int nameIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_NAME);
        int valueIndex = cursor.getColumnIndex(DbConstants.CurrencyTable.COLUMN_VALUE);

        this.id = cursor.getString(idIndex);
        this.numCode = cursor.getInt(numCodeIndex);
        this.charCode = cursor.getString(charCodeIndex);
        this.nominal = cursor.getInt(nominalIndex);
        this.name = cursor.getString(nameIndex);
        this.value = cursor.getString(valueIndex);


    }

    public Currency(String id, int numCode, int nominal, String name, String value) {
        this.id = id;
        this.numCode = numCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                Objects.equals(getId(), currency.getId()) &&
                Objects.equals(getCharCode(), currency.getCharCode()) &&
                Objects.equals(getName(), currency.getName()) &&
                Objects.equals(getValue(), currency.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumCode(), getCharCode(), getNominal(), getName(), getValue());
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                ", numCode=" + numCode +
                ", charCode='" + charCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

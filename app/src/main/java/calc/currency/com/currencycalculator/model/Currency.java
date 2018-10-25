package calc.currency.com.currencycalculator.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Valute")
public class Currency {

    @Attribute(name = "ID" , required = false)
    private String id;

    @Element(name = "NumCode", required = false)
    private int numCode;

    @Element(name = "CharCode")
    private String charCode;

    @Element(name = "Nominal", required = false)
    private int nominal;

    @Element(name = "Name", required = false)
    private String name;

    @Element(name = "Value", required = false)
    private String value;

    public Currency() {
        super();
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

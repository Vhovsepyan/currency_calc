package calc.currency.com.currencycalculator.example;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class SimpleXMLDemo {
    @Element
    private String text;

    @Attribute
    private int index;

    public SimpleXMLDemo() {
        super();
    }

    public SimpleXMLDemo(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public String getMessage() {
        return text;
    }

    public int getId() {
        return index;
    }

    @Override
    public String toString() {
        return "SimpleXMLDemo{" +
                "text='" + text + '\'' +
                ", index=" + index +
                '}';
    }
}

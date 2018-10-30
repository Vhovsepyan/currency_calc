package calc.currency.com.currencycalculator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import calc.currency.com.currencycalculator.R;
import calc.currency.com.currencycalculator.model.Currency;

public class CurrencyAdapter extends ArrayAdapter {
    private final LayoutInflater inflater;
    private List<Currency> currencies;

    public CurrencyAdapter(Context context, int resource, @NonNull List<Currency> currencies) {
        super(context, resource);
        inflater = LayoutInflater.from(context);
        this.currencies = currencies;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder view = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.currency_adapter_item, parent, false);
            view = new ViewHolder();
            view.charCode = convertView.findViewById(R.id.currency_char_code);
            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }
        view.charCode.setText(currencies.get(position).getCharCode());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return currencies.size();
    }

    @Override
    public Object getItem(int position) {
        return currencies.get(position);
    }

    private static class ViewHolder {
        public TextView charCode;

    }
}
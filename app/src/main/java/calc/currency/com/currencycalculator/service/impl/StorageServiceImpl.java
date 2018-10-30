package calc.currency.com.currencycalculator.service.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import calc.currency.com.currencycalculator.database.DbConstants;
import calc.currency.com.currencycalculator.database.DbHelper;
import calc.currency.com.currencycalculator.model.Currency;
import calc.currency.com.currencycalculator.service.StorageService;

public class StorageServiceImpl implements StorageService {

    private static final String SELECTION_BY_CURRENCY_ID = DbConstants.CurrencyTable.COLUMN_CURRENCY_ID + " = ?";
    private final DbHelper dbHelper;

    public StorageServiceImpl(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void insertOrUpdateCurrency(Currency currency) {
        Currency currencyDb = getCurrencyById(currency.getCurrencyId());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = getCurrencyContentValues(currency);
        if (currencyDb == null) {
            db.insert(DbConstants.CurrencyTable.TABLE_NAME, null, contentValues);
        } else {
            db.update(DbConstants.CurrencyTable.TABLE_NAME, contentValues, DbConstants.CurrencyTable.COLUMN_CURRENCY_ID + "= ?", new String[]{currency.getCurrencyId()});
        }
    }

    @Override
    public Currency getCurrencyById(String id) {
        Currency currency = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] selectionArgs = {id};
        Cursor cursor = null;
        try {
            cursor = db.query(
                    DbConstants.CurrencyTable.TABLE_NAME,
                    null,
                    SELECTION_BY_CURRENCY_ID,
                    selectionArgs,
                    null,
                    null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                currency = new Currency(cursor);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return currency;
    }

    @Override
    public Currency getCurrencyByCharCode(String charCode) {
        Currency currency = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DbConstants.CurrencyTable.COLUMN_CHAR_CODE + " = ?";
        String[] selectionArgs = {charCode.toUpperCase()};
        Cursor cursor = null;
        try {
            cursor = db.query(
                    DbConstants.CurrencyTable.TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                currency = new Currency(cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return currency;
    }

    @Override
    public List<Currency> getAllCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(
                    DbConstants.CurrencyTable.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);

            while (cursor != null && cursor.moveToNext()) {
                Currency currency = new Currency(cursor);
                currencies.add(currency);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return currencies;
    }

    private ContentValues getCurrencyContentValues(Currency currency) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.CurrencyTable.COLUMN_CURRENCY_ID, currency.getCurrencyId());
        contentValues.put(DbConstants.CurrencyTable.COLUMN_CHAR_CODE, currency.getCharCode());
        contentValues.put(DbConstants.CurrencyTable.COLUMN_NAME, currency.getName());
        contentValues.put(DbConstants.CurrencyTable.COLUMN_NOMINAL, currency.getNominal());
        contentValues.put(DbConstants.CurrencyTable.COLUMN_NUM_CODE, currency.getNumCode());
        contentValues.put(DbConstants.CurrencyTable.COLUMN_VALUE, currency.getValue());
        return contentValues;
    }
}

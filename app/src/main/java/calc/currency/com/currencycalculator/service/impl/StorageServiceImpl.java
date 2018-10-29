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

    private final DbHelper dbHelper;

    public StorageServiceImpl(DbHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    @Override
    public long inserCurrency(Currency currency) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = getCurrencyContentValues(currency);
        return db.insert(DbConstants.CurrencyTable.TABLE_NAME, null, contentValues);
    }

    @Override
    public Currency getCurrencyById(String id) {
        Currency currency = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DbConstants.CurrencyTable.COLUMN_ID + " = ?";
        String[] selectionArgs = { id };

        Cursor cursor = db.query(
                DbConstants.CurrencyTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor != null ){
            if (cursor.moveToFirst()){
                currency = new Currency(cursor);
            }
            cursor.close();
        }



        return currency;
    }

    @Override
    public Currency getCurrencyByCharCode(String charCode) {
        Currency currency = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DbConstants.CurrencyTable.COLUMN_CHAR_CODE + " = ?";
        String[] selectionArgs = { charCode.toUpperCase() };

        Cursor cursor = db.query(
                DbConstants.CurrencyTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor != null ){
            if (cursor.moveToFirst()){
                currency = new Currency(cursor);
            }
            cursor.close();
        }

        return currency;
    }

    @Override
    public List<Currency> getAllCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DbConstants.CurrencyTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor != null ){
            while (cursor.moveToNext()){
                Currency currency = new Currency(cursor);
                currencies.add(currency);
            }
            cursor.close();
        }

        return currencies;
    }

    private ContentValues getCurrencyContentValues(Currency currency){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.CurrencyTable.COLUMN_ID, currency.getId());
        contentValues.put(DbConstants.CurrencyTable.COLUMN_CHAR_CODE, currency.getCharCode());
        contentValues.put(DbConstants.CurrencyTable.COLUMN_NAME, currency.getName());
        contentValues.put(DbConstants.CurrencyTable.COLUMN_NOMINAL, currency.getNominal());
        contentValues.put(DbConstants.CurrencyTable.COLUMN_NUM_CODE, currency.getNumCode());
        contentValues.put(DbConstants.CurrencyTable.COLUMN_VALUE, currency.getValue());
        return contentValues;
    }
}

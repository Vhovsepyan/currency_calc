package calc.currency.com.currencycalculator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CurrencyApp.db";

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void initDatabase(Context context) {
        Holder.initDatabase(context);
    }

    public static DbHelper getInstance() {
        return Holder.sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createCurrencyTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    private void createCurrencyTable(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + DbConstants.CurrencyTable.TABLE_NAME + " ("
                + DbConstants.CurrencyTable.COLUMN_ID + " INT PRIMARY KEY,"
                + DbConstants.CurrencyTable.COLUMN_CURRENCY_ID + " TEXT,"
                + DbConstants.CurrencyTable.COLUMN_NUM_CODE + " INT,"
                + DbConstants.CurrencyTable.COLUMN_CHAR_CODE + " TEXT,"
                + DbConstants.CurrencyTable.COLUMN_NOMINAL + " INT,"
                + DbConstants.CurrencyTable.COLUMN_NAME + " TEXT,"
                + DbConstants.CurrencyTable.COLUMN_VALUE + " TEXT)";

        db.execSQL(sqlQuery);

    }

    private static class Holder {

        private static DbHelper sInstance;

        private static void initDatabase(Context context) {
            sInstance = new DbHelper(context);
        }
    }
}
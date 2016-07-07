package com.jprarama.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by joshua on 6/7/16.
 */
public class DbHepler extends SQLiteOpenHelper {
    public static final String TAG = DbHepler.class.getName();
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "InventoryApp.db";

    public DbHepler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = DbContract.ProductEntry.CREATE_STATEMENT;
        Log.d(TAG, "Create SQL: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = DbContract.ProductEntry.DROP_STATEMENT;
        Log.d(TAG, "Drop SQL: " + sql);
        db.execSQL(sql);
        onCreate(db);
    }

}

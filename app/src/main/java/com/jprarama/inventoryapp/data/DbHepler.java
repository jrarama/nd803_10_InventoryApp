package com.jprarama.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.jprarama.inventoryapp.model.Product;
import com.jprarama.inventoryapp.data.DbAnotations.Column;
import com.jprarama.inventoryapp.data.DbAnotations.Key;
import com.jprarama.inventoryapp.data.DbAnotations.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshua on 6/7/16.
 */
public class DbHepler extends SQLiteOpenHelper {
    public static final String TAG = DbHepler.class.getName();
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "InventoryApp.db";

    public DbHepler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = getCreateStatement(Product.class);
        Log.d(TAG, "Create SQL: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = getDropStatement(Product.class);
        Log.d(TAG, "Drop SQL: " + sql);
        db.execSQL(sql);
        onCreate(db);
    }

    public static String getCreateStatement(Class<?> clazz) {
        final String format = "CREATE TABLE %s( %s )";
        Table table = clazz.getAnnotation(Table.class);
        String tableName = table != null ? table.name() : clazz.getSimpleName().toLowerCase();

        Field[] fields = clazz.getDeclaredFields();
        List<String> items = new ArrayList<>();
        for (Field field: fields) {
            String sql = getSqlFromField(field);
            if (sql != null) {
                items.add(sql);
            }
        }

        return String.format(format, tableName, TextUtils.join(", ", items));
    }

    private static String getSqlFromField(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column == null) {
            return null;
        }
        final String format = "%s %s %s";
        Key key = field.getAnnotation(Key.class);
        String name = "".equals(column.name()) ? field.getName().toLowerCase() : column.name();
        String keyStr = key == null ? "" : "PRIMARY KEY";
        return String.format(format, name, column.type(), keyStr).trim();
    }

    public static String getDropStatement(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        String tableName = table != null ? table.name() : clazz.getSimpleName().toLowerCase();
        return "DROP TABLE IF EXISTS " + tableName;
    }

}

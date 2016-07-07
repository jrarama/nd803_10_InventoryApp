package com.jprarama.inventoryapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.jprarama.inventoryapp.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshua on 6/7/16.
 */
public final class DbContract {

    private DbContract() {}

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_IMAGE_PATH = "image_path";
        public static final String CREATE_STATEMENT = TextUtils.join(" ", new String[] {
                "CREATE TABLE", TABLE_NAME, "(",
                _ID, "INTEGER PRIMARY KEY,",
                COLUMN_TITLE, "TEXT NOT NULL,",
                COLUMN_PRICE, "FLOAT NOT NULL,",
                COLUMN_QUANTITY, "FLOAT NOT NULL,",
                COLUMN_IMAGE_PATH, "TEXT",
                ")"
            });
        public static final String DROP_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME;

        private static ContentValues getContentValues(Product product) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, product.getTitle());
            values.put(COLUMN_IMAGE_PATH, product.getImagePath());
            values.put(COLUMN_PRICE, product.getPrice());
            values.put(COLUMN_QUANTITY, product.getQuantity());
            return values;
        }

        public static void insert(SQLiteDatabase db, Product product) {
            long id = db.insert(TABLE_NAME, null, getContentValues(product));
            product.setId((int) id);
        }

        public static void update(SQLiteDatabase db, Product product) {
            db.update(TABLE_NAME, getContentValues(product), _ID + " = ?",
                    new String[]{String.valueOf(product.getId())});
        }

        public static Cursor getAll(SQLiteDatabase db) {
            return db.query(TABLE_NAME, null, null, null, null, null, COLUMN_TITLE);
        }

        public static void delete(SQLiteDatabase db, Product product) {
            db.delete(TABLE_NAME, _ID + " = ?",
                    new String[]{String.valueOf(product.getId())});
        }

        public static Product getItem(Cursor cursor) {
            Product product = new Product();
            product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)));
            product.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
            product.setQuantity(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
            product.setImagePath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH)));
            product.setPrice(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
            return product;
        }

    }
}

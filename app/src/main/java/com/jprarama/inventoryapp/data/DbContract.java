package com.jprarama.inventoryapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.jprarama.inventoryapp.model.Product;

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
            return db.query(TABLE_NAME, new String[] {
                    _ID, COLUMN_TITLE, COLUMN_PRICE, COLUMN_QUANTITY, COLUMN_IMAGE_PATH
            }, null, null, null, null, COLUMN_TITLE);
        }

    }
}

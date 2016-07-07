package com.jprarama.inventoryapp.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jprarama.inventoryapp.data.DbAnotations.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joshua on 6/7/16.
 */
public class CursorHelper {

    private Class<?> clazz;
    // (field, column)
    private Map<String, Column> columnMap;
    // (column, field)
    private Map<String, Field> fieldMap;

    public CursorHelper(Class<?> clazz) {
        this.clazz = clazz;
        init();
    }

    /**
     * Map class fieldNames to column info
     */
    private void init() {
        columnMap = new HashMap<>();
        fieldMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columnMap.put(field.getName(), column);
                fieldMap.put(column.name(), field);
            }
        }
    }

    public <T> T getItem(Cursor c, String[] projection) throws IllegalAccessException, InstantiationException {
        T instance = (T) clazz.newInstance();
        if (projection == null) {
            for (Map.Entry<String, Column> entry: columnMap.entrySet()) {
                setPropertyByCursor(c, instance, entry.getValue().name());
            }
        } else {

            for (String field : projection) {
                Column column = columnMap.get(field);
                if (column == null) {
                    continue;
                }
                setPropertyByCursor(c, instance, column.name());
            }
        }

        return instance;
    }

    private void setPropertyByCursor(Cursor c, Object instance, String columnName)
            throws IllegalAccessException {
        int index = c.getColumnIndexOrThrow(columnName);
        Object value = null;
        switch (c.getType(index)) {
            case Cursor.FIELD_TYPE_FLOAT:
                value = c.getFloat(index);
                break;
            case Cursor.FIELD_TYPE_INTEGER:
                value = c.getInt(index);
                break;
            default:
                value = c.getString(index);
                break;
        }
        setProperty(instance, columnName, value);
    }

    private void setProperty(Object o, String columnName, Object value) throws IllegalAccessException {
        Field f = fieldMap.get(columnName);
        f.setAccessible(true);
        f.set(o, value);
        f.setAccessible(false);
    }


}

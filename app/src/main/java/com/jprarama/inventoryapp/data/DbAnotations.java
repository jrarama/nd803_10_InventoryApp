package com.jprarama.inventoryapp.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by joshua on 6/7/16.
 */
public final class DbAnotations {

    private DbAnotations() {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD) //can use in method only.
    public @interface Key {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD) //can use in method only.
    public @interface Column {
        String name() default "";
        String type() default "TEXT";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE) //can use in method only.
    public @interface Table {
        String name();
    }

}

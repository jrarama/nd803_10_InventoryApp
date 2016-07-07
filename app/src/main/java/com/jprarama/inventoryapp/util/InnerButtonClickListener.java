package com.jprarama.inventoryapp.util;

import android.view.View;

/**
 * Created by joshua on 7/7/16.
 */
public interface InnerButtonClickListener<T> {

    void onClicked(View view, T selectedItem);
}

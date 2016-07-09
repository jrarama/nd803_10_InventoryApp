package com.jprarama.inventoryapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.jprarama.inventoryapp.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by joshua on 9/7/16.
 */
public class Utilities {

    private static final String TAG = Utilities.class.getName();

    public static boolean setImageFromPath(Context context, ImageView imageView, String path) {
        if (path == null) {
            imageView.setImageResource(R.drawable.default_product);
            return true;
        }
        try {
            final InputStream imageStream = context.getContentResolver()
                    .openInputStream(Uri.parse(path));
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(selectedImage);
            return true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return false;
    }
}

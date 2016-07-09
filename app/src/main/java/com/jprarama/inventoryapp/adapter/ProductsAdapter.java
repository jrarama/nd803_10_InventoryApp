package com.jprarama.inventoryapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jprarama.inventoryapp.R;
import com.jprarama.inventoryapp.data.DbContract;
import com.jprarama.inventoryapp.model.Product;
import com.jprarama.inventoryapp.util.InnerButtonClickListener;
import com.jprarama.inventoryapp.util.Utilities;

/**
 * Created by joshua on 7/7/16.
 */
public class ProductsAdapter extends CursorAdapter {
    private static final String TAG = ProductsAdapter.class.getName();

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvPrice;
        TextView tvQuantity;
        ImageView image;
        Button btnSell;
    }

    private InnerButtonClickListener<Product> buttonClickListener;

    public ProductsAdapter(Context context, Cursor c, InnerButtonClickListener<Product> buttonClickListener) {
        super(context, c, 0);
        this.buttonClickListener = buttonClickListener;
    }

    @Override
    public View newView(final Context context, final Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder();
        viewHolder.image = (ImageView) view.findViewById(R.id.imageView);
        viewHolder.tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        viewHolder.tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
        viewHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        viewHolder.btnSell = (Button) view.findViewById(R.id.btnSell);
        viewHolder.btnSell.setTag(cursor.getPosition());
        view.setTag(viewHolder);

        viewHolder.btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                cursor.moveToPosition(position);
                Log.d(TAG, "Button clicked: " + position);
                Product p = DbContract.ProductEntry.getItem(cursor);
                buttonClickListener.onClicked(view, p);
            }
        });

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Product product = DbContract.ProductEntry.getItem(cursor);
        viewHolder.tvPrice.setText(String.format(context.getString(R.string.price_format),
                product.getPrice()));
        viewHolder.tvTitle.setText(product.getTitle());
        viewHolder.tvQuantity.setText(String.format(context.getString(R.string.quantity_format),
                product.getQuantity()));
        Utilities.setImageFromPath(context, viewHolder.image, product.getImagePath());

    }
}

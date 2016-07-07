package com.jprarama.inventoryapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jprarama.inventoryapp.R;
import com.jprarama.inventoryapp.adapter.ProductsAdapter;
import com.jprarama.inventoryapp.data.CursorHelper;
import com.jprarama.inventoryapp.data.DbContract;
import com.jprarama.inventoryapp.data.DbHepler;
import com.jprarama.inventoryapp.model.Product;
import com.jprarama.inventoryapp.util.InnerButtonClickListener;

import static com.jprarama.inventoryapp.data.DbContract.ProductEntry;

/**
 * Created by joshua on 6/7/16.
 */
public class ProductsActivity extends AppCompatActivity {

    private static final String TAG = ProductsActivity.class.getName();
    private DbHepler dbHepler;
    private SQLiteDatabase db;

    private TextView tvNoResults;
    private ListView listView;
    private ProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        tvNoResults =  (TextView) findViewById(R.id.tvNoResults);
        listView = (ListView) findViewById(R.id.listView);

        dbHepler = new DbHepler(this);
        db = dbHepler.getWritableDatabase();

        Log.d(TAG, "Getting products");


        loadProducts();
    }

    private void loadProducts() {
        tvNoResults.setVisibility(View.VISIBLE);
        tvNoResults.setText(getString(R.string.loading_products));

        Cursor c = ProductEntry.getAll(db);

        final Activity activity = this;
        adapter = new ProductsAdapter(this, c, new InnerButtonClickListener<Product>() {
            @Override
            public void onClicked(View view, final Product product) {
                showSellDialog(product, new InnerButtonClickListener<Integer>() {
                    @Override
                    public void onClicked(View view, Integer quantity) {
                        product.setQuantity(product.getQuantity() - quantity);
                        DbContract.ProductEntry.update(db, product);
                        loadProducts();

                        String msg = String.format(getString(R.string.sell_notification_format),
                                quantity, product.getTitle());
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        listView.setAdapter(adapter);

        int count = c.getCount();
        if (count == 0) {
            tvNoResults.setText(getString(R.string.no_products));
        } else {
            tvNoResults.setVisibility(View.GONE);
        }
    }

    private void showSellDialog(final Product product, final InnerButtonClickListener<Integer> onClickListener) {
        final EditText tvQuantity = new EditText(this);
        tvQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);

        final Activity activity = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(String.format(getString(R.string.sell_product_title_format), product.getTitle()))
                .setMessage(getString(R.string.set_quantity))
                .setView(tvQuantity)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(getString(R.string.sell_title), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (tvQuantity.getText().length() == 0) {
                            Toast.makeText(activity, getString(R.string.quantity_required), Toast.LENGTH_LONG).show();
                            return;
                        }
                        int qtty = Integer.parseInt(tvQuantity.getText().toString());
                        if (qtty > product.getQuantity()) {
                            Toast.makeText(activity, getString(R.string.not_enough_stocks), Toast.LENGTH_LONG).show();
                        } else {
                            dialogInterface.dismiss();
                            onClickListener.onClicked(tvQuantity, qtty);
                        }
                    }
                });

        builder.show();
    }

    private void addSample() {
        Log.d(TAG, "Inserting products");
        ProductEntry.insert(db, new Product("Apple", "some/path", 100f, 20));
        ProductEntry.insert(db, new Product("Mango", "some/path2", 200f, 30));

        Log.d(TAG, "Getting products");
        Cursor c = ProductEntry.getAll(db);
        CursorHelper helper = new CursorHelper(Product.class);
        if (c.moveToFirst()) {
            do {
                try {
                    Product p = helper.getItem(c, null);
                    Log.d(TAG, p.toString());
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    break;
                }
            } while (c.moveToNext());
        }
    }
}
package com.jprarama.inventoryapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jprarama.inventoryapp.R;
import com.jprarama.inventoryapp.model.Product;

public class AddProductActivity extends AppCompatActivity {

    public static final String PRODUCT_KEY = "product";
    private ImageView imageView;
    private EditText txtTitle;
    private EditText txtQuantity;
    private EditText txtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.imageView);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        txtQuantity = (EditText) findViewById(R.id.txtQuantity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                Product product = getProduct();
                if (product != null) {
                    Intent intent = new Intent();
                    intent.putExtra(PRODUCT_KEY, product);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Product getProduct() {
        String message = null;
        if (txtTitle.getText().length() == 0) {
            message = getString(R.string.product_name_required);
            txtTitle.requestFocus();
        } else if (txtPrice.getText().length() == 0) {
            message = getString(R.string.price_is_required);
            txtPrice.requestFocus();
        }

        float quantity = txtQuantity.getText().length() == 0 ? 0f :
                Float.parseFloat(txtQuantity.getText().toString());

        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return null;
        }

        return new Product(txtTitle.getText().toString(), null,
                Float.parseFloat(txtPrice.getText().toString()),
                quantity);
    }
}

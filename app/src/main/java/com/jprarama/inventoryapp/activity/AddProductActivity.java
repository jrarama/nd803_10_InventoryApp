package com.jprarama.inventoryapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jprarama.inventoryapp.R;
import com.jprarama.inventoryapp.model.Product;
import com.jprarama.inventoryapp.util.Utilities;

public class AddProductActivity extends AppCompatActivity {

    public static final String PRODUCT_KEY = "product";
    private static final int PICK_IMAGE_CODE = 5;
    private ImageView imageView;
    private EditText txtTitle;
    private EditText txtQuantity;
    private EditText txtPrice;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.imageView);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        txtQuantity = (EditText) findViewById(R.id.txtQuantity);
        product = new Product();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setType("image/*");
                startActivityForResult(pickIntent, PICK_IMAGE_CODE);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            product.setImagePath(imageUri.toString());
            Utilities.setImageFromPath(this, imageView, imageUri.toString());
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

        product.setTitle(txtTitle.getText().toString());
        product.setPrice(Float.parseFloat(txtPrice.getText().toString()));
        product.setQuantity(quantity);

        return product;
    }
}

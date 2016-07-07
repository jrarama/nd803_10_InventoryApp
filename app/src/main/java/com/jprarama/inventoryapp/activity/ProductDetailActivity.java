package com.jprarama.inventoryapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jprarama.inventoryapp.R;
import com.jprarama.inventoryapp.model.Product;
import com.jprarama.inventoryapp.util.InnerButtonClickListener;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String TAG = ProductDetailActivity.class.getName();
    public static final String PRODUCT_KEY = "product";
    public static final String AMOUNT_KEY = "amount";
    public static final int DELETE_CODE = 9;
    public static final int SELL_CODE = 8;
    public static final int ADD_CODE = 7;

    private enum ActionType {
        SELL, ADD, ORDER
    }

    private Product product;

    private TextView tvTitle;
    private TextView tvPrice;
    private TextView tvQuantity;
    private ImageView imageView;

    private Button btnAdd;
    private Button btnOrder;
    private Button btnSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        imageView = (ImageView) findViewById(R.id.imageView);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnSell = (Button) findViewById(R.id.btnSell);

        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showModifyAmountDialog(product, ActionType.SELL, new InnerButtonClickListener<Integer>() {
                    @Override
                    public void onClicked(View view, Integer amount) {
                        Intent intent = getIntentData();
                        intent.putExtra(AMOUNT_KEY, amount);
                        setResult(SELL_CODE, intent);
                        finish();
                    }
                });
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showModifyAmountDialog(product, ActionType.ADD, new InnerButtonClickListener<Integer>() {
                    @Override
                    public void onClicked(View view, Integer amount) {
                        Intent intent = getIntentData();
                        intent.putExtra(AMOUNT_KEY, amount);
                        setResult(ADD_CODE, intent);
                        finish();
                    }
                });
            }
        });


        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showModifyAmountDialog(product, ActionType.ORDER, new InnerButtonClickListener<Integer>() {
                    @Override
                    public void onClicked(View view, Integer amount) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        Log.d(TAG, "Sending message");
                        String message = String.format(getString(R.string.order_product_format), product.getQuantity(), product.getTitle());
                        intent.putExtra(Intent.EXTRA_TEXT, message);
                        intent.setType("text/plain");
                        startActivity(intent);
                    }
                });
            }
        });
        handleIntent(getIntent());
    }

    private Intent getIntentData() {
        Intent data = new Intent();
        data.putExtra(PRODUCT_KEY, product);
        return data;
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            product = intent.getParcelableExtra(PRODUCT_KEY);
            Log.d(TAG, product.toString());

            tvPrice.setText(String.format(getString(R.string.price_format),
                    product.getPrice()));
            tvTitle.setText(product.getTitle());
            tvQuantity.setText(String.format(getString(R.string.quantity_format),
                    product.getQuantity()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showDeleteConfirmation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDeleteConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_confirmation))
                .setMessage(String.format(getString(R.string.delete_confirmation_format), product.getTitle()))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.putExtra(PRODUCT_KEY, product);
                        setResult(DELETE_CODE, intent);
                        finish();
                    }
                });

        builder.show();
    }

    private void showModifyAmountDialog(final Product product, final ActionType actionType, final InnerButtonClickListener<Integer> onClickListener) {
        final EditText txtQtty = new EditText(this);
        txtQtty.setInputType(InputType.TYPE_CLASS_NUMBER);

        final Activity activity = this;
        int title = actionType == ActionType.ADD ? R.string.add_product_title_format : actionType == ActionType.SELL ?
                R.string.sell_product_title_format : R.string.order_product_title_format;
        int positiveText = actionType == ActionType.ADD ? R.string.add : actionType == ActionType.SELL ?
                R.string.sell_title : R.string.order;

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(String.format(getString(title), product.getTitle()))
                .setMessage(getString(R.string.set_quantity))
                .setView(txtQtty)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(getString(positiveText), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (txtQtty.getText().length() == 0) {
                            Toast.makeText(activity, getString(R.string.quantity_required), Toast.LENGTH_LONG).show();
                            return;
                        }
                        int qtty = Integer.parseInt(txtQtty.getText().toString());
                        if (actionType == ActionType.ADD || actionType == ActionType.ORDER) {
                            if (qtty <= 0) {
                                Toast.makeText(activity, getString(R.string.greater_than_zero), Toast.LENGTH_LONG).show();
                                return;
                            }
                        } else if (actionType == ActionType.SELL && qtty > product.getQuantity()) {
                            Toast.makeText(activity, getString(R.string.not_enough_stocks), Toast.LENGTH_LONG).show();
                            return;
                        }
                        dialogInterface.dismiss();
                        onClickListener.onClicked(txtQtty, qtty);
                    }
                });

        builder.show();
    }
}

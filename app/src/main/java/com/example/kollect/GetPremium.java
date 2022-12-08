package com.example.kollect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.cancel.OnCancel;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.error.ErrorInfo;
import com.paypal.checkout.error.OnError;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PayPalButton;
import com.paypal.checkout.shipping.OnShippingChange;
import com.paypal.checkout.shipping.ShippingChangeActions;
import com.paypal.checkout.shipping.ShippingChangeData;

import java.util.ArrayList;
import java.util.Objects;

public class GetPremium extends AppCompatActivity {
    private String _USERNAME;
    private long _PREMIUM;
    private boolean isPremium;
    private static final String YOUR_CLIENT_ID = "Aetfa-NyKGfIWgKNR9PA3DeBxPKvSct9-tD392aBVw-S0Frr8kAjiyobjnEfkOMehPF8w1hAS_uo3iLf";
    PayPalButton payPalButton;
    Button btn_back;
    TextView payed;

    DatabaseReference reference;

    //private MySQLiteOpenHelper databaseHelper;
    //PaymentButtonContainer paymentButtonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PayPalCheckout.setConfig(new CheckoutConfig(
                getApplication(),
                YOUR_CLIENT_ID,
                Environment.SANDBOX,
                CurrencyCode.USD,
                UserAction.PAY_NOW

        ));

        reference = FirebaseDatabase.getInstance().getReference("Users");

        Intent intent = getIntent();

        _USERNAME = intent.getStringExtra("user_name");
        _PREMIUM = intent.getLongExtra("premium", 0);
        //databaseHelper = new MySQLiteOpenHelper(this);
        Log.w(_USERNAME, "user name");
        //isPremium = databaseHelper.getPremium(_USERNAME);


        Log.w(String.valueOf(_PREMIUM), "isPremium");

        setContentView(R.layout.activity_get_premium);

        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetPremium.this, Profile.class);
                intent.putExtra("user_name", _USERNAME);
                intent.putExtra("premium", _PREMIUM);
                startActivity(intent);
            }
        });

        payPalButton = findViewById(R.id.payPalButton);
        if (_PREMIUM == 1){
            findViewById(R.id.payPalButton).setClickable(false);
            btn_back.setText("Back");
            payed = findViewById(R.id.textView_Payed);
            payed.setText("You are a premium member already! PayPal has been disabled.");

        }
        //paymentButtonContainer = findViewById(R.id.payment_button_container);
        payPalButton.setup(
                new CreateOrder() {
                    @Override
                    public void create(@NotNull CreateOrderActions createOrderActions) {

                        ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();

                        purchaseUnits.add(
                                new PurchaseUnit.Builder()
                                        .amount(
                                                new Amount.Builder()
                                                        .currencyCode(CurrencyCode.USD)
                                                        .value("10.00")
                                                        .build()
                                        )
                                        .build()
                        );

                        Order order = new Order(
                                OrderIntent.CAPTURE,
                                new AppContext.Builder()
                                        .userAction(UserAction.PAY_NOW)
                                        .build(),
                                purchaseUnits
                        );
                        createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                    }
                },

                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));
                                //databaseHelper.setPremium(_USERNAME, 1);
                                reference.child(_USERNAME).child("premium").setValue(1);
                                // _PREMIUM = 1;
                                Log.w(String.valueOf(_PREMIUM), "PREMIUM STATUS");
                                Toast.makeText(GetPremium.this, "Thank you for your purchase!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(GetPremium.this, Profile.class);
                                intent.putExtra("user_name", _USERNAME);
                                intent.putExtra("premium", _PREMIUM);
                                startActivity(intent);
                            }
                        });
                    }
                },
                new OnShippingChange() {
                    @Override
                    public void onShippingChanged(@NonNull ShippingChangeData shippingChangeData, @NonNull ShippingChangeActions shippingChangeActions) {
                    }
                },

                new OnCancel() {
                    @Override
                    public void onCancel() {

                        Log.d("OnCancel", "Buyer cancelled the PayPal experience.");
                        Toast.makeText(GetPremium.this, "You have canceled payment", Toast.LENGTH_SHORT).show();

                    }

                },
                new OnError() {
                    @Override
                    public void onError(@NotNull ErrorInfo errorInfo) {
                        Log.d("OnError", String.format("Error: %s", errorInfo));
                        Toast.makeText(GetPremium.this, "Error occurred!", Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }
}
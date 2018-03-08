package com.example.rulebreaker.daroadies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondaryPage extends AppCompatActivity {
    @BindView(R.id.km)
    EditText kilometers;
    @BindView(R.id.hours)
    EditText hours;
    int rentrate;
    @BindView(R.id.price)
    Button priceBtn;
    @BindView(R.id.customerName)
    EditText customerName;
    @BindView(R.id.contactNumber)
    EditText contactNumber;
    @BindView(R.id.licence)
    CheckBox licence;
    @BindView(R.id.policy)
    CheckBox policy;
    @BindView(R.id.rentButton)
    Button rentButton;
    String bikeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_page);
        ButterKnife.bind(this);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        bikeName=getIntent().getExtras().getString("BikeName");
        rentrate=getIntent().getExtras().getInt("RentPrice");

    }
    @OnClick(R.id.price)
    public void estimatePrice() {

        if (TextUtils.isEmpty(kilometers.getText().toString())||kilometers.getText().length()==0){
            kilometers.setError("Kilometers cannot be Empty!");
            return;
        }
        if (TextUtils.isEmpty(hours.getText().toString())||hours.getText().length()==0){
            hours.setError("Hours cannot be Empty!");
            return;
        }
        hideSoftKeyboard(SecondaryPage.this);
        int cost=0;
        int km = Integer.parseInt(kilometers.getText().toString());
        int hrs = Integer.parseInt(hours.getText().toString());
        if(hrs<=0||km<=0)
            cost=0;
        else{
            cost=(int)(km*((double)rentrate+(double)(hrs-1)/2));
        }
        priceBtn.setText("Estimated Rent: Rs "+Integer.toString(cost)+"/-");
        return;

    }
    @OnClick(R.id.rentButton)
    public void bookRide(){
        if(TextUtils.isEmpty(customerName.getText().toString()))
        {
            customerName.setError("Name cannot be Empty!");
            return;
        }
        if(TextUtils.isEmpty(contactNumber.getText().toString()))
        {
            contactNumber.setError("Contact Number cannot be Empty!");
            return;
        }
        if(contactNumber.getText().toString().length()>10)
        {
            contactNumber.setError("Number should not contain prefix!");
            return;
        }
        if(contactNumber.getText().toString().length()<10)
        {
            contactNumber.setError("Contact Number not valid!");
            return;
        }
        if(!licence.isChecked())
        {
            Toast.makeText(this,"You need to have a Driving Licence!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!policy.isChecked())
        {
            Toast.makeText(this,"You need to agree to our Privacy Policy!!",Toast.LENGTH_SHORT).show();
            return;
        }
        hideSoftKeyboard(SecondaryPage.this);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Booking Ride");
        intent.putExtra(Intent.EXTRA_TEXT, customerName.getText().toString()+" wants to book ride for "+bikeName+" through Phone Number "+contactNumber.getText().toString());
        intent.setData(Uri.parse("mailto:daroadies@gmail.com"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        customerName.setText("");
        contactNumber.setText("");
        kilometers.setText("");
        hours.setText("");
        licence.setChecked(false);
        policy.setChecked(false);
        startActivity(intent);

    }
    public static void hideSoftKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
    @Override
    public void onBackPressed() {
        hideSoftKeyboard(SecondaryPage.this);
        super.onBackPressed();
    }
}

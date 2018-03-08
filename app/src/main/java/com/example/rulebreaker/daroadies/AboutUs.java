package com.example.rulebreaker.daroadies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUs extends AppCompatActivity {
    @BindView(R.id.aboutUs)
    TextView aboutUs;
    @BindView(R.id.aboutUs2)
    TextView aboutUs2;
    @BindView(R.id.mem1)
    TextView mem1;
    @BindView(R.id.mem2)
    TextView mem2;
    @BindView(R.id.mem3)
    TextView mem3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String text="We are a NIT Jalandhar Startup, aiming to make bike renting a hassle free business. We are well established in Jalandhar city since 2018 and we are on an expansion mode.We have a vision of becoming the pioneer of the Bike Renting market.";
        aboutUs.setText(text);
        String text2="We are a group of young individuals, driven by passion to put DA Roadies on the global map. Seizing opportunities and being awesome is what we do. Always open to suggestions and on a lookout for new challenges, we live by the saying - \"Team work makes the dream work\".";
        aboutUs2.setText(text2);
        mem1.setText("Ankush Sharma\n(Founder-9915995501)");
        mem2.setText("Davinder Gill\n(Co-Founder-8559098024)");
        mem3.setText("Gaurav Jindal\n(Software Developer)");

    }
}

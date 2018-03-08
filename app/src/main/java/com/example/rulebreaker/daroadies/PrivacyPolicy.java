package com.example.rulebreaker.daroadies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrivacyPolicy extends AppCompatActivity {
    @BindView(R.id.privacyPolicy)
    TextView privacyPolicy;
    @BindView(R.id.privacyPolicy2)
    TextView privacyPolicy2;
    @BindView(R.id.privacyPolicy3)
    TextView privacyPolicy3;
    @BindView(R.id.privacyPolicy4)
    TextView privacyPolicy4;
    @BindView(R.id.privacyPolicy5)
    TextView privacyPolicy5;
    @BindView(R.id.privacyPolicy6)
    TextView privacyPolicy6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        ButterKnife.bind(this);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String text="Please read the privacy policy carefully and make sure that if you want to use DA Roadies for the purpose prescribed under including but not limited to provisions of sections 43A, 72 and section 72A of Information Technology Act, 2000. This Privacy Policy is a legally binding document between you and DA Roadies";
        privacyPolicy.setText(text);
        String text2="1) We will collect your personal information such as deiving liscence, aadhar card, but not for any official use.\n2) Our System will associate this information with your activities in course of providing service to you.\n3) We will only collect or share your location with your permission.\n4) We don't allow advertising companies to collect data through our services for ad targeting.\n5) DA Roadies is not responsible for any loss and injury while using our services";
        privacyPolicy2.setText(text2);
        String text3="a) Should have Driving Liscence.\nb) Only that person can drive whose any personal document is with us eg. Aadhar Card.\nc) Members agrees to promptly report to DA Roadies any suspension or relocation of his/her driving liscence or any tickets, citations, convictions related to any traffic violations, but not limited to driving under influence of drugs or alcohol, driving while intoxicated, reckless driving, overspeeding or using phone while driving.";
        privacyPolicy3.setText(text3);
        String text4="a) Speed race or Competition.\nb) Crossing 80km/hr speed.\nc) Carrying out any crime or other illegal activity.\nd) Without wearing Helmet.\ne) Under influence of alcohol and drugs.\nf) Exceeding Seating Capacity of Vehicle.";
        privacyPolicy4.setText(text4);
        String text5="a) Members will be absolutely responsible for any and all losses, damages(direct/indirect) ,costs, charges, fees and expenses incurred by DA Roadies from member's property including but not limited to his/her legal heirs property.\nb) All your traffic violation fines would be payable by you.\nc) For each Additional hour the riding fare is incremented by 0.5 km/hour and the final fare per km would be applicable on the whole journey.";
        privacyPolicy5.setText(text5);
        String text6="a) Reservation is required for potential booking in advance for usage.\nb) Member should carry original Driving Liscence\nc) If you wish to cancel or want to extend the reservation it can be done before expiration of reservation, inorder with reservation charges.\nd) The Vehicle is available for use for extension period if it is not pre-booked by another member.\ne) You will be charged late fee, inconvenience fee, additional fee, legal attorney fees, consultancy fee applicable as per out fee policy.";
        privacyPolicy6.setText(text6);

    }
}

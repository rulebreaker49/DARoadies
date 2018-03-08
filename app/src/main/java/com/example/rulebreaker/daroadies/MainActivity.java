package com.example.rulebreaker.daroadies;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AdView mAdView;
    public String link;
    public ArrayList<String> bikeName=new ArrayList<>();
    public ArrayList<String> posterPath=new ArrayList<>();
    public ArrayList<Integer> bikeId=new ArrayList<>();
    public ArrayList<Integer> rentPrice=new ArrayList<>();
    public ArrayList<Boolean> available=new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ca-app-pub-3555311007133703/4558849230
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        link=getIntent().getStringExtra("Link");
        new ProgressTask().execute();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                    @Override
                                                    public void onRefresh() {
                                                        if(haveNetworkConnection()==false)
                                                        {
                                                            Toast.makeText(MainActivity.this,"No Internet Connectivity",Toast.LENGTH_SHORT).show();
                                                            swipeRefreshLayout.setRefreshing(false);
                                                        }
                                                        else
                                                        {
                                                            new ProgressTask().execute();
                                                        }
                                                    }
                                                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        Intent intent;
        switch (id)
        {
            case R.id.policyPage:
                intent=new Intent(this,PrivacyPolicy.class);
                startActivity(intent);
                break;
            case R.id.aboutUs:
                intent=new Intent(this,AboutUs.class);
                startActivity(intent);
                break;
            case R.id.feedback:
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                intent.putExtra(Intent.EXTRA_TEXT,"Write Your Feedback Here: ");
                intent.setData(Uri.parse("mailto:daroadies@gmail.com"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
        return true;
    }
    public class ProgressTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
           if(haveNetworkConnection()==true)
            {
                try {
                    URL url= new URL(link);
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    InputStream stream = connection.getInputStream();
                    String temp= IOUtils.toString(stream, "UTF-8");
                    JSONObject parentObject=new JSONObject(temp);
                    JSONArray parentArray=parentObject.getJSONArray("results");
                    int len=parentObject.getInt("total_results");
                    JSONObject childObject;
                    bikeName.clear();
                    bikeId.clear();
                    rentPrice.clear();
                    posterPath.clear();
                    for(int i=0;i<len;i++){
                        childObject=parentArray.getJSONObject(i);
                        bikeName.add(childObject.getString("name"));
                        bikeId.add(childObject.getInt("id"));
                        rentPrice.add(childObject.getInt("price"));
                        posterPath.add(childObject.getString("poster_image"));
                        available.add(childObject.getBoolean("flag"));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                Toast.makeText(MainActivity.this,"Internet Error",Toast.LENGTH_LONG).show();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {

            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            swipeRefreshLayout.setRefreshing(false);
            VehicleAdapter adapter=new VehicleAdapter(MainActivity.this,bikeName,posterPath,bikeId,rentPrice,available);
            mRecyclerView.setAdapter(adapter);
        }
    }
    private boolean haveNetworkConnection() {
        {
            boolean haveConnectedWifi = false;
            boolean haveConnectedMobile = false;

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
            return haveConnectedWifi || haveConnectedMobile;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Please Confirm!");
        builder.setMessage("Are you sure you want to exit?");

        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mAlertDialog = builder.create();
        mAlertDialog.show();
        Button nbutton = mAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.BLACK);

    }
}

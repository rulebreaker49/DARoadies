package com.example.rulebreaker.daroadies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPage extends AppCompatActivity {

    String link;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean checkInternet=this.haveNetworkConnection();
        if(checkInternet==true) {

            setContentView(R.layout.start_page);
            ButterKnife.bind(this);
            new ProgressTask().execute();
        }
        else {
            setContentView(R.layout.no_internet_connectivity);
            Button button=(Button)findViewById(R.id.retry);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(haveNetworkConnection()==true){
                        Intent intent=new Intent(StartPage.this,StartPage.class);
                        finish();
                        if(flag==0)
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(StartPage.this,"No Internet Connectivity",Toast.LENGTH_SHORT).show();
                }
            });
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



    public class ProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... params) {
            int cnt=0;
            while(link==null)
            if (haveNetworkConnection() == true) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.child("link").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        link = dataSnapshot.getValue(String.class);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        link = null;
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void params) {
            if (haveNetworkConnection() == true) {
                Intent intent = new Intent(StartPage.this, MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("Link",link);
                intent.putExtras(bundle);
                progressBar.setVisibility(View.GONE);
                if(flag==0)
                startActivity(intent);
            }
            else{
                Toast.makeText(StartPage.this,"Internet Error",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(StartPage.this,StartPage.class);
                finish();
                if(flag==0)
                startActivity(intent);
            }
        }
    }
    @Override
    protected void onResume() {
        if(haveNetworkConnection())
        {
            flag=0;
            new ProgressTask().execute();

        }
        super.onResume();
        }

    @Override
    protected void onPause() {
        if(haveNetworkConnection())
        flag=1;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(haveNetworkConnection())
        flag=1;
        super.onDestroy();
    }
}


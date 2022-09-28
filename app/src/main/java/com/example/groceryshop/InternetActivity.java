package com.example.groceryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class InternetActivity extends AppCompatActivity {
    Button retry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        retry=findViewById(R.id.continueid1);
        ConnectivityManager connectivityManager = (ConnectivityManager)InternetActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ninfo = connectivityManager.getActiveNetworkInfo();


        if(ninfo!=null && ninfo.isConnected())
        {
            Intent intent=new Intent(InternetActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
          //  Toasty.success(, "Internet Check ...", Toast.LENGTH_SHORT, true).show();
        }
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager1 = (ConnectivityManager)InternetActivity.this
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ninfo1 = connectivityManager1.getActiveNetworkInfo();

                if(ninfo1!=null && ninfo1.isConnected())
                {
                    Intent intent=new Intent(InternetActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                      Toasty.success(InternetActivity.this, "Internet Check ...", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }
}
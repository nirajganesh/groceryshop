package com.example.groceryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    CircleImageView circleImageView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        circleImageView=findViewById(R.id.profile_image);
        Glide.with(ProfileActivity.this).load("https://firebasestorage.googleapis.com/v0/b/publicvideo-6b42f.appspot.com/o/Wallpaper%2F1589871714898.jpg?alt=media&token=56a9736d-6183-4c73-bf31-dd2907b2ea8d").into(circleImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_remenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(ProfileActivity.this,"click",Toast.LENGTH_LONG).show();

        if(item.getItemId()==R.id.createid)
        {
            Intent intent=new Intent(ProfileActivity.this,ProfileUpdateActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
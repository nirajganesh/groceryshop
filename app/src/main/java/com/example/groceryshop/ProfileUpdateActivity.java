package com.example.groceryshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUpdateActivity extends AppCompatActivity {
    CircleImageView circleImageView;
    Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        circleImageView=findViewById(R.id.profile_image);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Glide.with(ProfileUpdateActivity.this).load("https://firebasestorage.googleapis.com/v0/b/publicvideo-6b42f.appspot.com/o/Wallpaper%2F1589871714898.jpg?alt=media&token=56a9736d-6183-4c73-bf31-dd2907b2ea8d").into(circleImageView);
    }
}
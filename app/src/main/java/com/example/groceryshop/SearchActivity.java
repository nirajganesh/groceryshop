package com.example.groceryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;



public class SearchActivity extends AppCompatActivity   {
     ImageButton crossbtn;
     ImageButton back;
     EditText inputedit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputedit=findViewById(R.id.editid);
        crossbtn=findViewById(R.id.crossid);
        back=findViewById(R.id.backid);
        inputedit.setFocusable(true);
        inputedit.setShowSoftInputOnFocus(true);
        inputedit.requestFocus();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        inputedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(SearchActivity.this,"click",Toast.LENGTH_LONG).show();
            }
        });

        inputedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.equals(""))
                {
                    crossbtn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.equals(""))
                {
                    crossbtn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.equals(""))
                {
                    crossbtn.setVisibility(View.INVISIBLE);
                }
                else
                {
                    crossbtn.setVisibility(View.VISIBLE);
                }

            }
        });
        crossbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputedit.setText("");
                crossbtn.setVisibility(View.INVISIBLE);
            }
        });
    }


}
package com.example.groceryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class VerificationActivity extends AppCompatActivity {
    String nameintent,phoneintent,emailintent,passintent,codeintent;
    EditText inputotp1,inputotp2,inputotp3,inputotp4,inputotp5,inputotp6;
    Button submitbtn;
    String  string1,string2,string3,string4,string5,string6;
    SharedPreferences sharedPreferences;
    private final static String SHARED_PREF = "mypref";
    private final static String MY_NAME = "name";
    private final static String MY_EMAIL = "email";
    private final static String MY_PASSWORD = "password";
    private final static String MY_PHONE = "phone";
    private final static String MY_FLAG = "flag";
    String sharename, sharemail, flag;
    String url="https://nirajganeshphp.000webhostapp.com/user.php";
    StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        sharedPreferences = this.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        inputotp1=findViewById(R.id.otp1);
        inputotp2=findViewById(R.id.otp2);
        inputotp3=findViewById(R.id.otp3);
        inputotp4=findViewById(R.id.otp4);
        inputotp5=findViewById(R.id.otp5);
        inputotp6=findViewById(R.id.otp6);
        submitbtn=findViewById(R.id.submitid);

        nameintent=getIntent().getStringExtra("name").toString();
        phoneintent=getIntent().getStringExtra("phone");
        emailintent=getIntent().getStringExtra("email");
        passintent=getIntent().getStringExtra("pass");
        codeintent=getIntent().getStringExtra("key");

        inputotp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  if(!s.toString().trim().isEmpty())
                  inputotp2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputotp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                    inputotp3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputotp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                    inputotp4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputotp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                    inputotp5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputotp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                    inputotp6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string1=inputotp1.getText().toString();
                string2=inputotp2.getText().toString();
                string3=inputotp3.getText().toString();
                string4=inputotp4.getText().toString();
                string5=inputotp5.getText().toString();
                string6=inputotp6.getText().toString();

                if(codeintent.equals(string1+string2+string3+string4+string5+string6))
                {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(MY_NAME,nameintent);
                    editor.putString(MY_EMAIL,emailintent);
                    editor.putString(MY_PASSWORD,passintent);
                    editor.putString(MY_PHONE,phoneintent);
                    editor.putString(MY_FLAG,"in");
                    editor.commit();


                    request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("name", nameintent);
                            params.put("email", emailintent);
                            params.put("password", passintent);
                            params.put("phone", phoneintent);
                            return params;
                        }
                    };

                    Intent intent=new Intent(VerificationActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });










    }
}
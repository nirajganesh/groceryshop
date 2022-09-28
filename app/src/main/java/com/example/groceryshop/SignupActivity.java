package com.example.groceryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SignupActivity extends AppCompatActivity {
    Button continuebtn;
    EditText inputname, inputphone, inputemail, inputpass;
    PhoneAuthProvider.ForceResendingToken token1;
    String verificationid;
    TextView textView;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    private final static String SHARED_PREF = "mypref";
    private final static String MY_NAME = "name";
    private final static String MY_EMAIL = "email";
    private final static String MY_FLAG = "flag";
    String sharename, sharemail, flag;
    String phone,name,email,pass;
    int randomnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textView = findViewById(R.id.codeid);
        sharedPreferences = this.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        sharename = sharedPreferences.getString(MY_NAME, null);
        sharemail = sharedPreferences.getString(MY_EMAIL, null);
        flag = sharedPreferences.getString(MY_FLAG, null);

        if (flag != null) {
            Intent intent = new Intent(SignupActivity.this, ProfileActivity.class);
            startActivity(intent);
        }

        continuebtn = findViewById(R.id.continueid);
        inputname = findViewById(R.id.nameeditid);
        inputphone = findViewById(R.id.phoneditid);
        inputemail = findViewById(R.id.emaileditid);
        inputpass = findViewById(R.id.passwordid);
        mAuth = FirebaseAuth.getInstance();
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(SignupActivity.this, "Please wait...", Toasty.LENGTH_SHORT).show();
                name = inputname.getText().toString();
                phone = inputphone.getText().toString().trim();
                email = inputemail.getText().toString();
                pass = inputpass.getText().toString();

                sendVerificationCode(phone);

//                try {
//                    // Construct data
//                    String apiKey = "apikey=" + "gjZ9mrt3An0-gVCq6snhIFPzeI6m8iL9uzQgD8YNVO";
//                    String message = "&message=" + "This is your otp";
//                    String sender = "&sender=" + "TXTLCL";
//                    String numbers = "&numbers="+"917999107995";
//
//                    // Send data
//                    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
//                    String data = apiKey + numbers + message + sender;
//                    conn.setDoOutput(true);
//                    conn.setRequestMethod("POST");
//                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
//
//                    conn.getOutputStream().write(data.getBytes("UTF_8"));
//                 //   Toast.makeText(SignupActivity.this,"line..",Toast.LENGTH_LONG).show();
//                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                    final StringBuffer stringBuffer = new StringBuffer();
//                    String line;
//                    while ((line = rd.readLine()) != null) {
//                        stringBuffer.append(line);
//                        Toast.makeText(SignupActivity.this,"this message are:"+line,Toast.LENGTH_LONG).show();
//                    }
//                    rd.close();
//
//
//                    //   return stringBuffer.toString();
//                } catch (Exception e) {
//                    System.out.println("Error SMS "+e);
//                    //  return "Error "+e;
//                    Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                }


//                try {
//                    // Construct data
//                    String apiKey = "apikey=" + URLEncoder.encode("gjZ9mrt3An0-gVCq6snhIFPzeI6m8iL9uzQgD8YNVO", "UTF-8");
//                  //  Random random=new Random();
//                 //   randomnumber=random.nextInt(999999);
//                    String message = "&message=" + URLEncoder.encode("This is your message", "UTF-8");
//                    String sender = "&sender=" + URLEncoder.encode("nirajganesh", "UTF-8");
//                    String numbers = "&numbers=" + URLEncoder.encode("917999107995", "UTF-8");
//
//                    // Send data
//                    String data = "https://api.textlocal.in/send/?" + apiKey + numbers + message + sender;
//                    URL url = new URL(data);
//                    final URLConnection conn = url.openConnection();
//                    conn.setDoOutput(true);
//
//                    // Get the response
//                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                    Toast.makeText(SignupActivity.this,"send..",Toast.LENGTH_LONG).show();
//                    String line;
//                    String sResult="";
//                    while ((line = rd.readLine()) != null) {
//                        // Process line...
//                        sResult=sResult+line+" ";
//                    }
//                    rd.close();
//                    Toast.makeText(SignupActivity.this,"Otp send successfull...",Toast.LENGTH_LONG).show();
//                    Intent intent=new Intent(SignupActivity.this,VerificationActivity.class);
//                    intent.putExtra("name",name);
//                    intent.putExtra("pass",pass);
//                    intent.putExtra("phone",phone);
//                    intent.putExtra("email",email);
//                    intent.putExtra("key",randomnumber);
//                   startActivity(intent);
//
//                //    return sResult;
//                } catch (Exception e) {
//                    System.out.println("Error SMS "+e);
//                    Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                 //   return "Error "+e;
//                }





                //              sendVerificationCode(phone);
//                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful())
//                        {
//                            Toast.makeText(SignupActivity.this,"Successfull sign in",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });


            }
        });
    //    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
    //    StrictMode.setThreadPolicy(policy);


    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 0: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage("+91"+phone, null, "first message", null, null);
//                    Toast.makeText(getApplicationContext(), "SMS sent.",
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//        }
//    }
    private void sendVerificationCode(String phone) {
       PhoneAuthProvider.getInstance().verifyPhoneNumber("+917999107995", 60, TimeUnit.SECONDS
               , SignupActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                   @Override
                   public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                       String code=phoneAuthCredential.getSmsCode();
                       if(code!=null)
                       {
                           textView.setText(code);
                           //    VerifyVerificationCode(code);

                       }
                   }

                   @Override
                   public void onVerificationFailed(@NonNull FirebaseException e) {
                       Toast.makeText(SignupActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                   }

                   @Override
                   public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                       super.onCodeSent(s, forceResendingToken);
                       verificationid=s;
                   }
               });
    }
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//
//        @Override
//        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//            String code=phoneAuthCredential.getSmsCode();
//            if(code!=null)
//            {
//                textView.setText(code);
//            //    VerifyVerificationCode(code);
//
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(@NonNull FirebaseException e) {
//                Toast.makeText(SignupActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            verificationid=s;
//        }
//    };
//    private void VerifyVerificationCode(String code)
//    {
//        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationid,code);
//    //    FirebaseAuth.getInstance().getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
//        signInWithPhoneAuthCredential(credential);
//    }
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                         //   Log.d(TAG, "signInWithCredential:success");
//
//                            FirebaseUser user = task.getResult().getUser();
//                            Toast.makeText(SignupActivity.this,"success",Toast.LENGTH_LONG).show();
//
//                            // ...
//                        } else {
//                           // Sign in failed, display a message and update the UI
//                      //      Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                            }
//                        }
//                    }
//               });
//    }


//    private void sendVerificationCode(String mobile) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+91" + mobile,                 //phoneNo that is given by user
//                60,                             //Timeout Duration
//                TimeUnit.SECONDS,                   //Unit of Timeout
//                TaskExecutors.MAIN_THREAD,          //Work done on main Thread
//                mCallbacks);                       // OnVerificationStateChangedCallbacks
//    }

//            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                @Override
//                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                    //Getting the code sent by SMS
//                    String code = phoneAuthCredential.getSmsCode();
//
//
//                    if (code != null) {
//                        textView.setText(code);
//                        //verifying the code
//                        verifyVerificationCode(code);
//                    }
//                }
//
//                @Override
//                public void onVerificationFailed(FirebaseException e) {
//                    Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//                }
//
//                //when the code is generated then this method will receive the code.
//                @Override
//                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
////                super.onCodeSent(s, forceResendingToken);
//                    //storing the verification id that is sent to the user
//                    verificationid = s;
//                }
//            };
//
//    private void verifyVerificationCode(String code) {
//        //creating the credential
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
//        signInWithPhoneAuthCredential(credential);
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(SignupActivity.this,
//                        new OnCompleteListener<AuthResult>() {
//
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    //verification successful we will start the profile activity
//                                    Intent intent = new Intent(SignupActivity.this, ProfileActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
//
//                                } else {
//
//
//
//                                    String message = "Somthing is wrong, we will fix it soon...";
//
//                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                        message = "Invalid code entered...";
//                                    }
//                                    Toast.makeText(SignupActivity.this,message,Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//    }

}
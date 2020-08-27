package com.prodev.moringaalumni;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    //views


    EditText mEmailEt, mPasswordEt;
    TextView notHaveAccntTv;
    Button mLoginBtn;





    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Actionbar and its title
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Login");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mEmailEt = findViewById(R.id.emailEt);
        mPasswordEt = findViewById(R.id.passwordEt);
        notHaveAccntTv = findViewById(R.id.nothave_accountTv);
        mLoginBtn = findViewById(R.id.loginBtn);

        //login Button click
        mLoginBtn.setOnClickListener((new View.OnClickListener(){
            public void onClick(View v){
                //input data
                String email = mEmailEt.getText().toString();
                String passw =mPasswordEt.getText().toString().trim();
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //invalid email_pattern set error;
                   mEmailEt.setError("Invalid Email");
                   mEmailEt.setFocusable(true);

                }
                else{
                    //valid email
                    loginUser(email, passw);
                }

                startActivity (new Intent(LoginActivity.this, RegisterActivity.class));

            }

        }));

    }

    private void loginUser(String email, String passw) {
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed(); //go to previous activity
        return super.onSupportNavigateUp();
    }
}
package com.cours5.android.androidcours5.loginSignUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cours5.android.androidcours5.R;

public class SignUpOrLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_or_login);
        setListener();
    }

    private void setListener(){
        findViewById(R.id.button_goToLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToLoginActivity();
            }
        });
        findViewById(R.id.button_goToSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentToSignUpActivity();
            }
        });
    }

    private void sentToSignUpActivity(){
        Intent sendToSignUp = new Intent(this, SignUpActivity.class);
        startActivity(sendToSignUp);
    }

    private void sendToLoginActivity(){
        Intent sendToLogin = new Intent(this, LoginActivity.class);
        startActivity(sendToLogin);
    }
}

package com.cours5.android.androidcours5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        setListener();
    }

    private void setListener(){
        findViewById(R.id.button_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void signUpUser(){
        EditText userEmail = findViewById(R.id.editText_signUpEmail);
        EditText userPassword = findViewById(R.id.editText_signUpPassword);
        EditText userPasswordConfirm = findViewById(R.id.editText_signUpPasswordConfirm);

        /*
        if(userPassword.getText().toString() != userPasswordConfirm.getText().toString()){
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show();
            return;
        }
        */

        auth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendToMainActivity();
                }else{
                    Toast.makeText(getApplicationContext(), "Failed to sign up", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void sendToMainActivity(){
        Intent sendToLogin = new Intent(this, LoginActivity.class);
        startActivity(sendToLogin);
    }
}

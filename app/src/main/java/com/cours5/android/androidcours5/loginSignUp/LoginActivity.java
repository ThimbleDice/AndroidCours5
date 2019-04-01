package com.cours5.android.androidcours5.loginSignUp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cours5.android.androidcours5.MainActivity;
import com.cours5.android.androidcours5.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        setListener();
    }

    private void setListener(){
        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void signUpUser(){
        EditText userEmail = findViewById(R.id.editText_loginEmail);
        EditText userPassword = findViewById(R.id.editText_loginPassword);

        auth.signInWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendToMainActivity();
                }else{
                    Toast.makeText(getApplicationContext(), "Failed to login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendToMainActivity(){
        Intent sendToSignUp = new Intent(this, MainActivity.class);
        startActivity(sendToSignUp);
    }
}

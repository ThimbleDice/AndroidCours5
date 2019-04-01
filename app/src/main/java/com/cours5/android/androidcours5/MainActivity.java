package com.cours5.android.androidcours5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cours5.android.androidcours5.loginSignUp.SignUpOrLoginActivity;
import com.cours5.android.androidcours5.notification.NotificationService;
import com.cours5.android.androidcours5.notification.model.ImportantMessageModel;
import com.cours5.android.androidcours5.notification.model.messageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        setListener();
    }

    private void startService(){
        Intent serviceIntent = new Intent(this, NotificationService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    protected void setListener(){
        findViewById(R.id.button_sendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        findViewById(R.id.button_sendImportantMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImportantMessage();
            }
        });

        findViewById(R.id.button_logOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void sendMessage(){
        EditText editTextMessage = findViewById(R.id.editText_message);
        messageModel message = new messageModel(editTextMessage.getText().toString(), auth.getCurrentUser().getEmail());
        database.collection("Notification").add(message).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendImportantMessage(){
        EditText editTextMessage = findViewById(R.id.editText_message);
        ImportantMessageModel message = new ImportantMessageModel(editTextMessage.getText().toString(), auth.getCurrentUser().getEmail());
        database.collection("NotificationImportant").add(message).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Important message sent", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void signOut(){
        auth.signOut();
        updateUI(auth.getCurrentUser());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    private  void updateUI(FirebaseUser currentUser){
        if(currentUser == null){
            sendUserToSignUpOrLoginActivity();
        }else{
            startService();
        }
    }

    private void sendUserToSignUpOrLoginActivity(){
        Intent sendToSignUpOrLogin = new Intent(this, SignUpOrLoginActivity.class);
        startActivity(sendToSignUpOrLogin);
    }
}

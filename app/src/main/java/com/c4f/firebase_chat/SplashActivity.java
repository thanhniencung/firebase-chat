package com.c4f.firebase_chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.c4f.firebase_chat.signin.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                   startActivity(intent);
                   finish();
               }
           }, 3000);
        } else {
            Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
            startActivity(intent);
        }
    }
}

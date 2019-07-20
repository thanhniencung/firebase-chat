package com.c4f.firebase_chat.signin;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.c4f.firebase_chat.MainActivity;
import com.c4f.firebase_chat.R;
import com.c4f.firebase_chat.signup.SignUpActivity;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private SignInViewModel signInViewModel;

    private EditText editEmail;
    private EditText editPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.signin_activity);

        initView();

        signInViewModel = SignInViewModel.of(this);

        onSignInResponse();
        findViewById(R.id.signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();



                signInViewModel.signIn(email, password);
            }
        });
    }

    private void onSignInResponse() {
        signInViewModel.getSignInResponse().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser user) {
                if (user != null) {
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                }
            }
        });
    }

    void initView() {
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);

        TextView txtTitleScreen = findViewById(R.id.txtTitleScreen);
        Typeface customFont = Typeface.createFromAsset(getAssets(),  "fonts/FasterOne-Regular.ttf");
        txtTitleScreen.setTypeface(customFont);

        TextView txtSignUp = findViewById(R.id.txtSignUp);
        txtSignUp.setPaintFlags(txtSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}

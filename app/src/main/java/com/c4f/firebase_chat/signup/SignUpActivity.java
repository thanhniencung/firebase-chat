package com.c4f.firebase_chat.signup;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.c4f.firebase_chat.R;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel signUpViewModel;

    private EditText editDisplayName;
    private EditText editEmail;
    private EditText editPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.signup_activity);

        initView();

        signUpViewModel = SignUpViewModel.of(this);

        onSignUpResponse();
        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayName = editDisplayName.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                signUpViewModel.signUp(displayName, email, password);
            }
        });
    }

    void onSignUpResponse() {
        signUpViewModel.getSignupResponse().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser user) {
                if (user != null) {
                    /*Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();*/
                }
            }
        });
    }

    void initView() {
        editDisplayName = findViewById(R.id.displayName);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);

        TextView txtTitleScreen = findViewById(R.id.txtTitleScreen);
        Typeface customFont = Typeface.createFromAsset(getAssets(),  "fonts/FasterOne-Regular.ttf");
        txtTitleScreen.setTypeface(customFont);
    }
}

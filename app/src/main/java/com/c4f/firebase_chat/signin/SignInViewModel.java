package com.c4f.firebase_chat.signin;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInViewModel extends ViewModel {
    private FirebaseAuth auth;
    private MutableLiveData<FirebaseUser> signInResponse = new MutableLiveData<>();


    public SignInViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public static SignInViewModel of(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(SignInViewModel.class);
    }

    public MutableLiveData<FirebaseUser> getSignInResponse() {
        return signInResponse;
    }

    void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            signInResponse.setValue(user);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signInResponse.setValue(null);
                    }
                });
    }
}

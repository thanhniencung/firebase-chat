package com.c4f.firebase_chat.signup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.c4f.firebase_chat.firebase.NodeCallback;
import com.c4f.firebase_chat.firebase.NodeManager;
import com.c4f.firebase_chat.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

public class SignUpViewModel extends ViewModel {
    private FirebaseAuth auth;
    private MutableLiveData<FirebaseUser> signUpResponse = new MutableLiveData<>();

    public SignUpViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public static SignUpViewModel of(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(SignUpViewModel.class);
    }

    public MutableLiveData<FirebaseUser> getSignupResponse() {
        return signUpResponse;
    }

    //https://firebase.google.com/docs/auth/android/password-auth
    void signUp(final String displayName, final String email, String password) {
        // Hash.md5(password)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // store user to users node
                            User user = new User(getRandomNumberInRange(1, 100000), displayName, email);
                            NodeManager.getInstance().writeUser(auth.getCurrentUser().getUid(),
                                    user, new NodeCallback<User>() {
                                @Override
                                public void onData(User value) {
                                    signUpResponse.setValue(auth.getCurrentUser());
                                }

                                @Override
                                public void onError(String message) {
                                    signUpResponse.setValue(null);
                                }
                            });

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signUpResponse.setValue(null);
                    }
                });
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    // validation
    boolean validateFields(String displayName, String email, String password) {
        return true;
    }
}

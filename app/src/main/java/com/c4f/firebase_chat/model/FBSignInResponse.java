package com.c4f.firebase_chat.model;

import com.google.firebase.auth.FirebaseUser;

public class FBSignInResponse {
    private FirebaseUser user;
    private String error;

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

package com.c4f.firebase_chat.model;

import androidx.annotation.Nullable;

public class User {
    private int id;
    private String displayName;
    private String email;

    public User() {}

    public User(int id, String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this.email != null && this.email.equals(((User) obj).email)) {
            return true;
        }
        return super.equals(obj);
    }
}

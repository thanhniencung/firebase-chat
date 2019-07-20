package com.c4f.firebase_chat.firebase;

public interface NodeCallback<T> {
    void onData(T value);
    void onError(String message);
}

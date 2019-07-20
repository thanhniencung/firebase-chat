package com.c4f.firebase_chat.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c4f.firebase_chat.model.Message;
import com.c4f.firebase_chat.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://firebase.google.com/docs/database/android/read-and-write
public class NodeManager implements Node {
    private static NodeManager INSTANCE = null;
    private Map<String, User> userMap = new HashMap<>();

    public static NodeManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeManager();
        }
        return INSTANCE;
    }

    public void listenerOn(String nodeId) {

    }

    @Override
    public void writeMessage(String roomId, final Message msg, final NodeCallback callback) {
        DatabaseReference msgRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("messages")
                .child(roomId);

        msgRef.push().setValue(msg)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.getMessage());
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onData(msg);
                    }
                });
    }

    @Override
    public void writeUser(String key, final User user, final NodeCallback callback) {
        DatabaseReference msgRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("users");

        msgRef.child(key).setValue(user)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.getMessage());
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onData(user);
                    }
                });
    }

    @Override
    public void readMessage(String nodeId, final NodeCallback callback) {
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message msg = dataSnapshot.getValue(Message.class);
                callback.onData(msg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        };
        DatabaseReference msgRef = FirebaseDatabase.getInstance()
                .getReference()
                .child(nodeId);
        msgRef.addValueEventListener(messageListener);
    }

    @Override
    public void readUser(String nodeId, final NodeCallback callback) {
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User msg = dataSnapshot.getValue(User.class);
                callback.onData(msg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        };
        DatabaseReference msgRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(nodeId);
        msgRef.addValueEventListener(userListener);
    }

    @Override
    public void loadFriends(final NodeCallback callback) {
        DatabaseReference friendsRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("users");

        final List<User> result = new ArrayList<>();

        friendsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot friendSnapshot: dataSnapshot.getChildren()) {
                    User user = friendSnapshot.getValue(User.class);
                    if (!friendSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())) {
                        result.add(user);
                    }
                }
                callback.onData(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    @Override
    public void loadMessages(String roomId, final NodeCallback callback) {
        DatabaseReference friendsRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("messages")
                .child(roomId);

        final List<Message> result = new ArrayList<>();
        friendsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot friendSnapshot: dataSnapshot.getChildren()) {
                    Message message = friendSnapshot.getValue(Message.class);
                    result.add(message);
                }
                callback.onData(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public void listenerNewMessage(String roomId, long startAt, final NodeCallback callback) {
        DatabaseReference friendsRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("messages")
                .child(roomId);

        friendsRef.orderByChild("timestamp")
                .startAt(startAt + 1)
                .addChildEventListener(new FBChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Message message = dataSnapshot.getValue(Message.class);
                        callback.onData(message);
                    }
        });
    }
}

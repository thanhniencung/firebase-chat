package com.c4f.firebase_chat.friend;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.c4f.firebase_chat.firebase.NodeCallback;
import com.c4f.firebase_chat.firebase.NodeManager;
import com.c4f.firebase_chat.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class FriendListViewModel extends ViewModel {
    private MutableLiveData<List<User>> friendListResponse = new MutableLiveData<>();
    private MutableLiveData<User> userResponse = new MutableLiveData<>();

    public static FriendListViewModel of(Fragment activity) {
        return ViewModelProviders.of(activity).get(FriendListViewModel.class);
    }

    public MutableLiveData<List<User>> getFriendListResponse() {
        return friendListResponse;
    }

    public MutableLiveData<User> getUserResponse() {
        return userResponse;
    }

    void getFriendList() {
        NodeManager.getInstance().loadFriends(new NodeCallback<List<User>>() {
            @Override
            public void onData(List<User> data) {
                friendListResponse.setValue(data);
            }

            @Override
            public void onError(String message) {
                friendListResponse.setValue(null);
            }
        });
    }

    void getMyInfo() {
        NodeManager.getInstance()
                .readUser(
                        FirebaseAuth.getInstance().getUid(),
                new NodeCallback<User>() {
            @Override
            public void onData(User value) {
                userResponse.setValue(value);
            }

            @Override
            public void onError(String message) {
                userResponse.setValue(null);
            }
        });
    }
}

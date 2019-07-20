package com.c4f.firebase_chat.chat;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.c4f.firebase_chat.firebase.NodeCallback;
import com.c4f.firebase_chat.firebase.NodeManager;
import com.c4f.firebase_chat.model.Message;

import java.util.List;

public class ChatViewModel extends ViewModel {
    private MutableLiveData<Message> msgResponse = new MutableLiveData<>();
    private MutableLiveData<List<Message>> msgListResponse = new MutableLiveData<>();

    public static ChatViewModel of(Fragment activity) {
        return ViewModelProviders.of(activity).get(ChatViewModel.class);
    }

    public MutableLiveData<Message> getMsgResponse() {
        return msgResponse;
    }

    public void setMsgResponse(MutableLiveData<Message> msgResponse) {
        this.msgResponse = msgResponse;
    }

    public MutableLiveData<List<Message>> getMsgListResponse() {
        return msgListResponse;
    }

    public void setMsgListResponse(MutableLiveData<List<Message>> msgListResponse) {
        this.msgListResponse = msgListResponse;
    }

    void getMessageList(String roomId) {
        NodeManager.getInstance().loadMessages(roomId, new NodeCallback<List<Message>>() {
            @Override
            public void onData(List<Message> value) {
                msgListResponse.setValue(value);
            }

            @Override
            public void onError(String message) {
                msgListResponse.setValue(null);
            }
        });
    }

    void listenNewMessage(String roomId, long startAt) {
        NodeManager.getInstance().listenerNewMessage(roomId, startAt, new NodeCallback<Message>() {
            @Override
            public void onData(Message value) {
                msgResponse.setValue(value);
            }

            @Override
            public void onError(String message) {
                String a = "";
            }
        });
    }

    void sendMessage(String roomId, Message message) {
        NodeManager.getInstance().writeMessage(roomId, message, new NodeCallback<Message>() {
            @Override
            public void onData(Message value) {
                Log.d("MESSAGE", "send message successfully");
            }

            @Override
            public void onError(String message) {
                Log.e("MESSAGE", message);
            }
        });
    }
}

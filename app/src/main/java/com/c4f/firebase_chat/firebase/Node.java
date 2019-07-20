package com.c4f.firebase_chat.firebase;

import com.c4f.firebase_chat.model.Message;
import com.c4f.firebase_chat.model.User;

public interface Node {
    void writeMessage(String roomId, Message msg, NodeCallback callback);
    void writeUser(String key, User user, NodeCallback callback);

    void readMessage(String nodeId, NodeCallback callback);
    void readUser(String nodeId, NodeCallback callback);

    void loadFriends(NodeCallback callback);
    void loadMessages(String roomId, NodeCallback callback);
}

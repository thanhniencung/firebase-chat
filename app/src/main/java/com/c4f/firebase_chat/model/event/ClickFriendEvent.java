package com.c4f.firebase_chat.model.event;

import com.c4f.firebase_chat.model.User;

public class ClickFriendEvent {
    private String friend;
    private String roomId;
    private User me;

    public ClickFriendEvent(String roomId, String friend, User me) {
        this.roomId = roomId;
        this.friend = friend;
        this.me = me;
    }

    public String getFriend() {
        return friend;
    }

    public String getRoomId() {
        return roomId;
    }
}

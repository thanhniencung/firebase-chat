package com.c4f.firebase_chat.model.event;

public class ClickFriendEvent {
    private String friend;
    private String roomId;

    public ClickFriendEvent(String roomId, String friend) {
        this.roomId = roomId;
        this.friend = friend;
    }

    public String getFriend() {
        return friend;
    }

    public String getRoomId() {
        return roomId;
    }
}

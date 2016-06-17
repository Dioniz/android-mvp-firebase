package com.diodev.chatapp.contactlist.events;

import com.diodev.chatapp.entities.User;

public class ContactListEvent {
    public static final int onContactAdded = 0;
    public static final int onContactChanged = 1;
    public static final int onContactRemoved = 2;

    private User user;
    private int eventType;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}

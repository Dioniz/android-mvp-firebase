package com.diodev.chatapp.contactlist.ui;

import com.diodev.chatapp.entities.User;

public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}

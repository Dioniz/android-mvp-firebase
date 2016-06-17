package com.diodev.chatapp.addcontact;

import com.diodev.chatapp.addcontact.events.AddContactEvent;

public interface AddContactPresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}
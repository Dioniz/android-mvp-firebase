package com.diodev.chatapp.contactlist;

public interface ContactListRepository {
    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void destroyListener();
    void subscribeToContactListEvents();
    void unsubscribeToContactListEvents();
    void changeConnectionStatus(boolean online);
}

package com.diodev.chatapp.chat;


public interface ChatInteractor {

    void sendMessage(String msg);
    void setRecipient(String recipient);
    void subscribe();
    void unsubscribe();
    void destroyListener();
}

package com.diodev.chatapp.chat;


public interface ChatRepository {
    void changeConnectionStatus(boolean online);

    void sendMessage(String msg);
    void setRecipient(String recipient);
    void subscribe();
    void unsubscribe();
    void destroyListener();
}

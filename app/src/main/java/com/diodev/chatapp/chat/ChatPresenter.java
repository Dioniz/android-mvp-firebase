package com.diodev.chatapp.chat;


import com.diodev.chatapp.chat.events.ChatEvent;

public interface ChatPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);
}

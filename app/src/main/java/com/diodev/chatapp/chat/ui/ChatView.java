package com.diodev.chatapp.chat.ui;


import com.diodev.chatapp.entities.ChatMessage;

public interface ChatView {
    void onMessageReceived(ChatMessage msg);
}

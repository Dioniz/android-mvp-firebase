package com.diodev.chatapp.chat.events;

import com.diodev.chatapp.entities.ChatMessage;



public class ChatEvent {
    private ChatMessage message;

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage chatMessage) {
        message = chatMessage;
    }
}

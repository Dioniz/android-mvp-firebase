package com.diodev.chatapp.chat;


import com.diodev.chatapp.chat.events.ChatEvent;
import com.diodev.chatapp.chat.ui.ChatView;
import com.diodev.chatapp.entities.User;
import com.diodev.chatapp.lib.EventBus;
import com.diodev.chatapp.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;


public class ChatPresenterImpl implements ChatPresenter {
    private EventBus eventBus;
    private ChatView chatView;
    private ChatInteractor chatInteractor;
    private ChatSessionInteractor chatSessionInteractor;

    public ChatPresenterImpl(ChatView chatView){
        this.chatView = chatView;
        this.eventBus = GreenRobotEventBus.getInstance();

        this.chatInteractor = new ChatInteractorImpl();
        this.chatSessionInteractor = new ChatSessionInteractorImpl();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onResume() {
        chatInteractor.subscribe();
        chatSessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onPause() {
        chatInteractor.unsubscribe();
        chatSessionInteractor.changeConnectionStatus(User.OFFLINE);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatInteractor.destroyListener();
        chatView = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        chatInteractor.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatInteractor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if (chatView != null) {
            chatView.onMessageReceived(event.getMessage());
        }
    }
}

package com.diodev.chatapp.chat;


public class ChatInteractorImpl implements ChatInteractor {

    ChatRepository chatRepository;

    public ChatInteractorImpl() {
        this.chatRepository = new ChatRepositoryImpl();
    }

    @Override
    public void sendMessage(String msg) {
        chatRepository.sendMessage(msg);
    }

    @Override
    public void setRecipient(String recipient) {
        chatRepository.setRecipient(recipient);
    }

    @Override
    public void subscribe() {
        chatRepository.subscribe();
    }

    @Override
    public void unsubscribe() {
        chatRepository.unsubscribe();
    }

    @Override
    public void destroyListener() {
        chatRepository.destroyListener();
    }
}

package com.diodev.chatapp.lib;

public class GreenRobotEventBus implements EventBus {

    org.greenrobot.eventbus.EventBus mEventBus;

    private static class SingletonHolder {
        private static final GreenRobotEventBus INSTANCE = new GreenRobotEventBus();
    }

    public static GreenRobotEventBus getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public GreenRobotEventBus() {
        mEventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Override
    public void register(Object subscriber) {
        mEventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        mEventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        mEventBus.post(event);
    }
}

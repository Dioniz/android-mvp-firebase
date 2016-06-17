package com.diodev.chatapp.contactlist;

import com.diodev.chatapp.contactlist.events.ContactListEvent;
import com.diodev.chatapp.contactlist.ui.ContactListView;
import com.diodev.chatapp.entities.User;
import com.diodev.chatapp.lib.EventBus;
import com.diodev.chatapp.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

public class ContactListPresenterImpl implements ContactListPresenter{

    private EventBus eventBus;
    private ContactListView contactListView;
    private ContactListInteractor listInteractor;
    private ContactListSessionInteractor listSessionInteractor;

    public ContactListPresenterImpl(ContactListView view) {
        contactListView = view;
        eventBus = GreenRobotEventBus.getInstance();
        listInteractor = new ContactListInteractorImpl();
        listSessionInteractor = new ContactListSessionInteractorImpl();
    }

    @Override
    public void onPause() {
        listSessionInteractor.changeConnectionStatus(User.OFFLINE);
        listInteractor.unsubscribe();
    }

    @Override
    public void onResume() {
        listSessionInteractor.changeConnectionStatus(User.ONLINE);
        listInteractor.subscribe();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        listInteractor.destroyListener();
        contactListView = null;
    }

    @Override
    public void signOff() {
        listSessionInteractor.changeConnectionStatus(User.OFFLINE);
        listInteractor.unsubscribe();
        listInteractor.destroyListener();
        listSessionInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return listSessionInteractor.getCurrentUserEmail();
    }

    @Override
    public void removeContact(String email) {
        listInteractor.removeContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvent event) {
        User user = event.getUser();
        switch (event.getEventType()){
            case ContactListEvent.onContactAdded:
                onContactAdded(user);
                break;
            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;
            case ContactListEvent.onContactRemoved:
                onContactRemoved(user);
                break;
        }
    }

    private void onContactAdded(User user) {
        if (contactListView != null) {
            contactListView.onContactAdded(user);
        }
    }

    private void onContactChanged(User user) {
        if (contactListView != null) {
            contactListView.onContactChanged(user);
        }
    }

    private void onContactRemoved(User user) {
        if (contactListView != null) {
            contactListView.onContactRemoved(user);
        }
    }
}
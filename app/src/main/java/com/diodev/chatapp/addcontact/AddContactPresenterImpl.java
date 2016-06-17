package com.diodev.chatapp.addcontact;

import com.diodev.chatapp.addcontact.events.AddContactEvent;
import com.diodev.chatapp.addcontact.ui.AddContactView;
import com.diodev.chatapp.lib.EventBus;
import com.diodev.chatapp.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;


public class AddContactPresenterImpl implements AddContactPresenter {

    private EventBus eventBus;
    private AddContactView mAddContactView;
    private AddContactInteractor addContactInteractor;

    public AddContactPresenterImpl(AddContactView view) {
        mAddContactView = view;
        eventBus = GreenRobotEventBus.getInstance();
        addContactInteractor = new AddContactInteractorImpl();
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        mAddContactView = null;
        eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {
        if (mAddContactView != null) {
            mAddContactView.hideInput();
            mAddContactView.showProgress();
        }
        addContactInteractor.execute(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {
        if (mAddContactView != null) {
            mAddContactView.hideProgress();
            mAddContactView.showInput();

            if (event.isError()) {
                mAddContactView.contactNotAdded();
            } else {
                mAddContactView.contactAdded();
            }
        }
    }
}

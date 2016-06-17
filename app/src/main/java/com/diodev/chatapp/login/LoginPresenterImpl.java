package com.diodev.chatapp.login;


import android.util.Log;

import com.diodev.chatapp.lib.EventBus;
import com.diodev.chatapp.lib.GreenRobotEventBus;
import com.diodev.chatapp.login.events.LoginEvent;
import com.diodev.chatapp.login.ui.LoginView;

import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterImpl implements LoginPresenter{

    private EventBus mEventBus;
    private LoginView mLoginView;
    private LoginInteractor mLoginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;
        mLoginInteractor = new LoginInteractorImpl();
        mEventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        mEventBus.register(this);
    }

    @Override
    public void onDestroy() {
        mLoginView = null;
        mEventBus.unregister(this);
    }

    @Override
    public void checkForAuthenticatedUser() {
        if(mLoginView != null) {
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }
        mLoginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if(mLoginView != null) {
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }
        mLoginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if(mLoginView != null) {
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }
        mLoginInteractor.doSignUp(email, password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()) {
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onSignUpError:
                onSignUnError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
        }
    }

    private void onFailedToRecoverSession() {
        if(mLoginView != null) {
            mLoginView.enableInputs();
            mLoginView.hideProgress();
        }
        Log.e("LoginPresenterImpl", "onFailedToRecoverSession");
    }

    private void onSignInSuccess() {
        if(mLoginView != null) {
            mLoginView.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess() {
        if(mLoginView != null) {
            mLoginView.newUserSuccess();
        }
    }
    private void onSignInError(String error) {
        if(mLoginView != null) {
            mLoginView.enableInputs();
            mLoginView.hideProgress();
            mLoginView.loginError(error);
        }
    }
    private void onSignUnError(String error) {
        if(mLoginView != null) {
            mLoginView.enableInputs();
            mLoginView.hideProgress();
            mLoginView.newUserError(error);
        }
    }
}

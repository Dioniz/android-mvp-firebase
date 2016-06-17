package com.diodev.chatapp.login;


import com.diodev.chatapp.domain.FirebaseHelper;
import com.diodev.chatapp.entities.User;
import com.diodev.chatapp.lib.EventBus;
import com.diodev.chatapp.lib.GreenRobotEventBus;
import com.diodev.chatapp.login.events.LoginEvent;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class LoginRepositoryImpl implements  LoginRepository{

    private static final String TAG = LoginRepositoryImpl.class.getName();
    private FirebaseHelper mFirebaseHelper;
    private Firebase mDataReference;
    private Firebase mMyUserReference;

    public LoginRepositoryImpl() {
        this.mFirebaseHelper = FirebaseHelper.getInstance();
        mDataReference = mFirebaseHelper.getDataReference();
        mMyUserReference = mFirebaseHelper.getMyUserReference();
    }

    @Override
    public void checkSession() {
        if (mDataReference.getAuth() != null){
            initSignIn();
        } else {
            postEvent(LoginEvent.onFailedToRecoverSession);
        }
    }

    // Registro
    @Override
    public void signUp(final String email, final String password) {
        mDataReference.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                postEvent(LoginEvent.onSignUpSuccess);
                signIn(email, password);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                postEvent(LoginEvent.onSignUpError, firebaseError.getMessage());
            }
        });
    }

    // Login
    @Override
    public void signIn(String email, String password) {
        mDataReference.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                initSignIn();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                postEvent(LoginEvent.onSignInError, firebaseError.getMessage());
            }
        });
    }

    private void initSignIn() {
        mMyUserReference = mFirebaseHelper.getMyUserReference();
        mMyUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);

                if (currentUser == null) {
                    registerNewUser();
                }
                mFirebaseHelper.changeUserConnectionStatus(User.ONLINE);
                postEvent(LoginEvent.onSignInSuccess);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }

    private void registerNewUser() {
        String email = mFirebaseHelper.getAuthUserEmail();
        if (email != null) {
            User currentUser = new User();
            currentUser.setEmail(email);
            mMyUserReference.setValue(currentUser);
        }
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);

    }
    private void postEvent(int type) {
        postEvent(type, null);
    }
}

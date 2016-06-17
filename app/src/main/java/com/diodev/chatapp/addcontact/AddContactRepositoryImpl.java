package com.diodev.chatapp.addcontact;


import com.diodev.chatapp.addcontact.events.AddContactEvent;
import com.diodev.chatapp.domain.FirebaseHelper;
import com.diodev.chatapp.entities.User;
import com.diodev.chatapp.lib.EventBus;
import com.diodev.chatapp.lib.GreenRobotEventBus;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AddContactRepositoryImpl implements AddContactRepository {

    private EventBus eventBus;
    private FirebaseHelper firebaseHelper;

    public AddContactRepositoryImpl() {
        eventBus = GreenRobotEventBus.getInstance();
        firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void addContact(final String email) {
        final String key = email.replace(".", "_");
        Firebase userReference = firebaseHelper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    // Añadir contacto a mi lista de contactos
                    Firebase myContactsReference = firebaseHelper.getMyContactsReference();
                    myContactsReference.child(key).setValue(user.isOnline());

                    // Añadirme a mi en su lista de contactos
                    String currentUserKey = firebaseHelper.getAuthUserEmail();
                    currentUserKey = currentUserKey.replace(".", "_");
                    Firebase reverseContactsReference = firebaseHelper.getContactsReference(email);
                    reverseContactsReference.child(currentUserKey).setValue(User.ONLINE);

                    postSuccess();
                } else {
                    postError();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                postError();
            }
        });
    }

    private void postSuccess() {
        post(false);
    }

    private void postError() {
        post(true);
    }

    private void post(boolean error) {
        AddContactEvent event = new AddContactEvent();
        event.setError(error);
        eventBus.post(event);
    }
}

package com.diodev.chatapp.addcontact;


public class AddContactInteractorImpl implements AddContactInteractor {

    private AddContactRepository addContactRepository;

    public AddContactInteractorImpl() {
        addContactRepository = new AddContactRepositoryImpl();
    }

    @Override
    public void execute(String email) {
        addContactRepository.addContact(email);
    }
}

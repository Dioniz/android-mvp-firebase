package com.diodev.chatapp.login;


public class LoginInteractorImpl implements LoginInteractor{

    private LoginRepository mLoginRepository;

    public LoginInteractorImpl() {
        mLoginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkSession() {
        mLoginRepository.checkSession();
    }

    @Override
    public void doSignUp(String email, String password) {
        mLoginRepository.signUp(email, password);
    }

    @Override
    public void doSignIn(String email, String password) {
        mLoginRepository.signIn(email, password);
    }
}

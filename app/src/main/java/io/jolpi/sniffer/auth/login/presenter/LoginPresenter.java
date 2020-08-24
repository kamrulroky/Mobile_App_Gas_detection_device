package io.jolpi.sniffer.auth.login.presenter;

import io.jolpi.sniffer.auth.login.view.LoginViewInterface;
import io.jolpi.sniffer.interactor.CreateUserInteractor;
import io.jolpi.sniffer.interactor.CreateUserInteractorInterface;
import io.jolpi.sniffer.model.CreateUserRequest;
import io.jolpi.sniffer.model.CreateUserResponse;

/**
 * Created by nasser on 4/2/18.
 */

public class LoginPresenter implements LoginPresenterInterface, CreateUserInteractorInterface.OnCreateUserFinishedListener {
    private LoginViewInterface mLoginViewInterface;
    private CreateUserInteractorInterface createUserInteractorInterface;


    public LoginPresenter(LoginViewInterface loginViewInterface) {
        this.mLoginViewInterface = loginViewInterface;
        this.createUserInteractorInterface = new CreateUserInteractor();
    }

    @Override
    public void onLoginButtonClick(String mobile) {
        createUserInteractorInterface.createNewUser(this, new CreateUserRequest(mobile));
        mLoginViewInterface.showProgress();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCreateUserSuccess(CreateUserResponse createUserResponse) {
        mLoginViewInterface.hideProgress("");
        mLoginViewInterface.showNextPage();
    }

    @Override
    public void onCreateUserError() {
        mLoginViewInterface.hideProgress("");
    }
}

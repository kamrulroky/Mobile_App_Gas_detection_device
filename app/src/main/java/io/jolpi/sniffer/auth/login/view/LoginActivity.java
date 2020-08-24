package io.jolpi.sniffer.auth.login.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import io.jolpi.sniffer.R;
import io.jolpi.sniffer.auth.JolpiDeviceListActivity;
import io.jolpi.sniffer.auth.login.presenter.LoginPresenter;
import io.jolpi.sniffer.auth.login.presenter.LoginPresenterInterface;
import io.jolpi.sniffer.home.view.HomeActivity;

public class LoginActivity extends Activity implements LoginViewInterface {


    private EditText mEtPhoneNumber;
    private LoginPresenterInterface mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEtPhoneNumber = (EditText) findViewById(R.id.et_mobileNumber);
        mPresenter = new LoginPresenter(this);

    }

    public void onLoginButtonClicked(View view) {
        String phoneNumber = mEtPhoneNumber.getText().toString();
        mPresenter.onLoginButtonClick(phoneNumber);
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress(String message) {

    }

    @Override
    public void setParsingError() {

    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this, JolpiDeviceListActivity.class));
    }
}

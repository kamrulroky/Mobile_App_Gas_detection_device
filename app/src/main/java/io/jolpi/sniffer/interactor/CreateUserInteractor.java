package io.jolpi.sniffer.interactor;

import android.util.Log;

import io.jolpi.sniffer.auth.login.presenter.LoginPresenter;
import io.jolpi.sniffer.model.CreateUserRequest;
import io.jolpi.sniffer.model.CreateUserResponse;
import io.jolpi.sniffer.rest_client.ApiClient;
import io.jolpi.sniffer.rest_client.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nasser on 4/2/18.
 */

public class CreateUserInteractor implements CreateUserInteractorInterface {
    @Override
    public void createNewUser(final OnCreateUserFinishedListener listener, CreateUserRequest createUserRequest) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<CreateUserResponse> call = apiService.createNewUserRequest(createUserRequest);
        call.enqueue(new Callback<CreateUserResponse>() {
            @Override
            public void onResponse(Call<CreateUserResponse>call, Response<CreateUserResponse> response) {
                if(response.body() != null) {
                    Log.e("createU", response.body().toString());
                    listener.onCreateUserSuccess(response.body());
                } else {
                    Log.e("createUserMessage", response.message());
                    listener.onCreateUserError();
                }

            }

            @Override
            public void onFailure(Call<CreateUserResponse>call, Throwable t) {
                listener.onCreateUserError();
            }
        });
    }
}

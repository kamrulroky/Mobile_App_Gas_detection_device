package io.jolpi.sniffer.rest_client;

import io.jolpi.sniffer.model.CreateUserRequest;
import io.jolpi.sniffer.model.CreateUserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nasser on 4/2/18.
 */

public interface ApiInterface {
    @POST("/customers")
    Call<CreateUserResponse> createNewUserRequest(@Body CreateUserRequest createUserRequest);
}

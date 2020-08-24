package io.jolpi.sniffer.interactor;

import io.jolpi.sniffer.model.CreateUserRequest;
import io.jolpi.sniffer.model.CreateUserResponse;

/**
 * Created by nasser on 4/2/18.
 */

public interface CreateUserInteractorInterface {
    interface OnCreateUserFinishedListener {
        /**
         *
         * @param createUserResponse
         */
        void onCreateUserSuccess(CreateUserResponse createUserResponse);

        /**
         * Error functionality for create user.
         */
        void onCreateUserError();
    }

    /**
     *
     * @param listener
     * @param createUserRequest
     */

    void createNewUser(CreateUserInteractorInterface.OnCreateUserFinishedListener listener, CreateUserRequest createUserRequest);

}

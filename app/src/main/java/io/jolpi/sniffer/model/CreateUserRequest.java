package io.jolpi.sniffer.model;

/**
 * Created by nasser on 4/2/18.
 */

public class CreateUserRequest {
    final String MSISDN;
    String email;
    String other_mobile;

    CreateUserRequest(String MSISDN, String email, String other_mobile) {
        this.MSISDN = MSISDN;
        this.email = email;
        this.other_mobile = other_mobile;
    }

    public CreateUserRequest(String MSISDN) {
        this.MSISDN = MSISDN;
    }
}


//{
//        "MSISDN": "01730053053",
//        "email": "anis.programmer@gmail.com",
//        "other_mobile": "01730053050"
//        }

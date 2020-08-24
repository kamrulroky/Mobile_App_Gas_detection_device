package io.jolpi.sniffer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasser on 4/2/18.
 */

public class CreateUserResponse {
    @SerializedName("id")
    int id;
    @SerializedName("MSISDN")
    String MSISDN;
    @SerializedName("email")
    String email;
    @SerializedName("other_mobile")
    String other_mobile;
    @SerializedName("messenger_id")
    String messenger_id;
    @SerializedName("fcm_token")
    String fcm_token;

    public CreateUserResponse(int id, String MSISDN, String email, String other_mobile, String messenger_id, String fcm_token) {
        this.id = id;
        this.MSISDN = MSISDN;
        this.email = email;
        this.other_mobile = other_mobile;
        this.messenger_id = messenger_id;
        this.fcm_token = fcm_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMSISDN() {
        return MSISDN;
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOther_mobile() {
        return other_mobile;
    }

    public void setOther_mobile(String other_mobile) {
        this.other_mobile = other_mobile;
    }

    public String getMessenger_id() {
        return messenger_id;
    }

    public void setMessenger_id(String messenger_id) {
        this.messenger_id = messenger_id;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    @Override
    public String toString() {
        return "CreateUserResponse{" +
                "id=" + id +
                ", MSISDN='" + MSISDN + '\'' +
                ", email='" + email + '\'' +
                ", other_mobile='" + other_mobile + '\'' +
                ", messenger_id='" + messenger_id + '\'' +
                ", fcm_token='" + fcm_token + '\'' +
                '}';
    }
}

//
//{
//        "id": 10,
//        "MSISDN": "01730053053",
//        "email": "anis.programmer@gmail.com",
//        "other_mobile": "01730053050",
//        "messenger_id": "ajaxray",
//        "fcm_token": "bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1"
//        }
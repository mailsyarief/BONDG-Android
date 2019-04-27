package com.example.coba.myreport.Response;

import com.example.coba.myreport.Pojo.User;

public class LoginResponse {

    private String error;
    private User message;

    public String getError() {
        return error;
    }

    public User getMessage() {
        return message;
    }

}

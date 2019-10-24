package com.example.coba.myreport.Response;

import com.example.coba.myreport.Pojo.User;

public class LoginResponse {

    public Integer getError() {
        return error;
    }

    public User getMessage() {
        return message;
    }

    private Integer error;
    private User message;

}

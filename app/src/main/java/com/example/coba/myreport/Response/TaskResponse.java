package com.example.coba.myreport.Response;

import com.example.coba.myreport.Pojo.Task;
import com.example.coba.myreport.Pojo.User;

import java.util.List;

public class TaskResponse {

    private String error;
    private List<Task> message;

    public String getError() {
        return error;
    }

    public List<Task> getMessage() {
        return message;
    }

}

package com.example.coba.myreport.Session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.coba.myreport.Pojo.Task;
import com.example.coba.myreport.Pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TaskSessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private List<Task> task;
    Context _context;


    private String SessionManager;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "task";

    public TaskSessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setStartTask(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String getStartTask(String key, String value){
        return pref.getString(key, "-");
    }

    public void setEndTask(String key, String value){
        editor.putString(key,value);
        editor.commit();
    }

    public String getEndTask(String key){
        return pref.getString(key, "-");
    }

    public void setTaskList(List<Task> task) {
        this.task = task;
        Gson gson = new Gson();
        String json = gson.toJson(task);
        editor.putString("TaskList", json);
        editor.commit();
    }

    public List<Task> getTaskList() {
        Gson gson = new Gson();
        String json = pref.getString("TaskList","-");
        Type type = new TypeToken<List<Task>>() {}.getType();
        List<Task> task = gson.fromJson(json, type);
        return task;
    }

}

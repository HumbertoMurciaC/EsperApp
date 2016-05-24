package com.example.humbertomariom.login;

import android.app.Application;

/**
 * Created by Humberto Mario M on 24/05/2016.
 */
public class GlobalVariables extends Application {

    private String id="";
    private String userId="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

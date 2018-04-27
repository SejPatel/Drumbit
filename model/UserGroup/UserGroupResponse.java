package com.example.devyani.drumbeatapplication.model.UserGroup;



/**
 * Created by devyani on 19/4/18.
 */

public class UserGroupResponse {

    public Integer status;
    public String message;
    public UserGroupData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserGroupData getData() {
        return data;
    }

    public void setData(UserGroupData data) {
        this.data = data;
    }

}

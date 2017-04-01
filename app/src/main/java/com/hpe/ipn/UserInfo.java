package com.hpe.ipn;

/**
 * Created by ventrapr on 3/30/2017.
 */

public class UserInfo {

    String userName;
    String userId;

    public UserInfo(){}

    public UserInfo(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

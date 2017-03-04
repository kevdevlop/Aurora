package com.example.kevdev.aurora.Model;

/**
 * Created by KevDev on 26/02/17.
 */
public class UserModel {


    public String userName;
    public String email;

    public UserModel() {
    }

    public UserModel(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }


    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "UserModel{" +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

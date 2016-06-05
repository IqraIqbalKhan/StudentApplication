package com.example.iqrakhan.studentapplication;


public class User {

    String username;
    String password;
//    int userType;
//    String imagePath;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
//        this.userType = userType;
//        this.imagePath = imagePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public int getUserType() {
//        return userType;
//    }
//
//    public void setUserType(int userType) {
//        this.userType = userType;
//    }
//
//    public String getImagePath() {
//        return imagePath;
//    }
//
//    public void setImagePath(String imagePath) {
//        this.imagePath = imagePath;
//    }
}

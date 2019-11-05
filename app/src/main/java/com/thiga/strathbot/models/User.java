package com.thiga.strathbot.models;

public class User {
    private String username;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String mobile_number;
    private String id;


//    public User(){}

    public User(String username, String email, String first_name, String last_name, String mobile_number, String id) {
        this.username = username;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile_number = mobile_number;
        this.id = id;
    }

//    public User(String username, String password, String email, String first_name, String last_name, String mobile_number, String id) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.first_name = first_name;
//        this.last_name = last_name;
//        this.mobile_number = mobile_number;
//        this.id = id;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
}

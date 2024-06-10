package com.example.forum;

public class User {
    private String email;
    private String login;
    private String role;


    public User(String email, String login, String role) {
        this.email = email;
        this.login = login;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

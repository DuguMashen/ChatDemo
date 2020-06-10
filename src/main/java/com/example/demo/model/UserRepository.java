package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
public class UserRepository {
    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser(String acc) {
        for (User user : users) {
            if (user.getAcc().equals(acc)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }
}

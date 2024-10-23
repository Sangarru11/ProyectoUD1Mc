package org.example.Controllers;

import org.example.Entity.User;

import java.io.IOException;
import java.util.List;

public class UserLogin {

    private User loggedUser;

    public boolean login(String inputIdOrName, String inputPassword) throws IOException {
        List<User> users = loadUsersFromFile();
        boolean isLoginSuccessful = false;
        for (User user : users) {
            if ((user.getId().equals(inputIdOrName) || user.getName().equals(inputIdOrName))
                    && user.getPassword().equals(inputPassword)) {
                loggedUser = user;
                isLoginSuccessful = true;
            }
        }
        return isLoginSuccessful;
    }


    public User getLoggedUser() {
        return loggedUser;
    }

    private List<User> loadUsersFromFile() throws IOException {
        return new UserRegister().loadUsersFromFile();
    }
}
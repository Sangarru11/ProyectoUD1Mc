package org.example.Controllers;

import org.example.Entity.User;

import java.io.IOException;
import java.util.List;

public class UserLogin {
    private static final String SAVE_XML_FILE = "users.xml";

    public static boolean login(String inputId, String inputPassword) throws IOException {
        List<User> users = loadUsersFromFile();

        for (User user : users) {
            if (user.getId().equals(inputId) && user.getPassword().equals(inputPassword)) {
                return true;
            }
        }
        return false;
    }

    private static List<User> loadUsersFromFile() throws IOException {
        List<User> users = new UserRegister().loadUsersFromFile();
        return users;
    }
}

package org.example.Controllers;

import org.example.Entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserContact {

    private User user;
    private List<User> contacts;

    public UserContact(User user) {
        this.user = user;
        this.contacts = new ArrayList<>(user.getContacts());
    }

    public void addContact(User newContact) throws IOException {
        if (!contacts.contains(newContact)) {
            contacts.add(newContact);
            user.setContacts(contacts);
            saveContactsToFile();
        } else {
            System.out.println("El contacto ya está en la lista.");
        }
    }

    public List<User> getContacts() {
        return contacts;
    }
    private void saveContactsToFile() throws IOException {
        UserRegister userRegister = new UserRegister();
        userRegister.updateUser(user);
    }
}

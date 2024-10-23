package org.example.Controllers;

import org.example.Entity.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRegister {

    private static final String SAVE_XML_FILE = "users.xml";

    public void registerUser(User user) throws IOException {
        List<User> existingUsers = loadUsersFromFile();

        for (User existingUser : existingUsers) {
            if (existingUser.getId().equals(user.getId()) || existingUser.getName().equals(user.getName())) {
                throw new IOException("El usuario ya está registrado.");
            }
        }
        writeUserToFile(user);
    }

    public List<User> loadUsersFromFile() throws IOException {
        List<User> users = new ArrayList<>();
        File xmlFile = new File(SAVE_XML_FILE);

        if (xmlFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(xmlFile))) {
                String line;
                String id = null;
                String name = null;
                String password = null;
                List<User> contacts = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.startsWith("<id>")) {
                        id = line.substring(4, line.indexOf("</id>"));
                    } else if (line.startsWith("<nombre>")) {
                        name = line.substring(8, line.indexOf("</nombre>"));
                    } else if (line.startsWith("<password>")) {
                        password = line.substring(10, line.indexOf("</password>"));
                    } else if (line.startsWith("<contacto>")) {
                        String contactId = null;
                        String contactName = null;
                        while (!(line = reader.readLine().trim()).startsWith("</contacto>")) {
                            if (line.startsWith("<id>")) {
                                contactId = line.substring(4, line.indexOf("</id>"));
                            } else if (line.startsWith("<nombre>")) {
                                contactName = line.substring(8, line.indexOf("</nombre>"));
                            }
                        }
                        if (contactId != null && contactName != null) {
                            contacts.add(new User(contactId, contactName, ""));
                        }
                    } else if (line.startsWith("</persona>")) {
                        if (id != null && name != null && password != null) {
                            User user = new User(id, name, password);
                            user.setContacts(contacts);
                            users.add(user);
                            id = null;
                            name = null;
                            password = null;
                            contacts = new ArrayList<>();
                        }
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
    public void updateUser(User user) throws IOException {
        List<User> users = loadUsersFromFile();

        for (User existingUser : users) {
            if (existingUser.getId().equals(user.getId())) {
                existingUser.setContacts(user.getContacts());
                break;
            }
        }

        saveUsersToFile(users);
    }

    private void writeUserToFile(User user) throws IOException {
        File file = new File(SAVE_XML_FILE);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_XML_FILE, true))) {
            writer.write("<persona>\n");
            writer.write("<id>" + user.getId() + "</id>\n");
            writer.write("<nombre>" + user.getName() + "</nombre>\n");
            writer.write("<password>" + user.getPassword() + "</password>\n");

            writer.write("        <contactos>\n");
            for (User contact : user.getContacts()) {
                writer.write("<contacto>\n");
                writer.write("<id>" + contact.getId() + "</id>\n");
                writer.write("<nombre>" + contact.getName() + "</nombre>\n");
                writer.write("</contacto>\n");
            }
            writer.write("</contactos>\n");

            writer.write("</persona>\n");
            writer.flush();
        }
    }
    private void saveUsersToFile(List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_XML_FILE))) {
            for (User user : users) {
                writer.write("<persona>\n");
                writer.write("<id>" + user.getId() + "</id>\n");
                writer.write("<nombre>" + user.getName() + "</nombre>\n");
                writer.write("<password>" + user.getPassword() + "</password>\n");
                writer.write("<contactos>\n");
                for (User contact : user.getContacts()) {
                    writer.write("<contacto>\n");
                    writer.write("<id>" + contact.getId() + "</id>\n");
                    writer.write("<nombre>" + contact.getName() + "</nombre>\n");
                    writer.write("</contacto>\n");
                }
                writer.write("</contactos>\n");
                writer.write("</persona>\n");
            }
        }
    }
}
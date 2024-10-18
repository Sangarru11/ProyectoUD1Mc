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

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("<id>")) {
                        id = line.substring(4, line.indexOf("</id>"));
                    } else if (line.startsWith("<nombre>")) {
                        name = line.substring(8, line.indexOf("</nombre>"));
                    } else if (line.startsWith("<password>")) {
                        password = line.substring(10, line.indexOf("</password>"));
                    } else if (line.startsWith("</persona>")) {
                        if (id != null && name != null && password != null) {
                            users.add(new User(id, name, password));
                            id = null;
                            name = null;
                            password = null;
                        }
                    }
                }
            }
        }
        return users;
    }

    private void writeUserToFile(User user) throws IOException {
        File file = new File(SAVE_XML_FILE);
        boolean isNewFile = !file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_XML_FILE, true))) {
            if (isNewFile) {
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                writer.write("<personas>\n");
            }
            writer.write("    <persona>\n");
            writer.write("        <id>" + user.getId() + "</id>\n");
            writer.write("        <nombre>" + user.getName() + "</nombre>\n");
            writer.write("        <password>" + user.getPassword() + "</password>\n");
            writer.write("    </persona>\n");
            writer.flush();
        }
    }
}
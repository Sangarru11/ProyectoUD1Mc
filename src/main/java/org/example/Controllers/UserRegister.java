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
            if (existingUser.getId().equals(user.getId()) || existingUser.getNombre().equals(user.getNombre())) {
                throw new IOException("El usuario ya está registrado.");
            }
        }
        writeUserToFile(user);
    }

    private List<User> loadUsersFromFile() throws IOException {
        List<User> users = new ArrayList<>();
        File xmlFile = new File(SAVE_XML_FILE);

        if (xmlFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(xmlFile))) {
                String line;
                String id = null;
                String nombre = null;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("<id>")) {
                        id = line.substring(4, line.indexOf("</id>"));
                    } else if (line.startsWith("<nombre>")) {
                        nombre = line.substring(8, line.indexOf("</nombre>"));
                    } else if (line.startsWith("</persona>")) {
                        if (id != null && nombre != null) {
                            users.add(new User(id, nombre));
                            id = null;
                            nombre = null;
                        }
                    }
                }
            }
        }
        return users;
    }

    private void writeUserToFile(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_XML_FILE, true))) {
            File file = new File(SAVE_XML_FILE);
            if (file.length() == 0) {
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                writer.write("<personas>\n");
            }
            writer.write("    <persona>\n");
            writer.write("        <id>" + user.getId() + "</id>\n");
            writer.write("        <nombre>" + user.getNombre() + "</nombre>\n");
            writer.write("    </persona>\n");
            writer.flush();
        }
    }

    public void closeXmlFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_XML_FILE, true))) {
            writer.write("</personas>");
        }
    }
}
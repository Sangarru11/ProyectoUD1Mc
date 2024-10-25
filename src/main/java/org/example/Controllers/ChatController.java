package org.example.Controllers;

import org.example.Entity.ChatConst;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChatController {

    private static final String CHAT_FILE_PATH = "chat.xml";

    public void saveMessage(ChatConst message) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CHAT_FILE_PATH, true))) {
                writer.write("    <message>\n");
                writer.write("        <sender>" + message.getSender() + "</sender>\n");
                writer.write("        <recipient>" + message.getRecipient() + "</recipient>\n");
                writer.write("        <content>" + message.getContent() + "</content>\n");
                writer.write("        <date>" + message.getDate() + "</date>\n");
                writer.write("        <time>" + message.getTime() + "</time>\n");
                writer.write("    </message>\n");
                writer.flush();
            }
    }

    public List<ChatConst> loadMessages(String userId) throws IOException {
        List<ChatConst> messages = new ArrayList<>();
        File xmlFile = new File(CHAT_FILE_PATH);

        if (xmlFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(xmlFile))) {
                String line;
                String sender = null;
                String recipient = null;
                String content = null;
                String date = null;
                String time = null;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.startsWith("<sender>")) {
                        sender = line.substring(8, line.indexOf("</sender>"));
                    } else if (line.startsWith("<recipient>")) {
                        recipient = line.substring(11, line.indexOf("</recipient>"));
                    } else if (line.startsWith("<content>")) {
                        content = line.substring(9, line.indexOf("</content>"));
                    } else if (line.startsWith("<date>")) {
                        date = line.substring(6, line.indexOf("</date>"));
                    } else if (line.startsWith("<time>")) {
                        time = line.substring(6, line.indexOf("</time>"));
                    } else if (line.startsWith("</message>")) {
                        if ((sender != null && recipient != null) && (sender.equals(userId) || recipient.equals(userId))) {
                            messages.add(new ChatConst(sender, recipient, content, date, time));
                        }
                        sender = null;
                        recipient = null;
                        content = null;
                        date = null;
                        time = null;
                    }
                }
            }
        }
        return messages;
    }

    public void saveAllMessages(List<ChatConst> messages) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CHAT_FILE_PATH))) {
            writer.write("<chat>\n");
            for (ChatConst message : messages) {
                writer.write("    <message>\n");
                writer.write("        <sender>" + message.getSender() + "</sender>\n");
                writer.write("        <recipient>" + message.getRecipient() + "</recipient>\n");
                writer.write("        <content>" + message.getContent() + "</content>\n");
                writer.write("        <date>" + message.getDate() + "</date>\n");
                writer.write("        <time>" + message.getTime() + "</time>\n");
                writer.write("    </message>\n");
            }
            writer.write("</chat>\n");
        }
    }
}

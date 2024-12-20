package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Controllers.ChatController;
import org.example.Controllers.UserLogin;
import org.example.Entity.ChatConst;
import org.example.Entity.User;


import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.View.LoginUserController.changeScene;

public class ChatViewController extends Controller implements Initializable {

    @FXML
    private TextArea messageArea;
    @FXML
    private TextField messageInput;
    private ChatController chatController;
    private String currentUserId;
    private String recipientId;

    public ChatViewController() {
        chatController = new ChatController();
    }

    public void setUsers(User currentUser, User recipient) {
        this.currentUserId = currentUser.getId();
        this.recipientId = recipient.getId();
        loadChatHistory();
    }


    @FXML
    public void exportChat() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Conversación");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"), new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try (FileOutputStream fos = new FileOutputStream(file);
                 OutputStreamWriter osw = new OutputStreamWriter(fos);
                 PrintWriter writer = new PrintWriter(osw)) {

                List<ChatConst> messages = chatController.loadMessages(currentUserId);

                for (ChatConst message : messages) {
                    if (message.getRecipient().equals(recipientId) || message.getSender().equals(recipientId)) {
                        writer.println("[" + message.getDate() + " " + message.getTime() + "] " +
                                message.getSender() + ": " + message.getContent());
                    }
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Conversación exportada correctamente a " + file.getAbsolutePath());
                alert.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void initialize() {
    }

    @Override
    public void onOpen(Object input) throws IOException {
        if (input instanceof User[]) {
            User[] users = (User[]) input;
            if (users.length == 2) {
                setUsers(users[0], users[1]);
            }
        }

    }
    public Map<String, Long> countWordsInMessages(List<ChatConst> messages) {
        return messages.stream().flatMap(message -> Arrays.stream(message.getContent().split("\\W+"))).filter(word -> !word.isEmpty()).collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
    }
    public Map<String, Long> countMessagesByUser(List<ChatConst> messages) {
        return messages.stream().collect(Collectors.groupingBy(ChatConst::getSender, Collectors.counting()));
    }

    public long countTotalMessages(List<ChatConst> messages) {
        return messages.size();
    }

    @FXML
    public void analyzeChat() {
        try {
            List<ChatConst> messages = chatController.loadMessages(currentUserId);
            analyzeConversation(messages);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error al analizar la conversación.");
            alert.show();
        }
    }

    public void analyzeConversation(List<ChatConst> messages) {
        long totalMessages = countTotalMessages(messages);
        Map<String, Long> messagesByUser = countMessagesByUser(messages);
        Map<String, Long> wordCounts = countWordsInMessages(messages);

        StringBuilder analysisResult = new StringBuilder();
        analysisResult.append("Total de mensajes: ").append(totalMessages).append("\n\n");

        analysisResult.append("Cantidad de mensajes por usuario:\n");
        messagesByUser.forEach((user, count) ->
                analysisResult.append(user).append(": ").append(count).append("\n"));

        analysisResult.append("\nPalabras más repetidas:\n");
        wordCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .forEach(entry ->
                        analysisResult.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n"));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Análisis de Conversación");
        alert.setHeaderText("Resultados del análisis");
        alert.setContentText(analysisResult.toString());
        alert.showAndWait();
    }

    @Override
    public void onClose(Object output) {
    }

    public void loadChatHistory() {
        try {
            List<ChatConst> messages = chatController.loadMessages(currentUserId);
            messageArea.clear();
            for (ChatConst message : messages) {
                if (message.getRecipient().equals(recipientId) || message.getSender().equals(recipientId)) {
                    displayMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void sendMessage() {
        String content = messageInput.getText().trim();
        String date = LocalDate.now().toString();
        String time = LocalTime.now().toString();

        if (!content.isEmpty()) {
            ChatConst message = new ChatConst(currentUserId, recipientId, content, date, time);
            displayMessage(message);
            try {
                chatController.saveMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

            messageInput.clear();
        }
    }
    @FXML
    public void ChangeSceneToContact() throws IOException {
        UserLogin userLogin = new UserLogin();
        changeScene(Scenes.ContactList, userLogin.getLoggedUser());
    }

    private void displayMessage(ChatConst message) {
        String formattedMessage = String.format("[%s %s] %s: %s\n", message.getDate(), message.getTime(), message.getSender().equals(currentUserId) ? "Tú" : message.getSender(), message.getContent());
        messageArea.appendText(formattedMessage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

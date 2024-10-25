package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.Controllers.ChatController;
import org.example.Entity.ChatConst;
import org.example.Entity.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

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

    @Override
    public void initialize() {

    }

    @Override
    public void onOpen(Object input){
        if (input instanceof User[]) {
            User[] users = (User[]) input;
            if (users.length == 2) {
                setUsers(users[0], users[1]);
            }
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onClose(Object output) {
    }

    private void loadChatHistory() {
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
    private void sendMessage(){
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

    private void displayMessage(ChatConst message) {
        String formattedMessage = String.format("[%s %s] %s: %s\n",
                message.getDate(), message.getTime(), message.getSender(), message.getContent());
        messageArea.appendText(formattedMessage);
    }
    @FXML
    private void ChangeSceneToContact() throws IOException {
        LoginUserController.changeScene(Scenes.ContactList, null);
    }
}

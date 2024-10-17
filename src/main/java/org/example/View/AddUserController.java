package org.example.View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.Controllers.UserRegister;
import org.example.Entity.User;

import java.io.IOException;

public class AddUserController extends Controller {

    @FXML
    private TextField userIdField;
    @FXML
    private TextField usernameField;

    private UserRegister userRegister;

    @Override
    public void initialize() {
        userRegister = new UserRegister();
    }

    @FXML
    private void AddUser() {
        String userId = userIdField.getText().trim();
        String username = usernameField.getText().trim();

        if (userId.isEmpty() || username.isEmpty()) {
            showAlert("Error", "ID y nombre son obligatorios");
            return;
        }

        User newUser = new User(userId, username);

        try {
            userRegister.registerUser(newUser);
            showAlert("Info", "Usuario registrado");
            userIdField.clear();
            usernameField.clear();
        } catch (IOException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
}

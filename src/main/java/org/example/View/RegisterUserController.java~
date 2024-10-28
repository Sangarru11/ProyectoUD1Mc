package org.example.View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.Entity.User;
import org.example.Controllers.UserRegister;

import java.io.IOException;

public class RegisterUserController extends Controller {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;

    private UserRegister userRegister;

    @Override
    @FXML
    public void initialize() {
        userRegister = new UserRegister();
    }

    @FXML
    private void AddUser() {
        String userId = txtId.getText().trim();
        String username = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();

        if (userId.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Todos los campos son obligatorios.");
            alert.show();
        }

        User newUser = new User(userId, username, password);

        try {
            userRegister.registerUser(newUser);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Usuario registrado");
            alert.show();
            txtId.clear();
            txtUserName.clear();
            txtPassword.clear();
            LoginUserController.changeScene(Scenes.Login,null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onOpen(Object input) {
    }

    @FXML
    public void ChangeToLogin() throws IOException {
        LoginUserController.changeScene(Scenes.Login,null);
    }
    @Override
    public void onClose(Object output) {
    }
}
package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.Controllers.UserLogin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginUserController extends Controller implements Initializable {
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void LoginUser() {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Todos los campos son obligatorios.");
            alert.show();
        }

        try {
            boolean isLoginSuccessful = UserLogin.login(userName, password);
            if (isLoginSuccessful) {
                changeScene(Scenes.ChatList,null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ID o contraseña incorrectos.");
                alert.show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize() {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
    public static void changeScene(Scenes scene, Object data) throws IOException {
        View view = MainController.loadFXML(scene);
        Scene _scene = new Scene(view.scene, 640, 480);
        App.currentController = view.controller;
        App.currentController.onOpen(data);
        App.stage.setScene(_scene);
        App.stage.show();
    }
    @FXML
    public void Register() throws IOException {
        changeScene(Scenes.MAIN, null);
    }
}

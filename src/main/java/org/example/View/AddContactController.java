package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.Controllers.UserContact;
import org.example.Controllers.UserRegister;
import org.example.Entity.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddContactController extends Controller implements Initializable {

    @FXML
    private TextField UserId;
    @FXML
    private TextField UserLoginId;
    private UserContact userContact;

    @FXML
    private void AddContact() {
        String userId = UserLoginId.getText();
        String contactId = UserId.getText();
        try {
            UserRegister userRegister = new UserRegister();
            List<User> allUsers = userRegister.loadUsersFromFile();

            User currentUser = null;
            for (User user : allUsers) {
                if (user.getId().equals(userId)) {
                    currentUser = user;
                }
            }
            if (currentUser == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Usuario actual no encontrado");
                alert.show();
            } else {
                User newContact = null;
                for (User user : allUsers) {
                    if (user.getId().equals(contactId)) {
                        newContact = user;
                    }
                }
                if (newContact != null) {
                    userContact = new UserContact(currentUser);
                    userContact.addContact(newContact);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Usuario añadido correctamente");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Contacto no encontrado");
                    alert.show();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @FXML
    public void ChangeSceneToContactList() throws IOException {
        LoginUserController.changeScene(Scenes.ContactList,null);
    }

    @Override
    public void onClose(Object output) {

    }
}
package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Entity.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ConctactListController extends Controller implements Initializable {
    @FXML
    private TableView<User> contactsTable;
    @FXML
    private TableColumn<User, String> columnIDUser;
    @FXML
    private TableColumn<User, String> columnContactName;
    private User loggedUser;

    @Override
    public void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnIDUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnContactName.setCellValueFactory(new PropertyValueFactory<>("name"));

        contactsTable.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    User selectedContact = row.getItem();
                    try {
                        changeSceneToChat(selectedContact);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    @Override
    public void onOpen(Object input) throws IOException {
        if (input instanceof User) {
            loggedUser = (User) input;
            loadContacts(loggedUser.getContacts());
        }


    }
    private void loadContacts(List<User> contacts) {
        if (contacts != null && !contacts.isEmpty()) {
            contactsTable.getItems().setAll(contacts);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Añade a alguien a tus contactos que estas mas solo que la una.");
            alert.show();
        }
    }
    private void changeSceneToChat(User selectedContact) throws IOException {
        LoginUserController.changeScene(Scenes.Chat, selectedContact);
    }
    public void addContactButton() throws IOException {
        LoginUserController.changeScene(Scenes.AddContact, null);
    }

    @Override
    public void onClose(Object output) {

    }
}
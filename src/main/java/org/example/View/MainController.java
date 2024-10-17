package org.example.View;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {

    public static View loadFXML(Scenes scene) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource(scene.getURL()));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        return new View(root, controller);
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

    @Override
    public void onClose(Object output) {

    }
}

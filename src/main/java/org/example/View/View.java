package org.example.View;

import javafx.scene.Parent;

public class View {
    public Parent scene;
    public Controller controller;
    public View(Parent scene, Controller controller) {
        this.scene = scene;
        this.controller = controller;
    }
}
